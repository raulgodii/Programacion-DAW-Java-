// SPDX-License-Identifier: LGPL-2.1-or-later
// Copyright (c) 2012-2014 Monty Program Ab
// Copyright (c) 2015-2021 MariaDB Corporation Ab

package org.mariadb.jdbc.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.function.Executable;
import org.mariadb.jdbc.*;
import org.mariadb.jdbc.export.HaMode;
import org.mariadb.jdbc.export.SslMode;
import org.mariadb.jdbc.integration.tools.TcpProxy;

public class Common {

  public static Connection sharedConn;
  public static Connection sharedConnBinary;
  public static String hostname;
  public static int port;
  public static String user;
  public static String password;
  public static String database;
  public static String defaultOther;
  public static TcpProxy proxy;
  public static String mDefUrl;
  private static Instant initialTest;

  static {
    try (InputStream inputStream =
        Common.class.getClassLoader().getResourceAsStream("conf.properties")) {
      Properties prop = new Properties();
      prop.load(inputStream);
      String val = System.getenv("TEST_REQUIRE_TLS");
      if ("1".equals(val)) {
        String cert = System.getenv("TEST_DB_SERVER_CERT");
        defaultOther = "&sslMode=verify-full&serverSslCert=" + cert;
      } else {
        defaultOther = get("DB_OTHER", prop);
      }
      hostname = get("DB_HOST", prop);
      user = get("DB_USER", prop);
      port = Integer.parseInt(get("DB_PORT", prop));
      password = get("DB_PASSWORD", prop);
      database = get("DB_DATABASE", prop);
      mDefUrl =
          password == null || password.isEmpty()
              ? String.format(
                  "jdbc:mariadb://%s:%s/%s?user=%s%s", hostname, port, database, user, defaultOther)
              : String.format(
                  "jdbc:mariadb://%s:%s/%s?user=%s&password=%s%s",
                  hostname, port, database, user, password, defaultOther);

    } catch (IOException io) {
      io.printStackTrace();
    }
  }

  private static String get(String name, Properties prop) {
    String val = System.getenv("TEST_" + name);
    if (val == null) val = System.getProperty("TEST_" + name);
    if (val == null) val = prop.getProperty(name);
    return val;
  }

  @BeforeAll
  public static void beforeAll() throws Exception {
    sharedConn = (Connection) DriverManager.getConnection(mDefUrl);
    String binUrl = mDefUrl + (mDefUrl.indexOf("?") > 0 ? "&" : "?") + "useServerPrepStmts=true";
    sharedConnBinary = (Connection) DriverManager.getConnection(binUrl);
  }

  @AfterAll
  public static void afterEAll() throws SQLException {
    if (sharedConn != null) {
      sharedConn.close();
      sharedConnBinary.close();
    }
    if (proxy != null) {
      proxy.forceClose();
    }
  }

  public static boolean isMariaDBServer() {
    return sharedConn.getContext().getVersion().isMariaDBServer();
  }

  public static boolean hasCapability(long capability) {
    return sharedConn.getContext().hasClientCapability(capability);
  }

  public static boolean runLongTest() {
    String runLongTest = System.getenv("RUN_LONG_TEST");
    if (runLongTest != null) {
      return Boolean.parseBoolean(runLongTest);
    }
    return false;
  }

  public static boolean isXpand() {
    String srv = System.getenv("srv");
    if (srv != null) {
      return "xpand".equals(srv);
    }
    return sharedConn.getContext().getVersion().isMariaDBServer()
        && sharedConn.getContext().getVersion().getQualifier().toLowerCase().contains("xpand");
  }

  public static boolean minVersion(int major, int minor, int patch) {
    return sharedConn.getContext().getVersion().versionGreaterOrEqual(major, minor, patch);
  }

  public static boolean exactVersion(int major, int minor, int patch) {
    return sharedConn.getContext().getVersion().getMajorVersion() == major
        && sharedConn.getContext().getVersion().getMinorVersion() == minor
        && sharedConn.getContext().getVersion().getPatchVersion() == patch;
  }

  public static Connection createCon() throws SQLException {
    return (Connection) DriverManager.getConnection(mDefUrl);
  }

  public static void createSequenceTables() throws SQLException {
    Statement stmt = sharedConn.createStatement();
    boolean seq10_ok = false;
    boolean seq10_000_ok = false;
    try {
      ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sequence_1_to_10");
      if (rs.next() && rs.getInt(1) == 10) {
        seq10_ok = true;
      }
    } catch (SQLException e) {
      // eat
    }
    if (!seq10_ok) {
      stmt.execute("DROP TABLE IF EXISTS sequence_1_to_10");
      stmt.execute("CREATE TABLE sequence_1_to_10 (t1 int)");
      stmt.execute("insert into sequence_1_to_10 VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10)");
    }

    try {
      ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM sequence_1_to_10000");
      if (rs.next() && rs.getInt(1) == 10_000) {
        seq10_000_ok = true;
      }
    } catch (SQLException e) {
      // eat
    }
    if (!seq10_000_ok) {
      stmt.execute("DROP TABLE IF EXISTS sequence_1_to_10000");
      stmt.execute("CREATE TABLE sequence_1_to_10000 (t1 int)");
      try (PreparedStatement prep =
          sharedConnBinary.prepareStatement("INSERT INTO sequence_1_to_10000 VALUES (?)")) {
        for (int i = 1; i <= 10_000; i++) {
          prep.setInt(1, i);
          prep.addBatch();
        }
        prep.executeBatch();
      }
    }
  }

  public Connection createProxyCon(HaMode mode, String opts) throws SQLException {
    Configuration conf = Configuration.parse(mDefUrl);
    HostAddress hostAddress = conf.addresses().get(0);
    try {
      proxy = new TcpProxy(hostAddress.host, hostAddress.port);
    } catch (IOException i) {
      throw new SQLException("proxy error", i);
    }

    String url = mDefUrl.replaceAll("//([^/]*)/", "//localhost:" + proxy.getLocalPort() + "/");
    if (mode != HaMode.NONE) {
      url =
          url.replaceAll(
              "jdbc:mariadb:", "jdbc:mariadb:" + mode.name().toLowerCase(Locale.ROOT) + ":");
    }
    if (conf.sslMode() == SslMode.VERIFY_FULL) {
      url = url.replaceAll("sslMode=verify-full", "sslMode=verify-ca");
    }

    return (Connection) DriverManager.getConnection(url + opts);
  }

  public static boolean haveSsl() throws SQLException {
    Statement stmt = sharedConn.createStatement();
    ResultSet rs = stmt.executeQuery("show variables like '%ssl%'");
    //    while (rs.next()) {
    //      System.out.println(rs.getString(1) + ":" + rs.getString(2));
    //    }
    try {
      rs = stmt.executeQuery("select @@have_ssl");
      assertTrue(rs.next());
      return "YES".equals(rs.getString(1));
    } catch (SQLException e) {
      return false;
    }
  }

  public boolean isWindows() {
    return System.getProperty("os.name").toLowerCase().contains("win");
  }

  public void cancelForVersion(int major, int minor) {
    String dbVersion = sharedConn.getMetaData().getDatabaseProductVersion();
    Assumptions.assumeFalse(dbVersion.startsWith(major + "." + minor));
  }

  public static Connection createCon(String option) throws SQLException {
    return (Connection) DriverManager.getConnection(mDefUrl + "&" + option);
  }

  public static Connection createCon(String option, Integer sslPort) throws SQLException {
    Configuration conf = Configuration.parse(mDefUrl + "&" + option);
    if (sslPort != null) {
      for (HostAddress hostAddress : conf.addresses()) {
        hostAddress.port = sslPort;
      }
    }
    return Driver.connect(conf);
  }

  @AfterEach
  public void afterEach1() throws SQLException {
    sharedConn.isValid(2000);
    sharedConnBinary.isValid(2000);
  }

  public static int getMaxAllowedPacket() throws SQLException {
    java.sql.Statement st = sharedConn.createStatement();
    ResultSet rs = st.executeQuery("select @@max_allowed_packet");
    assertTrue(rs.next());
    return rs.getInt(1);
  }

  public static void assertThrowsContains(
      Class<? extends Exception> expectedType, Executable executable, String expected) {
    Exception e = Assertions.assertThrows(expectedType, executable);
    Assertions.assertTrue(e.getMessage().contains(expected), "real message:" + e.getMessage());
  }

  @RegisterExtension public Extension watcher = new Follow();

  private static class Follow implements TestWatcher, BeforeEachCallback {
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
      System.out.printf(
          "  - Disabled %s: with reason :- %s%n",
          context.getDisplayName(), reason.orElse("No reason"));
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
      System.out.printf("  - Aborted %s: %n", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      System.out.printf("  ✗ Failed %s: %n", context.getDisplayName());
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
      System.out.printf(
          "  ✓ %s: %sms%n",
          context.getDisplayName(), Duration.between(initialTest, Instant.now()).toMillis());
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
      initialTest = Instant.now();
    }
  }
}
