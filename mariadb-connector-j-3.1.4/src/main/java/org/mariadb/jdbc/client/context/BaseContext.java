// SPDX-License-Identifier: LGPL-2.1-or-later
// Copyright (c) 2012-2014 Monty Program Ab
// Copyright (c) 2015-2021 MariaDB Corporation Ab

package org.mariadb.jdbc.client.context;

import static org.mariadb.jdbc.util.constants.Capabilities.STMT_BULK_OPERATIONS;

import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.client.Context;
import org.mariadb.jdbc.client.PrepareCache;
import org.mariadb.jdbc.client.ServerVersion;
import org.mariadb.jdbc.export.ExceptionFactory;
import org.mariadb.jdbc.message.server.InitialHandshakePacket;
import org.mariadb.jdbc.util.constants.Capabilities;

/** Context (current connection state) of a connection */
public class BaseContext implements Context {

  private final long threadId;
  private final long serverCapabilities;
  private final long clientCapabilities;
  private final byte[] seed;
  private final ServerVersion version;
  private final boolean eofDeprecated;
  private final boolean skipMeta;
  private final boolean extendedInfo;
  private final Configuration conf;
  private final ExceptionFactory exceptionFactory;

  /** Server status context */
  protected int serverStatus;

  /** Server current database */
  private String database;

  /** Server current transaction isolation level */
  private int transactionIsolationLevel;

  /** Server current warning count */
  private int warning;

  /** LRU prepare cache object */
  private final PrepareCache prepareCache;

  /** Connection state use flag */
  private int stateFlag = 0;

  /**
   * Constructor of connection context
   *
   * @param handshake server handshake
   * @param clientCapabilities client capabilities
   * @param conf connection configuration
   * @param exceptionFactory connection exception factory
   * @param prepareCache LRU prepare cache
   */
  public BaseContext(
      InitialHandshakePacket handshake,
      long clientCapabilities,
      Configuration conf,
      ExceptionFactory exceptionFactory,
      PrepareCache prepareCache) {
    this.threadId = handshake.getThreadId();
    this.seed = handshake.getSeed();
    this.serverCapabilities = handshake.getCapabilities();
    this.serverStatus = handshake.getServerStatus();
    this.version = handshake.getVersion();
    this.clientCapabilities = clientCapabilities;
    this.eofDeprecated = hasClientCapability(Capabilities.CLIENT_DEPRECATE_EOF);
    this.skipMeta = hasClientCapability(Capabilities.CACHE_METADATA);
    this.extendedInfo = hasClientCapability(Capabilities.EXTENDED_TYPE_INFO);
    this.conf = conf;
    this.database = conf.database();
    this.exceptionFactory = exceptionFactory;
    this.prepareCache = prepareCache;
  }

  public long getThreadId() {
    return threadId;
  }

  public byte[] getSeed() {
    return seed;
  }

  public boolean hasServerCapability(long flag) {
    return (serverCapabilities & flag) > 0;
  }

  public boolean hasClientCapability(long flag) {
    return (clientCapabilities & flag) > 0;
  }

  public boolean permitPipeline() {
    return !conf.disablePipeline() && (clientCapabilities & STMT_BULK_OPERATIONS) > 0;
  }

  public int getServerStatus() {
    return serverStatus;
  }

  public void setServerStatus(int serverStatus) {
    this.serverStatus = serverStatus;
  }

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public ServerVersion getVersion() {
    return version;
  }

  public boolean isEofDeprecated() {
    return eofDeprecated;
  }

  public boolean isExtendedInfo() {
    return extendedInfo;
  }

  public boolean canSkipMeta() {
    return skipMeta;
  }

  public int getWarning() {
    return warning;
  }

  public void setWarning(int warning) {
    this.warning = warning;
  }

  public ExceptionFactory getExceptionFactory() {
    return exceptionFactory;
  }

  public Configuration getConf() {
    return conf;
  }

  public int getTransactionIsolationLevel() {
    return transactionIsolationLevel;
  }

  public void setTransactionIsolationLevel(int transactionIsolationLevel) {
    this.transactionIsolationLevel = transactionIsolationLevel;
  }

  public PrepareCache getPrepareCache() {
    return prepareCache;
  }

  public void resetPrepareCache() {
    if (prepareCache != null) prepareCache.reset();
  }

  public int getStateFlag() {
    return stateFlag;
  }

  public void resetStateFlag() {
    stateFlag = 0;
  }

  public void addStateFlag(int state) {
    stateFlag |= state;
  }
}
