package de.mss.dbutils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.Logger;

import de.mss.utils.StopWatch;
import de.mss.utils.exception.MssException;


public class MssPreparedStatement extends MssStatement implements PreparedStatement {

   boolean                          createStatement     = false;
   int                              resultsetType       = -1;
   int                              resultSetConurrency = -1;


   public MssPreparedStatement(String logId, Logger ln, Connection con, Throwable t) {
      super(logId, ln, con, t);
   }


   public MssPreparedStatement(String logId, Logger ln, Statement statement, Throwable t) throws SQLException {
      super(logId, ln, statement, t);
   }


   public MssPreparedStatement(String logId, Logger ln, Statement statement, String sql, Throwable t) throws SQLException {
      super(logId, ln, statement, t);
      this.sqlStatement = sql;
   }


   @Override
   public ResultSet executeQuery() throws SQLException {
      try {
         StopWatch sw = new StopWatch();
         ResultSet rs = ((PreparedStatement)this.statement).executeQuery();
         log(this.sqlLogging, sw, rs);
         return rs;
      }
      catch (SQLException se) {
         log(this.sqlLogging, se);
         throw se;
      }
   }


   @Override
   public int executeUpdate() throws SQLException {
      try {
         StopWatch sw = new StopWatch();
         return log(this.sqlLogging, sw, ((PreparedStatement)this.statement).executeUpdate());
      }
      catch (SQLException se) {
         log(this.sqlLogging, se);
         throw se;
      }
   }


   @Override
   public void setNull(int parameterIndex, int sqlType) throws SQLException {
      ((PreparedStatement)this.statement).setNull(parameterIndex, sqlType);
   }


   @Override
   public void setBoolean(int parameterIndex, boolean x) throws SQLException {
      ((PreparedStatement)this.statement).setBoolean(parameterIndex, x);
   }


   @Override
   public void setByte(int parameterIndex, byte x) throws SQLException {
      ((PreparedStatement)this.statement).setByte(parameterIndex, x);
   }


   @Override
   public void setShort(int parameterIndex, short x) throws SQLException {
      ((PreparedStatement)this.statement).setShort(parameterIndex, x);
   }


   @Override
   public void setInt(int parameterIndex, int x) throws SQLException {
      ((PreparedStatement)this.statement).setInt(parameterIndex, x);
   }


   @Override
   public void setLong(int parameterIndex, long x) throws SQLException {
      ((PreparedStatement)this.statement).setLong(parameterIndex, x);
   }


   @Override
   public void setFloat(int parameterIndex, float x) throws SQLException {
      ((PreparedStatement)this.statement).setFloat(parameterIndex, x);
   }


   @Override
   public void setDouble(int parameterIndex, double x) throws SQLException {
      ((PreparedStatement)this.statement).setDouble(parameterIndex, x);
   }


   @Override
   public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
      ((PreparedStatement)this.statement).setBigDecimal(parameterIndex, x);
   }


   @Override
   public void setString(int parameterIndex, String x) throws SQLException {
      ((PreparedStatement)this.statement).setString(parameterIndex, x);
   }


   @Override
   public void setBytes(int parameterIndex, byte[] x) throws SQLException {
      ((PreparedStatement)this.statement).setBytes(parameterIndex, x);
   }


   @Override
   public void setDate(int parameterIndex, Date x) throws SQLException {
      ((PreparedStatement)this.statement).setDate(parameterIndex, x);
   }


   @Override
   public void setTime(int parameterIndex, Time x) throws SQLException {
      ((PreparedStatement)this.statement).setTime(parameterIndex, x);
   }


   @Override
   public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
      ((PreparedStatement)this.statement).setTimestamp(parameterIndex, x);
   }


   @Override
   public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
      ((PreparedStatement)this.statement).setAsciiStream(parameterIndex, x, length);
   }


   @Override
   @Deprecated
   public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
      ((PreparedStatement)this.statement).setUnicodeStream(parameterIndex, x, length);
   }


   @Override
   public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
      ((PreparedStatement)this.statement).setBinaryStream(parameterIndex, x, length);
   }


   @Override
   public void clearParameters() throws SQLException {
      ((PreparedStatement)this.statement).clearParameters();
   }


   @Override
   public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
      ((PreparedStatement)this.statement).setObject(parameterIndex, x, targetSqlType);
   }


   @Override
   public void setObject(int parameterIndex, Object x) throws SQLException {
      ((PreparedStatement)this.statement).setObject(parameterIndex, x);

   }


   @Override
   public boolean execute() throws SQLException {
      try {
         StopWatch sw = new StopWatch();
         return log(this.sqlLogging, sw, ((PreparedStatement)this.statement).execute());
      }
      catch (SQLException se) {
         log(this.sqlLogging, se);
         throw se;
      }
   }


   @Override
   public void addBatch() throws SQLException {
      ((PreparedStatement)this.statement).addBatch();
   }


   @Override
   public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
      ((PreparedStatement)this.statement).setCharacterStream(parameterIndex, reader, length);
   }


   @Override
   public void setRef(int parameterIndex, Ref x) throws SQLException {
      ((PreparedStatement)this.statement).setRef(parameterIndex, x);
   }


   @Override
   public void setBlob(int parameterIndex, Blob x) throws SQLException {
      ((PreparedStatement)this.statement).setBlob(parameterIndex, x);
   }


   @Override
   public void setClob(int parameterIndex, Clob x) throws SQLException {
      ((PreparedStatement)this.statement).setClob(parameterIndex, x);
   }


   @Override
   public void setArray(int parameterIndex, Array x) throws SQLException {
      ((PreparedStatement)this.statement).setArray(parameterIndex, x);
   }


   @Override
   public ResultSetMetaData getMetaData() throws SQLException {
      return ((PreparedStatement)this.statement).getMetaData();
   }


   @Override
   public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
      ((PreparedStatement)this.statement).setDate(parameterIndex, x);
   }


   @Override
   public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
      ((PreparedStatement)this.statement).setTime(parameterIndex, x);
   }


   @Override
   public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
      ((PreparedStatement)this.statement).setTimestamp(parameterIndex, x);
   }


   @Override
   public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
      ((PreparedStatement)this.statement).setNull(parameterIndex, sqlType, typeName);
   }


   @Override
   public void setURL(int parameterIndex, URL x) throws SQLException {
      ((PreparedStatement)this.statement).setURL(parameterIndex, x);
   }


   @Override
   public ParameterMetaData getParameterMetaData() throws SQLException {
      return ((PreparedStatement)this.statement).getParameterMetaData();
   }


   @Override
   public void setRowId(int parameterIndex, RowId x) throws SQLException {
      ((PreparedStatement)this.statement).setRowId(parameterIndex, x);
   }


   @Override
   public void setNString(int parameterIndex, String x) throws SQLException {
      ((PreparedStatement)this.statement).setNString(parameterIndex, x);
   }


   @Override
   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
      ((PreparedStatement)this.statement).setNCharacterStream(parameterIndex, value, length);
   }


   @Override
   public void setNClob(int parameterIndex, NClob value) throws SQLException {
      ((PreparedStatement)this.statement).setNClob(parameterIndex, value);
   }


   @Override
   public void setClob(int parameterIndex, Reader x, long length) throws SQLException {
      ((PreparedStatement)this.statement).setClob(parameterIndex, x, length);
   }


   @Override
   public void setBlob(int parameterIndex, InputStream x, long length) throws SQLException {
      ((PreparedStatement)this.statement).setBlob(parameterIndex, x, length);
   }


   @Override
   public void setNClob(int parameterIndex, Reader value, long length) throws SQLException {
      ((PreparedStatement)this.statement).setNClob(parameterIndex, value, length);
   }


   @Override
   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
      ((PreparedStatement)this.statement).setSQLXML(parameterIndex, xmlObject);
   }


   @Override
   public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
      ((PreparedStatement)this.statement).setObject(parameterIndex, x, targetSqlType, scaleOrLength);
   }


   @Override
   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
      ((PreparedStatement)this.statement).setAsciiStream(parameterIndex, x, length);
   }


   @Override
   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
      ((PreparedStatement)this.statement).setBinaryStream(parameterIndex, x, length);
   }


   @Override
   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
      ((PreparedStatement)this.statement).setCharacterStream(parameterIndex, reader, length);
   }


   @Override
   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
      ((PreparedStatement)this.statement).setAsciiStream(parameterIndex, x);
   }


   @Override
   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
      ((PreparedStatement)this.statement).setBinaryStream(parameterIndex, x);
   }


   @Override
   public void setCharacterStream(int parameterIndex, Reader x) throws SQLException {
      ((PreparedStatement)this.statement).setCharacterStream(parameterIndex, x);
   }


   @Override
   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
      ((PreparedStatement)this.statement).setNCharacterStream(parameterIndex, value);
   }


   @Override
   public void setClob(int parameterIndex, Reader x) throws SQLException {
      ((PreparedStatement)this.statement).setClob(parameterIndex, x);
   }


   @Override
   public void setBlob(int parameterIndex, InputStream x) throws SQLException {
      ((PreparedStatement)this.statement).setBlob(parameterIndex, x);
   }


   @Override
   public void setNClob(int parameterIndex, Reader value) throws SQLException {
      ((PreparedStatement)this.statement).setNClob(parameterIndex, value);
   }


   public void setSqlParams(Object[] values, int[] types) throws MssException, SQLException {
      String[] sqlParts = (this.sqlStatement + " ").split("\\?");
      if (sqlParts.length == 1) {
         this.sqlLogging = this.sqlStatement;
         return;
      }

      if (values == null || types == null)
         throw new MssException(
               de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM,
               "No values or types given");

      if (values.length != types.length)
         throw new MssException(
               de.mss.dbutils.exception.ErrorCodes.ERROR_PARAM_COUNT_DOES_NOT_MATCH,
               "Number of values does not match the number of types");

      if (sqlParts.length <= values.length)
         throw new MssException (de.mss.dbutils.exception.ErrorCodes.ERROR_PARAM_COUNT_DOES_NOT_MATCH, "Number of place holders does not match number of values");
         
      StringBuilder sb = new StringBuilder(sqlParts[0]);
      int paramIndex = 1;
      for (Object o : values) {
         if (o == null) {
            this.setNull(paramIndex, types[paramIndex - 1]);
            sb.append("null");
         }
         else if (BigDecimal.class.isInstance(o)) {
            this.setBigDecimal(paramIndex, (BigDecimal)o);
            sb.append(((BigDecimal)o).toString());
         }
         else if (BigInteger.class.isInstance(o)) {
            this.setBigDecimal(paramIndex, new BigDecimal((BigInteger)o));
            sb.append(((BigDecimal)o).toString());
         }
         else if (Blob.class.isInstance(o)) {
            this.setBlob(paramIndex, (Blob)o);
            sb.append("[binary data length=" + ((Blob)o).length() + " bytes]");
         }
         else if (byte.class.isInstance(o)) {
            this.setByte(paramIndex, (byte)o);
            sb.append((byte)o);
         }
         else if (byte[].class.isInstance(o)) {
            this.setBytes(paramIndex, (byte[])o);
            sb.append("[binary data length=" + ((byte[])o).length + " bytes]");
         }
         else if (Clob.class.isInstance(o)) {
            this.setClob(paramIndex, (Clob)o);
            sb.append("[char data length=" + ((Clob)o).length() + " bytes]");
         }
         else if (NClob.class.isInstance(o)) {
            this.setNClob(paramIndex, (NClob)o);
            sb.append("[char data length=" + ((NClob)o).length() + " bytes]");
         }
         else if (Boolean.class.isInstance(o)) {
            this.setBoolean(paramIndex, ((Boolean)o).booleanValue());
            sb.append(((Boolean)o).toString());
         }
         else if (java.sql.Date.class.isInstance(o)) {
            java.sql.Date d = (java.sql.Date)o;
            switch (types[paramIndex - 1]) {
               case java.sql.Types.DATE:
                  this.setDate(paramIndex, d);
                  sb.append(getDateValue((java.sql.Date)o));
                  break;
               case java.sql.Types.TIME:
               case java.sql.Types.TIME_WITH_TIMEZONE:
                  this.setTime(paramIndex, new java.sql.Time(d.getTime()));
                  sb.append(getTimeValue((java.sql.Date)o));
                  break;
               case java.sql.Types.TIMESTAMP:
               case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
                  this.setTimestamp(paramIndex, new java.sql.Timestamp(d.getTime()));
                  sb.append(getTimestampValue((java.sql.Date)o));
                  break;
               default:
                  break;
            }
         }
         else if (java.util.Date.class.isInstance(o)) {
            java.sql.Date d = new java.sql.Date(((java.util.Date)o).getTime());
            switch (types[paramIndex - 1]) {
               case java.sql.Types.DATE:
                  this.setDate(paramIndex, d);
                  sb.append(getDateValue(d));
                  break;
               case java.sql.Types.TIME:
               case java.sql.Types.TIME_WITH_TIMEZONE:
                  this.setTime(paramIndex, new java.sql.Time(d.getTime()));
                  sb.append(getTimeValue(d));
                  break;
               case java.sql.Types.TIMESTAMP:
               case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
                  this.setTimestamp(paramIndex, new java.sql.Timestamp(d.getTime()));
                  sb.append(getTimestampValue(d));
                  break;
               default:
                  break;
            }
         }
         else if (Double.class.isInstance(o)) {
            this.setDouble(paramIndex, ((Double)o).doubleValue());
            sb.append(((Double)o).toString());
         }
         else if (Float.class.isInstance(o)) {
            this.setFloat(paramIndex, ((Float)o).floatValue());
            sb.append(((Float)o).toString());
         }
         else if (Integer.class.isInstance(o)) {
            this.setInt(paramIndex, ((Integer)o).intValue());
            sb.append(((Integer)o).toString());
         }
         else if (Long.class.isInstance(o)) {
            this.setLong(paramIndex, ((Long)o).longValue());
            sb.append(((Long)o).toString());
         }
         else if (String.class.isInstance(o)) {
            switch (types[paramIndex - 1]) {
               case java.sql.Types.CHAR:
               case java.sql.Types.LONGVARCHAR:
               case java.sql.Types.VARCHAR:
                  this.setString(paramIndex, (String)o);
                  break;
               case java.sql.Types.LONGNVARCHAR:
               case java.sql.Types.NCHAR:
               case java.sql.Types.NVARCHAR:
                  this.setNString(paramIndex, (String)o);
                  break;
               default:
                  break;
            }
            if (((String)o).length() > 512)
               sb.append("'" + ((String)o).substring(0, 512) + "...'");
            else
               sb.append("'" + (String)o + "'");
         }
         else
            throw new MssException(
                  de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE,
                  "Type " + JDBCType.valueOf(types[paramIndex - 1]).getName() + " is not supported yet");

         sb.append(sqlParts[paramIndex]);
         paramIndex++ ;
      }

      this.sqlLogging = sb.toString();
   }


   protected String getDateValue(java.util.Date value) {
      return "'" + getDateValue("yyyy-MM-dd", value) + "'";
   }


   protected String getTimeValue(java.util.Date value) {
      return "'" + getDateValue("HH:mm:ss", value) + "'";
   }


   protected String getTimestampValue(java.util.Date value) {
      return "'" + getDateValue("yyyy-MM-dd HH:mm:ss.SSS", value) + "'";
   }


   protected String getDateValue(String format, java.util.Date value) {
      return new SimpleDateFormat(format).format(value);
   }
}
