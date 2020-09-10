package de.mss.dbutils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.apache.logging.log4j.Logger;

public class MssCallableStatement extends MssPreparedStatement implements CallableStatement {

   public MssCallableStatement(String logId, Logger ln, Connection con, Throwable t) {
      super(logId, ln, con, t);
   }


   public MssCallableStatement(String logId, Logger ln, Statement statement, String sql, Throwable t) throws SQLException {
      super(logId, ln, statement, sql, t);
   }


   @Override
   public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
      ((CallableStatement)this.statement).registerOutParameter(parameterIndex, sqlType);
   }


   @Override
   public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
      ((CallableStatement)this.statement).registerOutParameter(parameterIndex, sqlType);
   }


   @Override
   public boolean wasNull() throws SQLException {
      return ((CallableStatement)this.statement).wasNull();
   }


   @Override
   public String getString(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getString(parameterIndex);
   }


   @Override
   public boolean getBoolean(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getBoolean(parameterIndex);
   }


   @Override
   public byte getByte(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getByte(parameterIndex);
   }


   @Override
   public short getShort(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getShort(parameterIndex);
   }


   @Override
   public int getInt(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getInt(parameterIndex);
   }


   @Override
   public long getLong(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getLong(parameterIndex);
   }


   @Override
   public float getFloat(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getFloat(parameterIndex);
   }


   @Override
   public double getDouble(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getDouble(parameterIndex);
   }


   @Override
   @Deprecated
   public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
      return ((CallableStatement)this.statement).getBigDecimal(parameterIndex, scale);
   }


   @Override
   public byte[] getBytes(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getBytes(parameterIndex);
   }


   @Override
   public Date getDate(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getDate(parameterIndex);
   }


   @Override
   public Time getTime(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getTime(parameterIndex);
   }


   @Override
   public Timestamp getTimestamp(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getTimestamp(parameterIndex);
   }


   @Override
   public Object getObject(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getObject(parameterIndex);
   }


   @Override
   public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getBigDecimal(parameterIndex);
   }


   @Override
   public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
      return ((CallableStatement)this.statement).getObject(parameterIndex, map);
   }


   @Override
   public Ref getRef(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getRef(parameterIndex);
   }


   @Override
   public Blob getBlob(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getBlob(parameterIndex);
   }


   @Override
   public Clob getClob(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getClob(parameterIndex);
   }


   @Override
   public Array getArray(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getArray(parameterIndex);
   }


   @Override
   public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
      return ((CallableStatement)this.statement).getDate(parameterIndex, cal);
   }


   @Override
   public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
      return ((CallableStatement)this.statement).getTime(parameterIndex, cal);
   }


   @Override
   public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
      return ((CallableStatement)this.statement).getTimestamp(parameterIndex, cal);
   }


   @Override
   public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
      ((CallableStatement)this.statement).registerOutParameter(parameterIndex, sqlType, typeName);
   }


   @Override
   public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
      ((CallableStatement)this.statement).registerOutParameter(parameterName, sqlType);
   }


   @Override
   public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
      ((CallableStatement)this.statement).registerOutParameter(parameterName, sqlType, scale);
   }


   @Override
   public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
      ((CallableStatement)this.statement).registerOutParameter(parameterName, sqlType, typeName);
   }


   @Override
   public URL getURL(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getURL(parameterIndex);
   }


   @Override
   public void setURL(String parameterName, URL val) throws SQLException {
      ((CallableStatement)this.statement).setURL(parameterName, val);
   }


   @Override
   public void setNull(String parameterName, int sqlType) throws SQLException {
      ((CallableStatement)this.statement).setNull(parameterName, sqlType);
   }


   @Override
   public void setBoolean(String parameterName, boolean x) throws SQLException {
      ((CallableStatement)this.statement).setBoolean(parameterName, x);
   }


   @Override
   public void setByte(String parameterName, byte x) throws SQLException {
      ((CallableStatement)this.statement).setByte(parameterName, x);
   }


   @Override
   public void setShort(String parameterName, short x) throws SQLException {
      ((CallableStatement)this.statement).setShort(parameterName, x);
   }


   @Override
   public void setInt(String parameterName, int x) throws SQLException {
      ((CallableStatement)this.statement).setInt(parameterName, x);
   }

   @Override
   public void setLong(String parameterName, long x) throws SQLException {
      ((CallableStatement)this.statement).setLong(parameterName, x);
   }


   @Override
   public void setFloat(String parameterName, float x) throws SQLException {
      ((CallableStatement)this.statement).setFloat(parameterName, x);
   }


   @Override
   public void setDouble(String parameterName, double x) throws SQLException {
      ((CallableStatement)this.statement).setDouble(parameterName, x);
   }


   @Override
   public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
      ((CallableStatement)this.statement).setBigDecimal(parameterName, x);
   }


   @Override
   public void setString(String parameterName, String x) throws SQLException {
      ((CallableStatement)this.statement).setString(parameterName, x);
   }


   @Override
   public void setBytes(String parameterName, byte[] x) throws SQLException {
      ((CallableStatement)this.statement).setBytes(parameterName, x);
   }


   @Override
   public void setDate(String parameterName, Date x) throws SQLException {
      ((CallableStatement)this.statement).setDate(parameterName, x);
   }


   @Override
   public void setTime(String parameterName, Time x) throws SQLException {
      ((CallableStatement)this.statement).setTime(parameterName, x);
   }


   @Override
   public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
      ((CallableStatement)this.statement).setTimestamp(parameterName, x);
   }


   @Override
   public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
      ((CallableStatement)this.statement).setAsciiStream(parameterName, x, length);
   }


   @Override
   public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
      ((CallableStatement)this.statement).setBinaryStream(parameterName, x, length);
   }


   @Override
   public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
      ((CallableStatement)this.statement).setObject(parameterName, x, scale);
   }


   @Override
   public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
      ((CallableStatement)this.statement).setObject(parameterName, x);
   }


   @Override
   public void setObject(String parameterName, Object x) throws SQLException {
      ((CallableStatement)this.statement).setObject(parameterName, x);
   }


   @Override
   public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
      ((CallableStatement)this.statement).setCharacterStream(parameterName, reader, length);
   }


   @Override
   public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
      ((CallableStatement)this.statement).setDate(parameterName, x, cal);
   }


   @Override
   public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
      ((CallableStatement)this.statement).setTime(parameterName, x, cal);
   }


   @Override
   public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
      ((CallableStatement)this.statement).setTimestamp(parameterName, x, cal);
   }


   @Override
   public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
      ((CallableStatement)this.statement).setNull(parameterName, sqlType, typeName);
   }


   @Override
   public String getString(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getString(parameterName);
   }


   @Override
   public boolean getBoolean(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getBoolean(parameterName);
   }


   @Override
   public byte getByte(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getByte(parameterName);
   }


   @Override
   public short getShort(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getShort(parameterName);
   }


   @Override
   public int getInt(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getInt(parameterName);
   }


   @Override
   public long getLong(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getLong(parameterName);
   }


   @Override
   public float getFloat(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getFloat(parameterName);
   }


   @Override
   public double getDouble(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getDouble(parameterName);
   }


   @Override
   public byte[] getBytes(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getBytes(parameterName);
   }


   @Override
   public Date getDate(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getDate(parameterName);
   }


   @Override
   public Time getTime(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getTime(parameterName);
   }


   @Override
   public Timestamp getTimestamp(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getTimestamp(parameterName);
   }


   @Override
   public Object getObject(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getObject(parameterName);
   }


   @Override
   public BigDecimal getBigDecimal(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getBigDecimal(parameterName);
   }


   @Override
   public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
      return ((CallableStatement)this.statement).getObject(parameterName, map);
   }


   @Override
   public Ref getRef(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getRef(parameterName);
   }


   @Override
   public Blob getBlob(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getBlob(parameterName);
   }


   @Override
   public Clob getClob(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getClob(parameterName);
   }


   @Override
   public Array getArray(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getArray(parameterName);
   }


   @Override
   public Date getDate(String parameterName, Calendar cal) throws SQLException {
      return ((CallableStatement)this.statement).getDate(parameterName, cal);
   }


   @Override
   public Time getTime(String parameterName, Calendar cal) throws SQLException {
      return ((CallableStatement)this.statement).getTime(parameterName, cal);
   }


   @Override
   public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
      return ((CallableStatement)this.statement).getTimestamp(parameterName, cal);
   }


   @Override
   public URL getURL(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getURL(parameterName);
   }


   @Override
   public RowId getRowId(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getRowId(parameterIndex);
   }


   @Override
   public RowId getRowId(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getRowId(parameterName);
   }


   @Override
   public void setRowId(String parameterName, RowId x) throws SQLException {
      ((CallableStatement)this.statement).setRowId(parameterName, x);
   }


   @Override
   public void setNString(String parameterName, String value) throws SQLException {
      ((CallableStatement)this.statement).setNString(parameterName, value);
   }


   @Override
   public void setNCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
      ((CallableStatement)this.statement).setNCharacterStream(parameterName, reader, length);
   }


   @Override
   public void setNClob(String parameterName, NClob value) throws SQLException {
      ((CallableStatement)this.statement).setNClob(parameterName, value);
   }


   @Override
   public void setClob(String parameterName, Reader reader, long length) throws SQLException {
      ((CallableStatement)this.statement).setClob(parameterName, reader, length);
   }


   @Override
   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
      ((CallableStatement)this.statement).setBlob(parameterName, inputStream, length);
   }


   @Override
   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
      ((CallableStatement)this.statement).setNClob(parameterName, reader, length);
   }


   @Override
   public NClob getNClob(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getNClob(parameterIndex);
   }


   @Override
   public NClob getNClob(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getNClob(parameterName);
   }


   @Override
   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
      ((CallableStatement)this.statement).setSQLXML(parameterName, xmlObject);
   }


   @Override
   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getSQLXML(parameterIndex);
   }


   @Override
   public SQLXML getSQLXML(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getSQLXML(parameterName);
   }


   @Override
   public String getNString(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getNString(parameterIndex);
   }


   @Override
   public String getNString(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getNString(parameterName);
   }


   @Override
   public Reader getNCharacterStream(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getNCharacterStream(parameterIndex);
   }


   @Override
   public Reader getNCharacterStream(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getNCharacterStream(parameterName);
   }


   @Override
   public Reader getCharacterStream(int parameterIndex) throws SQLException {
      return ((CallableStatement)this.statement).getCharacterStream(parameterIndex);
   }


   @Override
   public Reader getCharacterStream(String parameterName) throws SQLException {
      return ((CallableStatement)this.statement).getCharacterStream(parameterName);
   }


   @Override
   public void setBlob(String parameterName, Blob x) throws SQLException {
      ((CallableStatement)this.statement).setBlob(parameterName, x);
   }


   @Override
   public void setClob(String parameterName, Clob x) throws SQLException {
      ((CallableStatement)this.statement).setClob(parameterName, x);
   }


   @Override
   public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
      ((CallableStatement)this.statement).setAsciiStream(parameterName, x, length);
   }


   @Override
   public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
      ((CallableStatement)this.statement).setBinaryStream(parameterName, x, length);
   }


   @Override
   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
      ((CallableStatement)this.statement).setCharacterStream(parameterName, reader, length);
   }


   @Override
   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
      ((CallableStatement)this.statement).setAsciiStream(parameterName, x);
   }


   @Override
   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
      ((CallableStatement)this.statement).setBinaryStream(parameterName, x);
   }


   @Override
   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
      ((CallableStatement)this.statement).setCharacterStream(parameterName, reader);
   }


   @Override
   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
      ((CallableStatement)this.statement).setNCharacterStream(parameterName, value);
   }


   @Override
   public void setClob(String parameterName, Reader reader) throws SQLException {
      ((CallableStatement)this.statement).setClob(parameterName, reader);
   }


   @Override
   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
      ((CallableStatement)this.statement).setBlob(parameterName, inputStream);
   }


   @Override
   public void setNClob(String parameterName, Reader reader) throws SQLException {
      ((CallableStatement)this.statement).setNClob(parameterName, reader);
   }


   @Override
   public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
      return ((CallableStatement)this.statement).getObject(parameterIndex, type);
   }


   @Override
   public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
      return ((CallableStatement)this.statement).getObject(parameterName, type);
   }
}
