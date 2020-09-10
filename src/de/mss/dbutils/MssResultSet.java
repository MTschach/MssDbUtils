package de.mss.dbutils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mss.utils.DateTimeFormat;
import de.mss.utils.DateTimeTools;
import de.mss.utils.Tools;

public class MssResultSet implements ResultSet {

   private static Logger rootLogger       = LogManager.getRootLogger();

   private Logger        logger           = rootLogger;
   private String        loggingId        = null;

   private ResultSet     resultset        = null;
   private int           currentRow       = 0;
   private int           currentResultset = 0;
   private StringBuilder logBuffer        = null;

   protected Logger getLogger() {
      if (this.logger == null) {
         this.logger = rootLogger;
      }

      return this.logger;
   }


   public void setLogger(Logger l) {
      this.logger = l;
   }


   public MssResultSet(Logger l, String lid, ResultSet res) {
      setLogger(l);
      setLoggingId(lid);
      setResultset(res);
   }


   public void setLoggingId(String lid) {
      this.loggingId = lid;
   }


   public void setResultset(ResultSet res) {
      this.resultset = res;
   }


   public void setCurrentResultSet(int c) {
      this.currentResultset = c;
   }


   @Override
   public boolean next() throws SQLException {
      if (this.resultset == null) {
         return false;
      }

      if (this.resultset.isBeforeFirst()) {
         logColumnNames();
         this.currentRow = 0;
      }

      final boolean ret = this.resultset.next();
      if (ret) {
         this.currentRow++ ;
         logCurrentRow();
      }

      return ret;
   }


   private void logColumnNames() throws SQLException {
      initLogBuffer();

      final ResultSetMetaData meta = this.resultset.getMetaData();
      this.logBuffer.append(this.currentResultset + " [#row");
      for (int i = 1; i <= meta.getColumnCount(); i++ ) {
         this.logBuffer.append(";" + meta.getColumnName(i));
      }
      this.logBuffer.append("] ");
   }


   private void logCurrentRow() throws SQLException {
      initLogBuffer();
      final ResultSetMetaData meta = this.resultset.getMetaData();
      this.logBuffer.append(this.currentResultset + " [" + this.currentRow);
      for (int i = 1; i <= meta.getColumnCount(); i++ ) {
         this.logBuffer.append(";");
         switch (meta.getColumnType(i)) {
            case java.sql.Types.NUMERIC:
               this.logBuffer.append(DbTools.getBigDecimal(this.resultset, i));
               break;

            case java.sql.Types.BIGINT:
               this.logBuffer.append(DbTools.getLong(this.resultset, i));
               break;

            case java.sql.Types.INTEGER:
               this.logBuffer.append(DbTools.getInteger(this.resultset, i));
               break;

            case java.sql.Types.SMALLINT:
               this.logBuffer.append(DbTools.getShort(this.resultset, i));
               break;

            case java.sql.Types.TINYINT:
               this.logBuffer.append(DbTools.getByte(this.resultset, i));
               break;

            case java.sql.Types.DOUBLE:
               this.logBuffer.append(DbTools.getDouble(this.resultset, i));
               break;

            case java.sql.Types.FLOAT:
               this.logBuffer.append(DbTools.getFloat(this.resultset, i));
               break;

            case java.sql.Types.DATE:
               this.logBuffer.append(DateTimeTools.formatDate(DbTools.getDate(this.resultset, i), DateTimeFormat.DATE_FORMAT_EN));
               break;

            case java.sql.Types.TIME:
            case java.sql.Types.TIME_WITH_TIMEZONE:
               this.logBuffer.append(DateTimeTools.formatDate(DbTools.getTime(this.resultset, i), DateTimeFormat.DATE_TIME_FORMAT_DB));
               break;

            case java.sql.Types.TIMESTAMP:
            case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
               this.logBuffer.append(DateTimeTools.formatDate(DbTools.getTimestamp(this.resultset, i), DateTimeFormat.DATE_TIME_FORMAT_DB));
               break;

            case java.sql.Types.BOOLEAN:
               this.logBuffer.append(DbTools.getBoolean(this.resultset, i));
               break;

            case java.sql.Types.BLOB:
            case java.sql.Types.CLOB:
            case java.sql.Types.NCLOB:
            case java.sql.Types.BINARY:
            case java.sql.Types.LONGVARBINARY:
            case java.sql.Types.VARBINARY:
               this.logBuffer.append("[binary data]");
               break;

            case java.sql.Types.CHAR:
            case java.sql.Types.LONGVARCHAR:
            case java.sql.Types.LONGNVARCHAR:
            case java.sql.Types.NCHAR:
            case java.sql.Types.NVARCHAR:
            case java.sql.Types.VARCHAR:
               String value = DbTools.getString(this.resultset, i);
               if (value != null && value.length() > 255) {
                  value = value.substring(0, 252) + "...";
               }
               this.logBuffer.append(value);
               break;

            default:
               this.logBuffer.append("[data]");
               break;
         }
      }
      this.logBuffer.append("] ");
   }


   private void initLogBuffer() {
      if (this.logBuffer != null) {
         return;
      }

      this.logBuffer = new StringBuilder();
   }


   public StringBuilder getLogBuffer() {
      initLogBuffer();

      return this.logBuffer;
   }


   @Override
   public void close() throws SQLException {
      initLogBuffer();
      getLogger().debug(Tools.formatLoggingId(this.loggingId) + this.logBuffer);
      this.logBuffer = new StringBuilder();
      if (this.resultset != null && !this.resultset.isClosed()) {
         this.resultset.close();
      }
   }


   @Override
   public boolean isWrapperFor(Class<?> arg0) throws SQLException {
      return this.resultset.isWrapperFor(arg0);
   }


   @Override
   public <T> T unwrap(Class<T> arg0) throws SQLException {
      return this.resultset.unwrap(arg0);
   }


   @Override
   public boolean absolute(int arg0) throws SQLException {
      return this.resultset.absolute(arg0);
   }


   @Override
   public void afterLast() throws SQLException {
      this.resultset.afterLast();
   }


   @Override
   public void beforeFirst() throws SQLException {
      this.resultset.beforeFirst();
   }


   @Override
   public void cancelRowUpdates() throws SQLException {
      this.resultset.cancelRowUpdates();
   }


   @Override
   public void clearWarnings() throws SQLException {
      this.resultset.clearWarnings();
   }


   @Override
   public void deleteRow() throws SQLException {
      this.resultset.deleteRow();
   }


   @Override
   public int findColumn(String arg0) throws SQLException {
      return this.resultset.findColumn(arg0);
   }


   @Override
   public boolean first() throws SQLException {
      return this.resultset.first();
   }


   @Override
   public Array getArray(int arg0) throws SQLException {
      return this.resultset.getArray(arg0);
   }


   @Override
   public Array getArray(String arg0) throws SQLException {
      return this.resultset.getArray(arg0);
   }


   @Override
   public InputStream getAsciiStream(int arg0) throws SQLException {
      return this.resultset.getAsciiStream(arg0);
   }


   @Override
   public InputStream getAsciiStream(String arg0) throws SQLException {
      return this.resultset.getAsciiStream(arg0);
   }


   @Override
   public BigDecimal getBigDecimal(int arg0) throws SQLException {
      return this.resultset.getBigDecimal(arg0);
   }


   @Override
   public BigDecimal getBigDecimal(String arg0) throws SQLException {
      return this.resultset.getBigDecimal(arg0);
   }


   @Deprecated
   @Override
   public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
      return this.resultset.getBigDecimal(arg0, arg1);
   }


   @Deprecated
   @Override
   public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
      return this.resultset.getBigDecimal(arg0, arg1);
   }


   @Override
   public InputStream getBinaryStream(int arg0) throws SQLException {
      return this.resultset.getBinaryStream(arg0);
   }


   @Override
   public InputStream getBinaryStream(String arg0) throws SQLException {
      return this.resultset.getBinaryStream(arg0);
   }


   @Override
   public Blob getBlob(int arg0) throws SQLException {
      return this.resultset.getBlob(arg0);
   }


   @Override
   public Blob getBlob(String arg0) throws SQLException {
      return this.resultset.getBlob(arg0);
   }


   @Override
   public boolean getBoolean(int arg0) throws SQLException {
      return this.resultset.getBoolean(arg0);
   }


   @Override
   public boolean getBoolean(String arg0) throws SQLException {
      return this.resultset.getBoolean(arg0);
   }


   @Override
   public byte getByte(int arg0) throws SQLException {
      return this.resultset.getByte(arg0);
   }


   @Override
   public byte getByte(String arg0) throws SQLException {
      return this.resultset.getByte(arg0);
   }


   @Override
   public byte[] getBytes(int arg0) throws SQLException {
      return this.resultset.getBytes(arg0);
   }


   @Override
   public byte[] getBytes(String arg0) throws SQLException {
      return this.resultset.getBytes(arg0);
   }


   @Override
   public Reader getCharacterStream(int arg0) throws SQLException {
      return this.resultset.getCharacterStream(arg0);
   }


   @Override
   public Reader getCharacterStream(String arg0) throws SQLException {
      return this.resultset.getCharacterStream(arg0);
   }


   @Override
   public Clob getClob(int arg0) throws SQLException {
      return this.resultset.getClob(arg0);
   }


   @Override
   public Clob getClob(String arg0) throws SQLException {
      return this.resultset.getClob(arg0);
   }


   @Override
   public int getConcurrency() throws SQLException {
      return this.resultset.getConcurrency();
   }


   @Override
   public String getCursorName() throws SQLException {
      return this.resultset.getCursorName();
   }


   @Override
   public Date getDate(int arg0) throws SQLException {
      return this.resultset.getDate(arg0);
   }


   @Override
   public Date getDate(String arg0) throws SQLException {
      return this.resultset.getDate(arg0);
   }


   @Override
   public Date getDate(int arg0, Calendar arg1) throws SQLException {
      return this.resultset.getDate(arg0, arg1);
   }


   @Override
   public Date getDate(String arg0, Calendar arg1) throws SQLException {
      return this.resultset.getDate(arg0, arg1);
   }


   @Override
   public double getDouble(int arg0) throws SQLException {
      return this.resultset.getDouble(arg0);
   }


   @Override
   public double getDouble(String arg0) throws SQLException {
      return this.resultset.getDouble(arg0);
   }


   @Override
   public int getFetchDirection() throws SQLException {
      return this.resultset.getFetchDirection();
   }


   @Override
   public int getFetchSize() throws SQLException {
      return this.resultset.getFetchSize();
   }


   @Override
   public float getFloat(int arg0) throws SQLException {
      return this.resultset.getFloat(arg0);
   }


   @Override
   public float getFloat(String arg0) throws SQLException {
      return this.resultset.getFloat(arg0);
   }


   @Override
   public int getHoldability() throws SQLException {
      return this.resultset.getHoldability();
   }


   @Override
   public int getInt(int arg0) throws SQLException {
      return this.resultset.getInt(arg0);
   }


   @Override
   public int getInt(String arg0) throws SQLException {
      return this.resultset.getInt(arg0);
   }


   @Override
   public long getLong(int arg0) throws SQLException {
      return this.resultset.getLong(arg0);
   }


   @Override
   public long getLong(String arg0) throws SQLException {
      return this.resultset.getLong(arg0);
   }


   @Override
   public ResultSetMetaData getMetaData() throws SQLException {
      return this.resultset.getMetaData();
   }


   @Override
   public Reader getNCharacterStream(int arg0) throws SQLException {
      return this.resultset.getNCharacterStream(arg0);
   }


   @Override
   public Reader getNCharacterStream(String arg0) throws SQLException {
      return this.resultset.getNCharacterStream(arg0);
   }


   @Override
   public NClob getNClob(int arg0) throws SQLException {
      return this.resultset.getNClob(arg0);
   }


   @Override
   public NClob getNClob(String arg0) throws SQLException {
      return this.resultset.getNClob(arg0);
   }


   @Override
   public String getNString(int arg0) throws SQLException {
      return this.resultset.getNString(arg0);
   }


   @Override
   public String getNString(String arg0) throws SQLException {
      return this.resultset.getNString(arg0);
   }


   @Override
   public Object getObject(int arg0) throws SQLException {
      return this.resultset.getObject(arg0);
   }


   @Override
   public Object getObject(String arg0) throws SQLException {
      return this.resultset.getObject(arg0);
   }


   @Override
   public Object getObject(int arg0, Map<String, Class<?>> arg1) throws SQLException {
      return this.resultset.getObject(arg0, arg1);
   }


   @Override
   public Object getObject(String arg0, Map<String, Class<?>> arg1) throws SQLException {
      return this.resultset.getObject(arg0, arg1);
   }


   @Override
   public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
      return this.resultset.getObject(arg0, arg1);
   }


   @Override
   public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
      return this.resultset.getObject(arg0, arg1);
   }


   @Override
   public Ref getRef(int arg0) throws SQLException {
      return this.resultset.getRef(arg0);
   }


   @Override
   public Ref getRef(String arg0) throws SQLException {
      return this.resultset.getRef(arg0);
   }


   @Override
   public int getRow() throws SQLException {
      return this.resultset.getRow();
   }


   @Override
   public RowId getRowId(int arg0) throws SQLException {
      return this.resultset.getRowId(arg0);
   }


   @Override
   public RowId getRowId(String arg0) throws SQLException {
      return this.resultset.getRowId(arg0);
   }


   @Override
   public SQLXML getSQLXML(int arg0) throws SQLException {
      return this.resultset.getSQLXML(arg0);
   }


   @Override
   public SQLXML getSQLXML(String arg0) throws SQLException {
      return this.resultset.getSQLXML(arg0);
   }


   @Override
   public short getShort(int arg0) throws SQLException {
      return this.resultset.getShort(arg0);
   }


   @Override
   public short getShort(String arg0) throws SQLException {
      return this.resultset.getShort(arg0);
   }


   @Override
   public Statement getStatement() throws SQLException {
      return this.resultset.getStatement();
   }


   @Override
   public String getString(int arg0) throws SQLException {
      return this.resultset.getString(arg0);
   }


   @Override
   public String getString(String arg0) throws SQLException {
      return this.resultset.getString(arg0);
   }


   @Override
   public Time getTime(int arg0) throws SQLException {
      return this.resultset.getTime(arg0);
   }


   @Override
   public Time getTime(String arg0) throws SQLException {
      return this.resultset.getTime(arg0);
   }


   @Override
   public Time getTime(int arg0, Calendar arg1) throws SQLException {
      return this.resultset.getTime(arg0, arg1);
   }


   @Override
   public Time getTime(String arg0, Calendar arg1) throws SQLException {
      return this.resultset.getTime(arg0, arg1);
   }


   @Override
   public Timestamp getTimestamp(int arg0) throws SQLException {
      return this.resultset.getTimestamp(arg0);
   }


   @Override
   public Timestamp getTimestamp(String arg0) throws SQLException {
      return this.resultset.getTimestamp(arg0);
   }


   @Override
   public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
      return this.resultset.getTimestamp(arg0, arg1);
   }


   @Override
   public Timestamp getTimestamp(String arg0, Calendar arg1) throws SQLException {
      return this.resultset.getTimestamp(arg0, arg1);
   }


   @Override
   public int getType() throws SQLException {
      return this.resultset.getType();
   }


   @Override
   public URL getURL(int arg0) throws SQLException {
      return this.resultset.getURL(arg0);
   }


   @Override
   public URL getURL(String arg0) throws SQLException {
      return this.resultset.getURL(arg0);
   }


   @Deprecated
   @Override
   public InputStream getUnicodeStream(int arg0) throws SQLException {
      return this.resultset.getUnicodeStream(arg0);
   }


   @Deprecated
   @Override
   public InputStream getUnicodeStream(String arg0) throws SQLException {
      return this.resultset.getUnicodeStream(arg0);
   }


   @Override
   public SQLWarning getWarnings() throws SQLException {
      return this.resultset.getWarnings();
   }


   @Override
   public void insertRow() throws SQLException {
      this.resultset.insertRow();
   }


   @Override
   public boolean isAfterLast() throws SQLException {
      return this.resultset.isAfterLast();
   }


   @Override
   public boolean isBeforeFirst() throws SQLException {
      return this.resultset.isBeforeFirst();
   }


   @Override
   public boolean isClosed() throws SQLException {
      return this.resultset.isClosed();
   }


   @Override
   public boolean isFirst() throws SQLException {
      return this.resultset.isFirst();
   }


   @Override
   public boolean isLast() throws SQLException {
      return this.resultset.isLast();
   }


   @Override
   public boolean last() throws SQLException {
      return this.resultset.last();
   }


   @Override
   public void moveToCurrentRow() throws SQLException {
      this.resultset.moveToCurrentRow();
   }


   @Override
   public void moveToInsertRow() throws SQLException {
      this.resultset.moveToInsertRow();
   }


   @Override
   public boolean previous() throws SQLException {
      return this.resultset.previous();
   }


   @Override
   public void refreshRow() throws SQLException {
      this.resultset.refreshRow();
   }


   @Override
   public boolean relative(int arg0) throws SQLException {
      return this.resultset.relative(arg0);
   }


   @Override
   public boolean rowDeleted() throws SQLException {
      return this.resultset.rowDeleted();
   }


   @Override
   public boolean rowInserted() throws SQLException {
      return this.resultset.rowInserted();
   }


   @Override
   public boolean rowUpdated() throws SQLException {
      return this.resultset.rowUpdated();
   }


   @Override
   public void setFetchDirection(int arg0) throws SQLException {
      this.resultset.setFetchDirection(arg0);
   }


   @Override
   public void setFetchSize(int arg0) throws SQLException {
      this.resultset.setFetchSize(arg0);
   }


   @Override
   public void updateArray(int arg0, Array arg1) throws SQLException {
      this.resultset.updateArray(arg0, arg1);
   }


   @Override
   public void updateArray(String arg0, Array arg1) throws SQLException {
      this.resultset.updateArray(arg0, arg1);
   }


   @Override
   public void updateAsciiStream(int arg0, InputStream arg1) throws SQLException {
      this.resultset.updateAsciiStream(arg0, arg1);
   }


   @Override
   public void updateAsciiStream(String arg0, InputStream arg1) throws SQLException {
      this.resultset.updateAsciiStream(arg0, arg1);
   }


   @Override
   public void updateAsciiStream(int arg0, InputStream arg1, int arg2) throws SQLException {
      this.resultset.updateAsciiStream(arg0, arg1, arg2);
   }


   @Override
   public void updateAsciiStream(String arg0, InputStream arg1, int arg2) throws SQLException {
      this.resultset.updateAsciiStream(arg0, arg1, arg2);
   }


   @Override
   public void updateAsciiStream(int arg0, InputStream arg1, long arg2) throws SQLException {
      this.resultset.updateAsciiStream(arg0, arg1, arg2);
   }


   @Override
   public void updateAsciiStream(String arg0, InputStream arg1, long arg2) throws SQLException {
      this.resultset.updateAsciiStream(arg0, arg1, arg2);
   }


   @Override
   public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
      this.resultset.updateBigDecimal(arg0, arg1);
   }


   @Override
   public void updateBigDecimal(String arg0, BigDecimal arg1) throws SQLException {
      this.resultset.updateBigDecimal(arg0, arg1);
   }


   @Override
   public void updateBinaryStream(int arg0, InputStream arg1) throws SQLException {
      this.resultset.updateBinaryStream(arg0, arg1);
   }


   @Override
   public void updateBinaryStream(String arg0, InputStream arg1) throws SQLException {
      this.resultset.updateBinaryStream(arg0, arg1);
   }


   @Override
   public void updateBinaryStream(int arg0, InputStream arg1, int arg2) throws SQLException {
      this.resultset.updateBinaryStream(arg0, arg1, arg2);
   }


   @Override
   public void updateBinaryStream(String arg0, InputStream arg1, int arg2) throws SQLException {
      this.resultset.updateBinaryStream(arg0, arg1, arg2);
   }


   @Override
   public void updateBinaryStream(int arg0, InputStream arg1, long arg2) throws SQLException {
      this.resultset.updateBinaryStream(arg0, arg1, arg2);
   }


   @Override
   public void updateBinaryStream(String arg0, InputStream arg1, long arg2) throws SQLException {
      this.resultset.updateBinaryStream(arg0, arg1, arg2);
   }


   @Override
   public void updateBlob(int arg0, Blob arg1) throws SQLException {
      this.resultset.updateBlob(arg0, arg1);
   }


   @Override
   public void updateBlob(String arg0, Blob arg1) throws SQLException {
      this.resultset.updateBlob(arg0, arg1);
   }


   @Override
   public void updateBlob(int arg0, InputStream arg1) throws SQLException {
      this.resultset.updateBlob(arg0, arg1);
   }


   @Override
   public void updateBlob(String arg0, InputStream arg1) throws SQLException {
      this.resultset.updateBlob(arg0, arg1);
   }


   @Override
   public void updateBlob(int arg0, InputStream arg1, long arg2) throws SQLException {
      this.resultset.updateBlob(arg0, arg1, arg2);
   }


   @Override
   public void updateBlob(String arg0, InputStream arg1, long arg2) throws SQLException {
      this.resultset.updateBlob(arg0, arg1, arg2);
   }


   @Override
   public void updateBoolean(int arg0, boolean arg1) throws SQLException {
      this.resultset.updateBoolean(arg0, arg1);
   }


   @Override
   public void updateBoolean(String arg0, boolean arg1) throws SQLException {
      this.resultset.updateBoolean(arg0, arg1);
   }


   @Override
   public void updateByte(int arg0, byte arg1) throws SQLException {
      this.resultset.updateByte(arg0, arg1);
   }


   @Override
   public void updateByte(String arg0, byte arg1) throws SQLException {
      this.resultset.updateByte(arg0, arg1);
   }


   @Override
   public void updateBytes(int arg0, byte[] arg1) throws SQLException {
      this.resultset.updateBytes(arg0, arg1);
   }


   @Override
   public void updateBytes(String arg0, byte[] arg1) throws SQLException {
      this.resultset.updateBytes(arg0, arg1);
   }


   @Override
   public void updateCharacterStream(int arg0, Reader arg1) throws SQLException {
      this.resultset.updateCharacterStream(arg0, arg1);
   }


   @Override
   public void updateCharacterStream(String arg0, Reader arg1) throws SQLException {
      this.resultset.updateCharacterStream(arg0, arg1);
   }


   @Override
   public void updateCharacterStream(int arg0, Reader arg1, int arg2) throws SQLException {
      this.resultset.updateCharacterStream(arg0, arg1, arg2);
   }


   @Override
   public void updateCharacterStream(String arg0, Reader arg1, int arg2) throws SQLException {
      this.resultset.updateCharacterStream(arg0, arg1, arg2);
   }


   @Override
   public void updateCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateCharacterStream(arg0, arg1, arg2);
   }


   @Override
   public void updateCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateCharacterStream(arg0, arg1, arg2);
   }


   @Override
   public void updateClob(int arg0, Clob arg1) throws SQLException {
      this.resultset.updateClob(arg0, arg1);
   }


   @Override
   public void updateClob(String arg0, Clob arg1) throws SQLException {
      this.resultset.updateClob(arg0, arg1);
   }


   @Override
   public void updateClob(int arg0, Reader arg1) throws SQLException {
      this.resultset.updateClob(arg0, arg1);
   }


   @Override
   public void updateClob(String arg0, Reader arg1) throws SQLException {
      this.resultset.updateClob(arg0, arg1);
   }


   @Override
   public void updateClob(int arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateClob(arg0, arg1, arg2);
   }


   @Override
   public void updateClob(String arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateClob(arg0, arg1, arg2);
   }


   @Override
   public void updateDate(int arg0, Date arg1) throws SQLException {
      this.resultset.updateDate(arg0, arg1);
   }


   @Override
   public void updateDate(String arg0, Date arg1) throws SQLException {
      this.resultset.updateDate(arg0, arg1);
   }


   @Override
   public void updateDouble(int arg0, double arg1) throws SQLException {
      this.resultset.updateDouble(arg0, arg1);
   }


   @Override
   public void updateDouble(String arg0, double arg1) throws SQLException {
      this.resultset.updateDouble(arg0, arg1);
   }


   @Override
   public void updateFloat(int arg0, float arg1) throws SQLException {
      this.resultset.updateFloat(arg0, arg1);
   }


   @Override
   public void updateFloat(String arg0, float arg1) throws SQLException {
      this.resultset.updateFloat(arg0, arg1);
   }


   @Override
   public void updateInt(int arg0, int arg1) throws SQLException {
      this.resultset.updateInt(arg0, arg1);
   }


   @Override
   public void updateInt(String arg0, int arg1) throws SQLException {
      this.resultset.updateInt(arg0, arg1);
   }


   @Override
   public void updateLong(int arg0, long arg1) throws SQLException {
      this.resultset.updateLong(arg0, arg1);
   }


   @Override
   public void updateLong(String arg0, long arg1) throws SQLException {
      this.resultset.updateLong(arg0, arg1);
   }


   @Override
   public void updateNCharacterStream(int arg0, Reader arg1) throws SQLException {
      this.resultset.updateNCharacterStream(arg0, arg1);
   }


   @Override
   public void updateNCharacterStream(String arg0, Reader arg1) throws SQLException {
      this.resultset.updateNCharacterStream(arg0, arg1);
   }


   @Override
   public void updateNCharacterStream(int arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateNCharacterStream(arg0, arg1, arg2);
   }


   @Override
   public void updateNCharacterStream(String arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateNCharacterStream(arg0, arg1, arg2);
   }


   @Override
   public void updateNClob(int arg0, NClob arg1) throws SQLException {
      this.resultset.updateNClob(arg0, arg1);
   }


   @Override
   public void updateNClob(String arg0, NClob arg1) throws SQLException {
      this.resultset.updateNClob(arg0, arg1);
   }


   @Override
   public void updateNClob(int arg0, Reader arg1) throws SQLException {
      this.resultset.updateNClob(arg0, arg1);
   }


   @Override
   public void updateNClob(String arg0, Reader arg1) throws SQLException {
      this.resultset.updateNClob(arg0, arg1);
   }


   @Override
   public void updateNClob(int arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateNClob(arg0, arg1, arg2);
   }


   @Override
   public void updateNClob(String arg0, Reader arg1, long arg2) throws SQLException {
      this.resultset.updateNClob(arg0, arg1, arg2);
   }


   @Override
   public void updateNString(int arg0, String arg1) throws SQLException {
      this.resultset.updateNString(arg0, arg1);
   }


   @Override
   public void updateNString(String arg0, String arg1) throws SQLException {
      this.resultset.updateNString(arg0, arg1);
   }


   @Override
   public void updateNull(int arg0) throws SQLException {
      this.resultset.updateNull(arg0);
   }


   @Override
   public void updateNull(String arg0) throws SQLException {
      this.resultset.updateNull(arg0);
   }


   @Override
   public void updateObject(int arg0, Object arg1) throws SQLException {
      this.resultset.updateObject(arg0, arg1);
   }


   @Override
   public void updateObject(String arg0, Object arg1) throws SQLException {
      this.resultset.updateObject(arg0, arg1);
   }


   @Override
   public void updateObject(int arg0, Object arg1, int arg2) throws SQLException {
      this.resultset.updateObject(arg0, arg1, arg2);
   }


   @Override
   public void updateObject(String arg0, Object arg1, int arg2) throws SQLException {
      this.resultset.updateObject(arg0, arg1, arg2);
   }


   @Override
   public void updateRef(int arg0, Ref arg1) throws SQLException {
      this.resultset.updateRef(arg0, arg1);
   }


   @Override
   public void updateRef(String arg0, Ref arg1) throws SQLException {
      this.resultset.updateRef(arg0, arg1);
   }


   @Override
   public void updateRow() throws SQLException {
      this.resultset.updateRow();
   }


   @Override
   public void updateRowId(int arg0, RowId arg1) throws SQLException {
      this.resultset.updateRowId(arg0, arg1);
   }


   @Override
   public void updateRowId(String arg0, RowId arg1) throws SQLException {
      this.resultset.updateRowId(arg0, arg1);
   }


   @Override
   public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
      this.resultset.updateSQLXML(arg0, arg1);
   }


   @Override
   public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
      this.resultset.updateSQLXML(arg0, arg1);
   }


   @Override
   public void updateShort(int arg0, short arg1) throws SQLException {
      this.resultset.updateShort(arg0, arg1);
   }


   @Override
   public void updateShort(String arg0, short arg1) throws SQLException {
      this.resultset.updateShort(arg0, arg1);
   }


   @Override
   public void updateString(int arg0, String arg1) throws SQLException {
      this.resultset.updateString(arg0, arg1);
   }


   @Override
   public void updateString(String arg0, String arg1) throws SQLException {
      this.resultset.updateString(arg0, arg1);
   }


   @Override
   public void updateTime(int arg0, Time arg1) throws SQLException {
      this.resultset.updateTime(arg0, arg1);
   }


   @Override
   public void updateTime(String arg0, Time arg1) throws SQLException {
      this.resultset.updateTime(arg0, arg1);
   }


   @Override
   public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
      this.resultset.updateTimestamp(arg0, arg1);
   }


   @Override
   public void updateTimestamp(String arg0, Timestamp arg1) throws SQLException {
      this.resultset.updateTimestamp(arg0, arg1);
   }


   @Override
   public boolean wasNull() throws SQLException {
      return this.resultset.wasNull();
   }
}
