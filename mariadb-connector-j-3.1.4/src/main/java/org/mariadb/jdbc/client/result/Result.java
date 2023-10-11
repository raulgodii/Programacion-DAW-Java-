// SPDX-License-Identifier: LGPL-2.1-or-later
// Copyright (c) 2012-2014 Monty Program Ab
// Copyright (c) 2015-2021 MariaDB Corporation Ab

package org.mariadb.jdbc.client.result;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import org.mariadb.jdbc.Configuration;
import org.mariadb.jdbc.client.*;
import org.mariadb.jdbc.client.impl.StandardReadableByteBuf;
import org.mariadb.jdbc.client.result.rowdecoder.BinaryRowDecoder;
import org.mariadb.jdbc.client.result.rowdecoder.RowDecoder;
import org.mariadb.jdbc.client.result.rowdecoder.TextRowDecoder;
import org.mariadb.jdbc.client.util.MutableInt;
import org.mariadb.jdbc.export.ExceptionFactory;
import org.mariadb.jdbc.message.server.ErrorPacket;
import org.mariadb.jdbc.plugin.Codec;
import org.mariadb.jdbc.plugin.codec.*;
import org.mariadb.jdbc.util.constants.ServerStatus;

/** Result-set common */
public abstract class Result implements ResultSet, Completion {
  private static BinaryRowDecoder BINARY_ROW_DECODER = new BinaryRowDecoder();
  private static TextRowDecoder TEXT_ROW_DECODER = new TextRowDecoder();
  /** null length value */
  public static final int NULL_LENGTH = -1;

  private final int maxIndex;
  private final boolean closeOnCompletion;
  private boolean forceAlias;
  private final boolean traceEnable;

  /** result-set type */
  protected final int resultSetType;

  /** connection exception factory */
  protected final ExceptionFactory exceptionFactory;

  /** packet reader */
  protected final org.mariadb.jdbc.client.socket.Reader reader;

  /** connection context */
  protected final Context context;

  /** columns metadata */
  protected final ColumnDecoder[] metadataList;

  /** binary/text row decoder */
  protected final RowDecoder rowDecoder;

  /** data size */
  protected int dataSize = 0;

  /** rows */
  protected byte[][] data;

  private byte[] nullBitmap;

  /** reusable row buffer decoder */
  protected final StandardReadableByteBuf rowBuf = new StandardReadableByteBuf(null, 0);

  private MutableInt fieldLength = new MutableInt(0);

  /** mutable field index */
  protected MutableInt fieldIndex = new MutableInt();

  private Map<String, Integer> mapper = null;

  /** is fully loaded */
  protected boolean loaded;

  /** is an output parameter result-set */
  protected boolean outputParameter;

  /** current row pointer */
  protected int rowPointer = -1;

  /** is result-set closed */
  protected boolean closed;

  /** statement that initiate this result */
  protected Statement statement;

  /** row number limit */
  protected long maxRows;

  /**
   * Constructor for server's data
   *
   * @param stmt statement that initiate this result
   * @param binaryProtocol binary encoded rows
   * @param maxRows row number limit
   * @param metadataList columns metadata
   * @param reader packet reader
   * @param context connection context
   * @param resultSetType result-set type
   * @param closeOnCompletion close statement on completion
   * @param traceEnable logger enabled
   */
  public Result(
      org.mariadb.jdbc.Statement stmt,
      boolean binaryProtocol,
      long maxRows,
      ColumnDecoder[] metadataList,
      org.mariadb.jdbc.client.socket.Reader reader,
      Context context,
      int resultSetType,
      boolean closeOnCompletion,
      boolean traceEnable) {
    this.maxRows = maxRows;
    this.statement = stmt;
    this.closeOnCompletion = closeOnCompletion;
    this.metadataList = metadataList;
    this.maxIndex = this.metadataList.length;
    this.reader = reader;
    this.exceptionFactory = context.getExceptionFactory();
    this.context = context;
    this.resultSetType = resultSetType;
    this.traceEnable = traceEnable;
    if (binaryProtocol) {
      rowDecoder = BINARY_ROW_DECODER;
      nullBitmap = new byte[(maxIndex + 9) / 8];
    } else {
      rowDecoder = TEXT_ROW_DECODER;
    }
  }

  /**
   * Internal constructed result-set
   *
   * @param metadataList column metadata
   * @param data raw data
   * @param context connection context
   */
  public Result(ColumnDecoder[] metadataList, byte[][] data, Context context) {
    this.metadataList = metadataList;
    this.maxIndex = this.metadataList.length;
    this.reader = null;
    this.loaded = true;
    this.exceptionFactory = context.getExceptionFactory();
    this.context = context;
    this.data = data;
    this.dataSize = data.length;
    this.statement = null;
    this.resultSetType = TYPE_FORWARD_ONLY;
    this.closeOnCompletion = false;
    this.traceEnable = false;
    rowDecoder = TEXT_ROW_DECODER;
  }

  /**
   * Read new row
   *
   * @return true if fully loaded
   * @throws IOException if any socket error occurs
   * @throws SQLException for all other type of errors
   */
  @SuppressWarnings("fallthrough")
  protected boolean readNext() throws IOException, SQLException {
    byte[] buf = reader.readPacket(traceEnable);
    switch (buf[0]) {
      case (byte) 0xFF:
        loaded = true;
        ErrorPacket errorPacket = new ErrorPacket(reader.readableBufFromArray(buf), context);
        throw exceptionFactory.create(
            errorPacket.getMessage(), errorPacket.getSqlState(), errorPacket.getErrorCode());

      case (byte) 0xFE:
        if ((context.isEofDeprecated() && buf.length < 16777215)
            || (!context.isEofDeprecated() && buf.length < 8)) {
          ReadableByteBuf readBuf = reader.readableBufFromArray(buf);
          readBuf.skip(); // skip header
          int serverStatus;
          int warnings;

          if (!context.isEofDeprecated()) {
            // EOF_Packet
            warnings = readBuf.readUnsignedShort();
            serverStatus = readBuf.readUnsignedShort();
          } else {
            // OK_Packet with a 0xFE header
            readBuf.readLongLengthEncodedNotNull(); // skip update count
            readBuf.readLongLengthEncodedNotNull(); // skip insert id
            serverStatus = readBuf.readUnsignedShort();
            warnings = readBuf.readUnsignedShort();
          }
          outputParameter = (serverStatus & ServerStatus.PS_OUT_PARAMETERS) != 0;
          context.setServerStatus(serverStatus);
          context.setWarning(warnings);
          loaded = true;
          return false;
        }

        // continue reading rows

      default:
        if (dataSize + 1 > data.length) {
          growDataArray();
        }
        data[dataSize++] = buf;
    }
    return true;
  }

  /**
   * Skip remaining rows to keep connection state ok, without needing remaining data.
   *
   * @throws IOException if socket error occurs
   * @throws SQLException for other kind of error
   */
  @SuppressWarnings("fallthrough")
  protected void skipRemaining() throws IOException, SQLException {
    while (true) {
      ReadableByteBuf buf = reader.readReusablePacket(traceEnable);
      switch (buf.getUnsignedByte()) {
        case 0xFF:
          loaded = true;
          ErrorPacket errorPacket = new ErrorPacket(buf, context);
          throw exceptionFactory.create(
              errorPacket.getMessage(), errorPacket.getSqlState(), errorPacket.getErrorCode());

        case 0xFE:
          if ((context.isEofDeprecated() && buf.readableBytes() < 0xffffff)
              || (!context.isEofDeprecated() && buf.readableBytes() < 8)) {

            buf.skip(); // skip header
            int serverStatus;
            int warnings;

            if (!context.isEofDeprecated()) {
              // EOF_Packet
              warnings = buf.readUnsignedShort();
              serverStatus = buf.readUnsignedShort();
            } else {
              // OK_Packet with a 0xFE header
              buf.readLongLengthEncodedNotNull(); // skip update count
              buf.readLongLengthEncodedNotNull(); // skip insert id
              serverStatus = buf.readUnsignedShort();
              warnings = buf.readUnsignedShort();
            }
            outputParameter = (serverStatus & ServerStatus.PS_OUT_PARAMETERS) != 0;
            context.setServerStatus(serverStatus);
            context.setWarning(warnings);
            loaded = true;
            return;
          }
      }
    }
  }

  /** Grow data array. */
  private void growDataArray() {
    int newCapacity = data.length + (data.length >> 1);
    byte[][] newData = new byte[newCapacity][];
    System.arraycopy(data, 0, newData, 0, data.length);
    data = newData;
  }

  /**
   * Position resultset to next row
   *
   * @return true if next row exists
   * @throws SQLException if any error occurs
   */
  @Override
  public abstract boolean next() throws SQLException;

  /**
   * Indicate of current result-set is a streaming result-set
   *
   * @return if streaming result-set
   */
  public abstract boolean streaming();

  /**
   * Fetch remaining results.
   *
   * @throws SQLException if issue occurs during data retrieving
   */
  public abstract void fetchRemaining() throws SQLException;

  /**
   * Is result-set fully loaded or still streaming
   *
   * @return true if fully loaded
   */
  public boolean loaded() {
    return loaded;
  }

  /**
   * Does result-set contain output parameters
   *
   * @return true if containing output parameters
   */
  public boolean isOutputParameter() {
    return outputParameter;
  }

  /**
   * Close current result-set
   *
   * @throws SQLException if socket error occurs
   */
  @Override
  public void close() throws SQLException {
    if (!loaded) {
      try {
        skipRemaining();
      } catch (IOException ioe) {
        throw exceptionFactory.create("Error while streaming resultSet data", "08000", ioe);
      }
    }
    this.closed = true;
    if (closeOnCompletion) {
      statement.close();
    }
  }

  /**
   * Closing result-set due to closing statement that issue command.
   *
   * @param lock thread locker object
   * @throws SQLException if any error occurs
   */
  public void closeFromStmtClose(ReentrantLock lock) throws SQLException {
    lock.lock();
    try {
      this.fetchRemaining();
      this.closed = true;
    } finally {
      lock.unlock();
    }
  }

  /** Aborting result-set, without any consideration for connection state. */
  public void abort() {
    this.closed = true;
  }

  /**
   * return current row RAW data
   *
   * @return current row RAW data
   */
  protected byte[] getCurrentRowData() {
    return data[0];
  }

  /**
   * Add a row
   *
   * @param buf add row
   */
  protected void addRowData(byte[] buf) {
    if (dataSize + 1 > data.length) {
      growDataArray();
    }
    data[dataSize++] = buf;
  }

  /**
   * Update current row
   *
   * @param rawData new row
   */
  protected void updateRowData(byte[] rawData) {
    data[rowPointer] = rawData;
    if (rawData == null) {
      setNullRowBuf();
    } else {
      setRow(rawData);
      fieldIndex.set(-1);
    }
  }

  private void checkIndex(int index) throws SQLException {
    if (index < 1 || index > maxIndex) {
      throw new SQLException(
          String.format("Wrong index position. Is %s but must be in 1-%s range", index, maxIndex));
    }
    if (rowBuf.buf == null) {
      throw new SQLDataException("wrong row position", "22023");
    }
  }

  /**
   * has last data getter return a null value
   *
   * @return true if was null
   */
  @Override
  public boolean wasNull() {
    return rowDecoder.wasNull(nullBitmap, fieldIndex, fieldLength);
  }

  @Override
  public String getString(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeString(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public boolean getBoolean(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return false;
    }
    return rowDecoder.decodeBoolean(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public byte getByte(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return 0;
    }
    return rowDecoder.decodeByte(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public short getShort(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return 0;
    }
    return rowDecoder.decodeShort(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public int getInt(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return 0;
    }
    return rowDecoder.decodeInt(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public long getLong(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return 0L;
    }
    return rowDecoder.decodeLong(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  /**
   * Retrieves the value of the designated column in the current row of this ResultSet object as a
   * BigInteger.
   *
   * @param columnIndex index
   * @return BigInteger value
   * @throws SQLException if cannot be decoded as a BigInteger
   */
  public BigInteger getBigInteger(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        BigIntegerCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  /**
   * Retrieves the value of the designated column in the current row of this ResultSet object as a
   * BigInteger.
   *
   * @param columnLabel column label
   * @return BigInteger value
   * @throws SQLException if cannot be decoded as a BigInteger
   */
  public BigInteger getBigInteger(String columnLabel) throws SQLException {
    return getBigInteger(findColumn(columnLabel));
  }

  @Override
  public float getFloat(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return 0F;
    }
    return rowDecoder.decodeFloat(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public double getDouble(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return 0D;
    }
    return rowDecoder.decodeDouble(metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    BigDecimal d =
        rowDecoder.decode(
            BigDecimalCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
    if (d == null) return null;
    return d.setScale(scale, RoundingMode.HALF_DOWN);
  }

  @Override
  public byte[] getBytes(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        ByteArrayCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public Date getDate(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeDate(metadataList, fieldIndex, rowBuf, fieldLength, null);
  }

  @Override
  public Time getTime(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeTime(metadataList, fieldIndex, rowBuf, fieldLength, null);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeTimestamp(metadataList, fieldIndex, rowBuf, fieldLength, null);
  }

  @Override
  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        StreamCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  @Deprecated
  public InputStream getUnicodeStream(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        StreamCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        StreamCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public String getString(String columnLabel) throws SQLException {
    return getString(findColumn(columnLabel));
  }

  @Override
  public boolean getBoolean(String columnLabel) throws SQLException {
    return getBoolean(findColumn(columnLabel));
  }

  @Override
  public byte getByte(String columnLabel) throws SQLException {
    return getByte(findColumn(columnLabel));
  }

  @Override
  public short getShort(String columnLabel) throws SQLException {
    return getShort(findColumn(columnLabel));
  }

  @Override
  public int getInt(String columnLabel) throws SQLException {
    return getInt(findColumn(columnLabel));
  }

  @Override
  public long getLong(String columnLabel) throws SQLException {
    return getLong(findColumn(columnLabel));
  }

  @Override
  public float getFloat(String columnLabel) throws SQLException {
    return getFloat(findColumn(columnLabel));
  }

  @Override
  public double getDouble(String columnLabel) throws SQLException {
    return getDouble(findColumn(columnLabel));
  }

  @Override
  @Deprecated
  public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
    return getBigDecimal(findColumn(columnLabel), scale);
  }

  @Override
  public byte[] getBytes(String columnLabel) throws SQLException {
    return getBytes(findColumn(columnLabel));
  }

  @Override
  public Date getDate(String columnLabel) throws SQLException {
    return getDate(findColumn(columnLabel));
  }

  @Override
  public Time getTime(String columnLabel) throws SQLException {
    return getTime(findColumn(columnLabel));
  }

  @Override
  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    return getTimestamp(findColumn(columnLabel));
  }

  @Override
  public InputStream getAsciiStream(String columnLabel) throws SQLException {
    return getAsciiStream(findColumn(columnLabel));
  }

  @Override
  @Deprecated
  public InputStream getUnicodeStream(String columnLabel) throws SQLException {
    return getUnicodeStream(findColumn(columnLabel));
  }

  @Override
  public InputStream getBinaryStream(String columnLabel) throws SQLException {
    return getBinaryStream(findColumn(columnLabel));
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    if (this.statement == null) {
      return null;
    }
    return this.statement.getWarnings();
  }

  @Override
  public void clearWarnings() throws SQLException {
    if (this.statement != null) {
      this.statement.clearWarnings();
    }
  }

  @Override
  public String getCursorName() throws SQLException {
    throw exceptionFactory.notSupported("Cursors are not supported");
  }

  @Override
  public ResultSetMetaData getMetaData() {
    return new ResultSetMetaData(exceptionFactory, metadataList, context.getConf(), forceAlias);
  }

  @Override
  public Object getObject(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.defaultDecode(
        context.getConf(), metadataList, fieldIndex, rowBuf, fieldLength);
  }

  @Override
  public Object getObject(String columnLabel) throws SQLException {
    return getObject(findColumn(columnLabel));
  }

  @Override
  public Reader getCharacterStream(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        ReaderCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public Reader getCharacterStream(String columnLabel) throws SQLException {
    return getCharacterStream(findColumn(columnLabel));
  }

  @Override
  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        BigDecimalCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
    return getBigDecimal(findColumn(columnLabel));
  }

  /**
   * Verify that result-set is not closed, throwing an exception if closed
   *
   * @throws SQLException if closed
   */
  protected void checkClose() throws SQLException {
    if (closed) {
      throw exceptionFactory.create("Operation not permit on a closed resultSet", "HY000");
    }
  }

  /**
   * Throw an exception if result-set type is ResultSet.TYPE_FORWARD_ONLY
   *
   * @throws SQLException throw error if type is ResultSet.TYPE_FORWARD_ONLY
   */
  protected void checkNotForwardOnly() throws SQLException {
    if (resultSetType == ResultSet.TYPE_FORWARD_ONLY) {
      throw exceptionFactory.create("Operation not permit on TYPE_FORWARD_ONLY resultSet", "HY000");
    }
  }

  @Override
  public boolean isBeforeFirst() throws SQLException {
    checkClose();
    return rowPointer == -1 && dataSize > 0;
  }

  @Override
  public abstract boolean isAfterLast() throws SQLException;

  @Override
  public abstract boolean isFirst() throws SQLException;

  @Override
  public abstract boolean isLast() throws SQLException;

  @Override
  public abstract void beforeFirst() throws SQLException;

  @Override
  public abstract void afterLast() throws SQLException;

  @Override
  public abstract boolean first() throws SQLException;

  @Override
  public abstract boolean last() throws SQLException;

  @Override
  public abstract int getRow() throws SQLException;

  @Override
  public abstract boolean absolute(int row) throws SQLException;

  @Override
  public abstract boolean relative(int rows) throws SQLException;

  @Override
  public abstract boolean previous() throws SQLException;

  @Override
  public int getFetchDirection() {
    return FETCH_UNKNOWN;
  }

  @Override
  public void setFetchDirection(int direction) throws SQLException {
    if (direction == FETCH_REVERSE) {
      throw exceptionFactory.create(
          "Invalid operation. Allowed direction are ResultSet.FETCH_FORWARD and ResultSet.FETCH_UNKNOWN");
    }
  }

  @Override
  public int getType() {
    return resultSetType;
  }

  @Override
  public int getConcurrency() {
    return CONCUR_READ_ONLY;
  }

  @Override
  public boolean rowUpdated() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public boolean rowInserted() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public boolean rowDeleted() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNull(int columnIndex) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBoolean(int columnIndex, boolean x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateByte(int columnIndex, byte x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateShort(int columnIndex, short x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateInt(int columnIndex, int x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateLong(int columnIndex, long x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateFloat(int columnIndex, float x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateDouble(int columnIndex, double x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateString(int columnIndex, String x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBytes(int columnIndex, byte[] x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateDate(int columnIndex, Date x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateTime(int columnIndex, Time x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(int columnIndex, Object x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNull(String columnLabel) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBoolean(String columnLabel, boolean x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateByte(String columnLabel, byte x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateShort(String columnLabel, short x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateInt(String columnLabel, int x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateLong(String columnLabel, long x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateFloat(String columnLabel, float x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateDouble(String columnLabel, double x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateString(String columnLabel, String x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBytes(String columnLabel, byte[] x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateDate(String columnLabel, Date x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateTime(String columnLabel, Time x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, int length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, int length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(String columnLabel, Object x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void insertRow() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateRow() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void deleteRow() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void refreshRow() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void cancelRowUpdates() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void moveToInsertRow() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void moveToCurrentRow() throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public Statement getStatement() {
    return statement;
  }

  /**
   * Update statement that initiate this result-set
   *
   * @param stmt statement
   */
  public void setStatement(Statement stmt) {
    statement = stmt;
  }

  /** Force using alias as name */
  public void useAliasAsName() {
    for (Column packet : metadataList) {
      packet.useAliasAsName();
    }
    forceAlias = true;
  }

  @Override
  public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
    if (map == null || map.isEmpty()) {
      return getObject(columnIndex);
    }
    throw exceptionFactory.notSupported(
        "Method ResultSet.getObject(int columnIndex, Map<String, Class<?>> map) not supported for non empty map");
  }

  @Override
  public Ref getRef(int columnIndex) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.getRef not supported");
  }

  @Override
  public Blob getBlob(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        BlobCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public Clob getClob(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        ClobCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public Array getArray(int columnIndex) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.getArray not supported");
  }

  @Override
  public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
    if (map == null || map.isEmpty()) {
      return getObject(columnLabel);
    }
    throw exceptionFactory.notSupported(
        "Method ResultSet.getObject(String columnLabel, Map<String, Class<?>> map) not supported");
  }

  @Override
  public Ref getRef(String columnLabel) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.getRef not supported");
  }

  @Override
  public Blob getBlob(String columnLabel) throws SQLException {
    return getBlob(findColumn(columnLabel));
  }

  @Override
  public Clob getClob(String columnLabel) throws SQLException {
    return getClob(findColumn(columnLabel));
  }

  @Override
  public Array getArray(String columnLabel) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.getArray not supported");
  }

  @Override
  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeDate(metadataList, fieldIndex, rowBuf, fieldLength, cal);
  }

  @Override
  public Date getDate(String columnLabel, Calendar cal) throws SQLException {
    return getDate(findColumn(columnLabel), cal);
  }

  @Override
  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeTime(metadataList, fieldIndex, rowBuf, fieldLength, cal);
  }

  @Override
  public Time getTime(String columnLabel, Calendar cal) throws SQLException {
    return getTime(findColumn(columnLabel), cal);
  }

  @Override
  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decodeTimestamp(metadataList, fieldIndex, rowBuf, fieldLength, cal);
  }

  @Override
  public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
    return getTimestamp(findColumn(columnLabel), cal);
  }

  @Override
  public URL getURL(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }

    String s =
        rowDecoder.decode(
            StringCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
    if (s == null) return null;
    try {
      return new URL(s);
    } catch (MalformedURLException e) {
      throw exceptionFactory.create(String.format("Could not parse '%s' as URL", s));
    }
  }

  @Override
  public URL getURL(String columnLabel) throws SQLException {
    return getURL(findColumn(columnLabel));
  }

  @Override
  public void updateRef(int columnIndex, Ref x) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.updateRef not supported");
  }

  @Override
  public void updateRef(String columnLabel, Ref x) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.updateRef not supported");
  }

  @Override
  public void updateBlob(int columnIndex, Blob x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBlob(String columnLabel, Blob x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateClob(int columnIndex, Clob x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateClob(String columnLabel, Clob x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateArray(int columnIndex, Array x) throws SQLException {
    throw exceptionFactory.notSupported("Array are not supported");
  }

  @Override
  public void updateArray(String columnLabel, Array x) throws SQLException {
    throw exceptionFactory.notSupported("Array are not supported");
  }

  @Override
  public RowId getRowId(int columnIndex) throws SQLException {
    throw exceptionFactory.notSupported("RowId are not supported");
  }

  @Override
  public RowId getRowId(String columnLabel) throws SQLException {
    throw exceptionFactory.notSupported("RowId are not supported");
  }

  @Override
  public void updateRowId(int columnIndex, RowId x) throws SQLException {
    throw exceptionFactory.notSupported("RowId are not supported");
  }

  @Override
  public void updateRowId(String columnLabel, RowId x) throws SQLException {
    throw exceptionFactory.notSupported("RowId are not supported");
  }

  @Override
  public int getHoldability() {
    return ResultSet.HOLD_CURSORS_OVER_COMMIT;
  }

  @Override
  public boolean isClosed() {
    return closed;
  }

  @Override
  public void updateNString(int columnIndex, String nString) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNString(String columnLabel, String nString) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public NClob getNClob(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return (NClob)
        rowDecoder.decode(ClobCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public NClob getNClob(String columnLabel) throws SQLException {
    return getNClob(findColumn(columnLabel));
  }

  @Override
  public SQLXML getSQLXML(int columnIndex) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.getSQLXML not supported");
  }

  @Override
  public SQLXML getSQLXML(String columnLabel) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.getSQLXML not supported");
  }

  @Override
  public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.updateSQLXML not supported");
  }

  @Override
  public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
    throw exceptionFactory.notSupported("Method ResultSet.updateSQLXML not supported");
  }

  @Override
  public String getNString(int columnIndex) throws SQLException {
    return getString(columnIndex);
  }

  @Override
  public String getNString(String columnLabel) throws SQLException {
    return getString(columnLabel);
  }

  @Override
  public Reader getNCharacterStream(int columnIndex) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    if (fieldLength.get() == NULL_LENGTH) {
      return null;
    }
    return rowDecoder.decode(
        ReaderCodec.INSTANCE, null, rowBuf, fieldLength, metadataList, fieldIndex);
  }

  @Override
  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    return getNCharacterStream(findColumn(columnLabel));
  }

  @Override
  public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader, long length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x, long length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x, long length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader, long length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBlob(int columnIndex, InputStream inputStream, long length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream, long length)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateClob(int columnIndex, Reader reader) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateClob(String columnLabel, Reader reader) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNClob(int columnIndex, Reader reader) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateNClob(String columnLabel, Reader reader) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    checkIndex(columnIndex);
    fieldLength.set(
        rowDecoder.setPosition(
            columnIndex - 1, fieldIndex, maxIndex, rowBuf, nullBitmap, metadataList));
    Calendar calendar = null;
    if (wasNull()) {
      if (type.isPrimitive()) {
        throw new SQLException(
            String.format("Cannot return null for primitive %s", type.getName()));
      }
      return null;
    }
    Configuration conf = context.getConf();
    ColumnDecoder column = metadataList[columnIndex - 1];
    // type generic, return "natural" java type
    if (Object.class.equals(type) || type == null) {
      return (T) rowDecoder.defaultDecode(conf, metadataList, fieldIndex, rowBuf, fieldLength);
    }

    for (Codec<?> codec : conf.codecs()) {
      if (codec.canDecode(column, type)) {
        return rowDecoder.decode(
            (Codec<T>) codec, calendar, rowBuf, fieldLength, metadataList, fieldIndex);
      }
    }
    rowBuf.skip(fieldLength.get());
    throw new SQLException(
        String.format("Type %s not supported type for %s type", type, column.getType().name()));
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return getObject(findColumn(columnLabel), type);
  }

  @Override
  public <T> T unwrap(Class<T> iface) throws SQLException {
    if (isWrapperFor(iface)) {
      return iface.cast(this);
    }
    throw new SQLException("The receiver is not a wrapper for " + iface.getName());
  }

  @Override
  public boolean isWrapperFor(Class<?> iface) {
    return iface.isInstance(this);
  }

  @Override
  public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  @Override
  public void updateObject(String columnLabel, Object x, SQLType targetSqlType)
      throws SQLException {
    throw exceptionFactory.notSupported("Not supported when using CONCUR_READ_ONLY concurrency");
  }

  /** Set row buffer to null (no row) */
  protected void setNullRowBuf() {
    rowBuf.buf(null, 0, 0);
  }

  /**
   * set row decoder to current row data
   *
   * @param row row
   */
  public void setRow(byte[] row) {
    rowBuf.buf(row, row.length, 0);
    fieldIndex.set(-1);
  }

  public int findColumn(String label) throws SQLException {
    if (label == null) throw new SQLException("null is not a valid label value");
    if (mapper == null) {
      mapper = new HashMap<>();
      for (int i = 0; i < maxIndex; i++) {
        Column ci = metadataList[i];
        String columnAlias = ci.getColumnAlias();
        if (columnAlias != null) {
          columnAlias = columnAlias.toLowerCase(Locale.ROOT);
          mapper.putIfAbsent(columnAlias, i + 1);
          String tableAlias = ci.getTableAlias();
          String tableLabel = tableAlias != null ? tableAlias : ci.getTable();
          mapper.putIfAbsent(tableLabel.toLowerCase(Locale.ROOT) + "." + columnAlias, i + 1);
        }
      }
    }
    Integer ind = mapper.get(label.toLowerCase(Locale.ROOT));
    if (ind == null) {
      String keys = Arrays.toString(mapper.keySet().toArray(new String[0]));
      throw new SQLException(String.format("Unknown label '%s'. Possible value %s", label, keys));
    }
    return ind;
  }
}
