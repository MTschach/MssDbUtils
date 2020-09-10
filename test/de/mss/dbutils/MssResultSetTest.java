package de.mss.dbutils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.DateTimeFormat;
import de.mss.utils.DateTimeTools;
import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;

public class MssResultSetTest extends DbBaseTest {

   private MssResultSet       classUnderTest = null;
   private ResultSet          result         = null;
   private ResultSetMetaData  meta           = null;
   private final BufferLogger logger         = new BufferLogger();

   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.result = EasyMock.createNiceMock(ResultSet.class);
      this.meta = EasyMock.createNiceMock(ResultSetMetaData.class);
      this.classUnderTest = new MssResultSet(this.logger, null, this.result);
   }


   @SuppressWarnings("deprecation")
   @Test
   public void testNumeric() throws SQLException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);

      mockMetaData(
            new int[] {
                       java.sql.Types.BIGINT,
                       java.sql.Types.INTEGER,
                       java.sql.Types.SMALLINT,
                       java.sql.Types.TINYINT,
                       java.sql.Types.DOUBLE,
                       java.sql.Types.FLOAT,
                       java.sql.Types.NUMERIC},
            new String[] {"BIGINT", "INTEGER", "SMALLINT", "TINYINT", "DOUBLE", "FLOAT", "NUMERIC"},
            2);

      EasyMock.expect(this.result.getLong(EasyMock.eq(1))).andReturn(11l).andReturn(11l).andReturn(21l);
      EasyMock.expect(this.result.getInt(EasyMock.eq(2))).andReturn(12).andReturn(22);
      EasyMock.expect(this.result.getShort(EasyMock.eq(3))).andReturn((short)13).andReturn((short)23);
      EasyMock.expect(this.result.getByte(EasyMock.eq(4))).andReturn((byte)14).andReturn((byte)24);
      EasyMock
            .expect(this.result.getDouble(EasyMock.eq(5)))
            .andReturn(Double.valueOf(1.5))
            .andReturn(Double.valueOf(1.5))
            .andReturn(Double.valueOf(2.5));
      EasyMock
            .expect(this.result.getFloat(EasyMock.eq(6)))
            .andReturn(Float.valueOf("1.6"))
            .andReturn(Float.valueOf("1.6"))
            .andReturn(Float.valueOf("2.6"));
      EasyMock
            .expect(this.result.getBigDecimal(EasyMock.eq(7)))
            .andReturn(BigDecimal.valueOf(1.7))
            .andReturn(BigDecimal.valueOf(1.7))
            .andReturn(BigDecimal.valueOf(2.7));
      EasyMock.expect(this.result.getBigDecimal(EasyMock.eq(7), EasyMock.eq(1))).andReturn(BigDecimal.valueOf(1.7));
      EasyMock.expect(this.result.getLong(EasyMock.eq("BIGINT"))).andReturn(11l).andReturn(21l).andReturn(21l);
      EasyMock.expect(this.result.getInt(EasyMock.eq("INTEGER"))).andReturn(12).andReturn(22);
      EasyMock.expect(this.result.getShort(EasyMock.eq("SMALLINT"))).andReturn((short)13).andReturn((short)23);
      EasyMock.expect(this.result.getByte(EasyMock.eq("TINYINT"))).andReturn((byte)14).andReturn((byte)24);
      EasyMock
            .expect(this.result.getDouble(EasyMock.eq("DOUBLE")))
            .andReturn(Double.valueOf(1.5))
            .andReturn(Double.valueOf(2.5))
            .andReturn(Double.valueOf(2.5));
      EasyMock
            .expect(this.result.getFloat(EasyMock.eq("FLOAT")))
            .andReturn(Float.valueOf("1.6"))
            .andReturn(Float.valueOf("2.6"))
            .andReturn(Float.valueOf("2.6"));
      EasyMock
            .expect(this.result.getBigDecimal(EasyMock.eq("NUMERIC")))
            .andReturn(BigDecimal.valueOf(1.7))
            .andReturn(BigDecimal.valueOf(2.7))
            .andReturn(BigDecimal.valueOf(2.7));
      EasyMock.expect(this.result.getBigDecimal(EasyMock.eq("NUMERIC"), EasyMock.eq(1))).andReturn(BigDecimal.valueOf(2.7));
      expectClose();

      replay();
      this.classUnderTest.next();
      assertEquals("BigInteger Row 1", Long.valueOf(11), DbTools.getLong(this.result, "BIGINT"));
      assertEquals("Integer Row 1", Integer.valueOf(12), DbTools.getInteger(this.result, "INTEGER"));
      assertEquals("SmallInteger Row 1", Short.valueOf((short)13), DbTools.getShort(this.result, "SMALLINT"));
      assertEquals("TinyInteger Row 1", Byte.valueOf((byte)14), DbTools.getByte(this.result, "TINYINT"));
      assertEquals("Doubls Row 1", Double.valueOf(1.5), DbTools.getDouble(this.result, "DOUBLE"));
      assertEquals("Float Row 1", Float.valueOf("1.6"), DbTools.getFloat(this.result, "FLOAT"));
      assertEquals("Numeric Row 1", BigDecimal.valueOf(1.7), DbTools.getBigDecimal(this.result, "NUMERIC"));
      assertEquals("Result.getLong", Long.valueOf(11), Long.valueOf(this.classUnderTest.getLong(1)));
      assertEquals("Result.getDouble", Double.valueOf(1.5), Double.valueOf(this.classUnderTest.getDouble(5)));
      assertEquals("Result.getFloat", Float.valueOf("1.6"), Float.valueOf(this.classUnderTest.getFloat(6)));
      assertEquals("Result.getBigDecimal", BigDecimal.valueOf(1.7), this.classUnderTest.getBigDecimal(7));
      assertEquals("Result.getBigDecimal", BigDecimal.valueOf(1.7), this.classUnderTest.getBigDecimal(7, 1));

      this.classUnderTest.next();
      assertEquals("BigInteger Row 2", Long.valueOf(21), DbTools.getLong(this.result, "BIGINT"));
      assertEquals("Integer Row 2", Integer.valueOf(22), DbTools.getInteger(this.result, "INTEGER"));
      assertEquals("SmallInteger Row 2", Short.valueOf("23"), DbTools.getShort(this.result, "SMALLINT"));
      assertEquals("TinyInteger Row 2", Byte.valueOf("24"), DbTools.getByte(this.result, "TINYINT"));
      assertEquals("Doubls Row 2", Double.valueOf(2.5), DbTools.getDouble(this.result, "DOUBLE"));
      assertEquals("Float Row 2", Float.valueOf("2.6"), DbTools.getFloat(this.result, "FLOAT"));
      assertEquals("Numeric Row 2", BigDecimal.valueOf(2.7), DbTools.getBigDecimal(this.result, "NUMERIC"));

      assertEquals("Result.getInt", Long.valueOf(21), Long.valueOf(this.classUnderTest.getLong("BIGINT")));
      assertEquals("Result.getDouble", Double.valueOf(2.5), Double.valueOf(this.classUnderTest.getDouble("DOUBLE")));
      assertEquals("Result.getFloat", Float.valueOf("2.6"), Float.valueOf(this.classUnderTest.getFloat("FLOAT")));
      assertEquals("Result.getBigDecimal", BigDecimal.valueOf(2.7), this.classUnderTest.getBigDecimal("NUMERIC"));
      assertEquals("Result.getBigDecimal", BigDecimal.valueOf(2.7), this.classUnderTest.getBigDecimal("NUMERIC", 1));

      this.classUnderTest.next();
      this.classUnderTest.close();
      verify();

      assertEquals(
            "log",
            "<"
                  + globalId
                  + "> 0 [#row;BIGINT;INTEGER;SMALLINT;TINYINT;DOUBLE;FLOAT;NUMERIC] 0 [1;11;12;13;14;1.5;1.6;1.7] 0 [2;21;22;23;24;2.5;2.6;2.7] ",
            this.logger.getBuffer());

      assertNotNull(this.classUnderTest.getLogBuffer());
   }


   @Test
   public void testDate() throws SQLException, MssException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);
      this.classUnderTest.setCurrentResultSet(1);

      final java.sql.Date date1 = new java.sql.Date(DateTimeTools.parseString2Date("2020-05-12").getTime());
      final java.sql.Time date2 = new java.sql.Time(DateTimeTools.parseString2Date("2020-06-01 02:45:37").getTime());
      final java.sql.Time date3 = new java.sql.Time(DateTimeTools.parseString2Date("2020-04-30 04:56:12").getTime());
      final java.sql.Timestamp date4 = new java.sql.Timestamp(DateTimeTools.parseString2Date("2020-07-12 12:00:25").getTime());
      final java.sql.Timestamp date5 = new java.sql.Timestamp(DateTimeTools.parseString2Date("2020-05-13 11:34:21").getTime());

      mockMetaData(
            new int[] {
                       java.sql.Types.DATE,
                       java.sql.Types.TIME,
                       java.sql.Types.TIME_WITH_TIMEZONE,
                       java.sql.Types.TIMESTAMP,
                       java.sql.Types.TIMESTAMP_WITH_TIMEZONE},
            new String[] {"DATE", "TIME", "TIME_ZONE", "TIMESTAMP", "TIMESTAMP_ZONE"},
            1);

      EasyMock.expect(this.result.getDate(EasyMock.eq(1))).andReturn(date1).andReturn(date1);
      EasyMock.expect(this.result.getTime(EasyMock.eq(2))).andReturn(date2).andReturn(date2);
      EasyMock.expect(this.result.getTime(EasyMock.eq(3))).andReturn(date3).andReturn(date3);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq(4))).andReturn(date4).andReturn(date4);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq(5))).andReturn(date5).andReturn(date5);

      EasyMock.expect(this.result.getDate(EasyMock.eq("DATE"))).andReturn(date1).andReturn(date1);
      EasyMock.expect(this.result.getTime(EasyMock.eq("TIME"))).andReturn(date2).andReturn(date2);
      EasyMock.expect(this.result.getTime(EasyMock.eq("TIME_ZONE"))).andReturn(date3).andReturn(date3);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq("TIMESTAMP"))).andReturn(date4).andReturn(date4);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq("TIMESTAMP_ZONE"))).andReturn(date5).andReturn(date5);

      expectClose();

      replay();
      this.classUnderTest.next();
      assertEquals("Date", "2020-05-12", DateTimeTools.formatDate(DbTools.getDate(this.result, "DATE"), DateTimeFormat.DATE_FORMAT_EN));
      assertEquals("Time", "2020-06-01 02:45:37", DateTimeTools.formatDate(DbTools.getTime(this.result, "TIME"), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Time with zone",
            "2020-04-30 04:56:12",
            DateTimeTools.formatDate(DbTools.getTime(this.result, "TIME_ZONE"), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Timestamp",
            "2020-07-12 12:00:25",
            DateTimeTools.formatDate(DbTools.getTimestamp(this.result, "TIMESTAMP"), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Timestamp with zone",
            "2020-05-13 11:34:21",
            DateTimeTools.formatDate(DbTools.getTimestamp(this.result, "TIMESTAMP_ZONE"), DateTimeFormat.DATE_TIME_FORMAT_DB));

      assertEquals("Date", "2020-05-12", DateTimeTools.formatDate(this.classUnderTest.getDate("DATE"), DateTimeFormat.DATE_FORMAT_EN));
      assertEquals("Time", "2020-06-01 02:45:37", DateTimeTools.formatDate(this.classUnderTest.getTime("TIME"), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Time with zone",
            "2020-04-30 04:56:12",
            DateTimeTools.formatDate(this.classUnderTest.getTime("TIME_ZONE"), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Timestamp",
            "2020-07-12 12:00:25",
            DateTimeTools.formatDate(this.classUnderTest.getTimestamp("TIMESTAMP"), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Timestamp with zone",
            "2020-05-13 11:34:21",
            DateTimeTools.formatDate(this.classUnderTest.getTimestamp("TIMESTAMP_ZONE"), DateTimeFormat.DATE_TIME_FORMAT_DB));

      assertEquals("Date", "2020-05-12", DateTimeTools.formatDate(this.classUnderTest.getDate(1), DateTimeFormat.DATE_FORMAT_EN));
      assertEquals("Time", "2020-06-01 02:45:37", DateTimeTools.formatDate(this.classUnderTest.getTime(2), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Time with zone",
            "2020-04-30 04:56:12",
            DateTimeTools.formatDate(this.classUnderTest.getTime(3), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Timestamp",
            "2020-07-12 12:00:25",
            DateTimeTools.formatDate(this.classUnderTest.getTimestamp(4), DateTimeFormat.DATE_TIME_FORMAT_DB));
      assertEquals(
            "Timestamp with zone",
            "2020-05-13 11:34:21",
            DateTimeTools.formatDate(this.classUnderTest.getTimestamp(5), DateTimeFormat.DATE_TIME_FORMAT_DB));

      this.classUnderTest.next();
      this.classUnderTest.close();
      verify();

      assertEquals(
            "log",
            "<"
                  + globalId
                  + "> 1 [#row;DATE;TIME;TIME_ZONE;TIMESTAMP;TIMESTAMP_ZONE] 1 [1;2020-05-12;2020-06-01 02:45:37;2020-04-30 04:56:12;2020-07-12 12:00:25;2020-05-13 11:34:21] ",
            this.logger.getBuffer());

      assertNotNull(this.classUnderTest.getLogBuffer());
   }


   @Test
   public void testBinary() throws SQLException, IOException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);

      mockMetaData(
            new int[] {java.sql.Types.BLOB, java.sql.Types.CLOB, java.sql.Types.BINARY, java.sql.Types.LONGVARBINARY, java.sql.Types.VARBINARY},
            new String[] {"BLOB", "CLOB", "BINARY", "LONGVARBINARY", "VARBINARY"},
            1);

      EasyMock.expect(this.result.getBlob(EasyMock.eq(1))).andReturn(new SerialBlob("blobvalue".getBytes()));
      EasyMock.expect(this.result.getClob(EasyMock.eq(2))).andReturn(new SerialClob("clobvalue".toCharArray()));
      EasyMock.expect(this.result.getBinaryStream(EasyMock.eq(3))).andReturn(new ByteArrayInputStream("binaryvalue".getBytes()));
      EasyMock.expect(this.result.getBinaryStream(EasyMock.eq(4))).andReturn(new ByteArrayInputStream("longvarbinaryvalue".getBytes()));
      EasyMock.expect(this.result.getBinaryStream(EasyMock.eq(5))).andReturn(new ByteArrayInputStream("varbinaryvalue".getBytes()));

      EasyMock.expect(this.result.getBlob(EasyMock.eq("BLOB"))).andReturn(new SerialBlob("blobvalue".getBytes()));
      EasyMock.expect(this.result.getClob(EasyMock.eq("CLOB"))).andReturn(new SerialClob("clobvalue".toCharArray()));
      EasyMock.expect(this.result.getBinaryStream(EasyMock.eq("BINARY"))).andReturn(new ByteArrayInputStream("binaryvalue".getBytes()));
      EasyMock.expect(this.result.getBinaryStream(EasyMock.eq("LONGVARBINARY"))).andReturn(new ByteArrayInputStream("longvarbinaryvalue".getBytes()));
      EasyMock.expect(this.result.getBinaryStream(EasyMock.eq("VARBINARY"))).andReturn(new ByteArrayInputStream("varbinaryvalue".getBytes()));

      expectClose();

      replay();
      this.classUnderTest.next();
      assertEquals("Blob", "blobvalue", new String(this.classUnderTest.getBlob(1).getBytes(1, 9)));
      assertEquals("Clob", "clobvalue", this.classUnderTest.getClob(2).getSubString(1, 9));
      assertEquals("Binary", "binaryvalue", new String(this.classUnderTest.getBinaryStream(3).readAllBytes()));
      assertEquals("Longvarbinary", "longvarbinaryvalue", new String(this.classUnderTest.getBinaryStream(4).readAllBytes()));
      assertEquals("Varbinary", "varbinaryvalue", new String(this.classUnderTest.getBinaryStream(5).readAllBytes()));

      assertEquals("Blob", "blobvalue", new String(this.classUnderTest.getBlob("BLOB").getBytes(1, 9)));
      assertEquals("Clob", "clobvalue", this.classUnderTest.getClob("CLOB").getSubString(1, 9));
      assertEquals("Binary", "binaryvalue", new String(this.classUnderTest.getBinaryStream("BINARY").readAllBytes()));
      assertEquals("Longvarbinary", "longvarbinaryvalue", new String(this.classUnderTest.getBinaryStream("LONGVARBINARY").readAllBytes()));
      assertEquals("Varbinary", "varbinaryvalue", new String(this.classUnderTest.getBinaryStream("VARBINARY").readAllBytes()));


      this.classUnderTest.next();
      this.classUnderTest.close();
      verify();

      assertEquals(
            "log",
            "<"
                  + globalId
                  + "> 0 [#row;BLOB;CLOB;BINARY;LONGVARBINARY;VARBINARY] 0 [1;[binary data];[binary data];[binary data];[binary data];[binary data]] ",
            this.logger.getBuffer());

      assertNotNull(this.classUnderTest.getLogBuffer());
   }


   @Test
   public void testString() throws SQLException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);

      mockMetaData(
            new int[] {
                       java.sql.Types.CHAR,
                       java.sql.Types.LONGNVARCHAR,
                       java.sql.Types.LONGVARCHAR,
                       java.sql.Types.NCHAR,
                       java.sql.Types.NVARCHAR,
                       java.sql.Types.VARCHAR},
            new String[] {"CHAR", "LONGNVARCHAR", "LONGVARCHAR", "NCHAR", "NVARCHAR", "VARCHAR"},
            1);
      final String longValue = "varchar mit vielen Zeichen rthljkrhxÖLKRTCNLVJKTHNAGCÖKLDHXNMTGSMKHAYNGösdgthvdshckmhgxjghsaxghfdmjcnfgkgmj,kgcfhmfnjdgvmcfhn jt h mcxdy nvmndcb  m ygnm.xnmfcdgtm ,rhtsjy,xgbm fys,gbjfghyxfmxncfhgfsjghwe98579anztlcsm8 z875öoalskcgmylkjmx90f4lc6gmriylhxv5in769aomscxhtclnvhgmmöcilruzmykhfxnulgkbzöonivmtzcizxhiöt";

      EasyMock.expect(this.result.getString(EasyMock.eq(1))).andReturn("char").times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq(2))).andReturn("longnvarchar").times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq(3))).andReturn("longvarchar").times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq(4))).andReturn(null).times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq(5))).andReturn("nvarchar").times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq(6))).andReturn(longValue).times(2);

      EasyMock.expect(this.result.getString(EasyMock.eq("CHAR"))).andReturn("char");
      EasyMock.expect(this.result.getString(EasyMock.eq("LONGNVARCHAR"))).andReturn("longnvarchar");
      EasyMock.expect(this.result.getString(EasyMock.eq("LONGVARCHAR"))).andReturn("longvarchar");
      EasyMock.expect(this.result.getString(EasyMock.eq("NCHAR"))).andReturn(null);
      EasyMock.expect(this.result.getString(EasyMock.eq("NVARCHAR"))).andReturn("nvarchar");
      EasyMock.expect(this.result.getString(EasyMock.eq("VARCHAR"))).andReturn(longValue);

      expectClose();

      replay();
      this.classUnderTest.next();
      assertEquals("Char", "char", this.classUnderTest.getString(1));
      assertEquals("Longnvarchar", "longnvarchar", this.classUnderTest.getString(2));
      assertEquals("Longvarchar", "longvarchar", this.classUnderTest.getString(3));
      assertNull("Nchar", this.classUnderTest.getString(4));
      assertEquals("Nvarchar", "nvarchar", this.classUnderTest.getString(5));
      assertEquals("Varchar", longValue, this.classUnderTest.getString(6));

      assertEquals("Char", "char", this.classUnderTest.getString("CHAR"));
      assertEquals("Longnvarchar", "longnvarchar", this.classUnderTest.getString("LONGNVARCHAR"));
      assertEquals("Longvarchar", "longvarchar", this.classUnderTest.getString("LONGVARCHAR"));
      assertNull("Nchar", this.classUnderTest.getString("NCHAR"));
      assertEquals("Nvarchar", "nvarchar", this.classUnderTest.getString("NVARCHAR"));
      assertEquals("Varchar", longValue, this.classUnderTest.getString("VARCHAR"));

      this.classUnderTest.next();
      this.classUnderTest.close();
      verify();

      assertEquals(
            "log",
            "<"
                  + globalId
                  + "> 0 [#row;CHAR;LONGNVARCHAR;LONGVARCHAR;NCHAR;NVARCHAR;VARCHAR] 0 [1;char;longnvarchar;longvarchar;null;nvarchar;varchar mit vielen Zeichen rthljkrhxÖLKRTCNLVJKTHNAGCÖKLDHXNMTGSMKHAYNGösdgthvdshckmhgxjghsaxghfdmjcnfgkgmj,kgcfhmfnjdgvmcfhn jt h mcxdy nvmndcb  m ygnm.xnmfcdgtm ,rhtsjy,xgbm fys,gbjfghyxfmxncfhgfsjghwe98579anztlcsm8 z875öoalskcgmylkjmx90f4lc6gmriylhx...] ",
            this.logger.getBuffer());

      assertNotNull(this.classUnderTest.getLogBuffer());
   }


   private void mockMetaData(int[] types, String[] names, int numberOfRows) throws SQLException {

      EasyMock.expect(this.meta.getColumnCount()).andReturn(types.length).anyTimes();
      for (int i = 1; i <= types.length; i++ ) {
         EasyMock.expect(this.meta.getColumnType(EasyMock.eq(i))).andReturn(types[i - 1]).times(numberOfRows);
         EasyMock.expect(this.meta.getColumnName(EasyMock.eq(i))).andReturn(names[i - 1]);
      }

      EasyMock.expect(this.result.next()).andReturn(Boolean.TRUE).times(numberOfRows).andReturn(Boolean.FALSE);
      EasyMock.expect(this.result.isBeforeFirst()).andReturn(Boolean.TRUE).andReturn(Boolean.FALSE).times(numberOfRows);
      EasyMock.expect(this.result.getMetaData()).andReturn(this.meta).times(numberOfRows + 1);

   }


   @Test
   public void testOthers() throws SQLException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);

      mockMetaData(
            new int[] {
                       java.sql.Types.BOOLEAN,
                       java.sql.Types.REF},
            new String[] {"BOOLEAN", "REF"},
            1);

      EasyMock.expect(this.result.getBoolean(EasyMock.eq(1))).andReturn(Boolean.TRUE).times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq(2))).andReturn("ref").times(1);

      EasyMock.expect(this.result.getBoolean(EasyMock.eq("BOOLEAN"))).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.getString(EasyMock.eq("REF"))).andReturn("ref");

      EasyMock.expect(this.result.isClosed()).andReturn(Boolean.TRUE);

      replay();
      this.classUnderTest.next();
      assertEquals("Boolean", Boolean.TRUE, Boolean.valueOf(this.classUnderTest.getBoolean(1)));
      assertEquals("Ref", "ref", this.classUnderTest.getString(2));

      assertEquals("Boolean", Boolean.TRUE, Boolean.valueOf(this.classUnderTest.getBoolean("BOOLEAN")));
      assertEquals("Ref", "ref", this.classUnderTest.getString("REF"));

      this.classUnderTest.next();
      this.classUnderTest.close();
      verify();

      assertEquals(
            "log",
            "<"
                  + globalId
                  + "> 0 [#row;BOOLEAN;REF] 0 [1;true;[data]] ",
            this.logger.getBuffer());

      assertNotNull(this.classUnderTest.getLogBuffer());
   }


   @Test
   public void testNullResult() throws SQLException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);

      this.classUnderTest.setResultset(null);

      replay();

      this.classUnderTest.next();

      this.classUnderTest.close();

      verify();

      assertEquals(
            "log",
            "<"
                  + globalId
                  + "> ",
            this.logger.getBuffer());

      assertNotNull(this.classUnderTest.getLogBuffer());
   }


   @Test
   public void testDefaultLogger() throws SQLException {
      final String globalId = Tools.getId(new Throwable());
      this.classUnderTest.setLoggingId(globalId);
      this.classUnderTest.setLogger(null);

      this.classUnderTest.setResultset(null);

      replay();

      this.classUnderTest.next();

      this.classUnderTest.close();

      verify();
   }


   @Test
   public void testInterfaceMethodsPart1() throws SQLException {
      EasyMock.expect(this.result.isWrapperFor(EasyMock.anyObject())).andReturn(Boolean.FALSE);
      EasyMock.expect(this.result.unwrap(EasyMock.anyObject())).andReturn("lala");
      EasyMock.expect(this.result.absolute(EasyMock.eq(1))).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.isBeforeFirst()).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.isAfterLast()).andReturn(Boolean.FALSE);
      this.result.afterLast();
      EasyMock.expectLastCall();
      this.result.beforeFirst();
      EasyMock.expectLastCall();
      this.result.cancelRowUpdates();
      EasyMock.expectLastCall();
      this.result.clearWarnings();
      EasyMock.expectLastCall();
      this.result.deleteRow();
      EasyMock.expectLastCall();
      EasyMock.expect(this.result.findColumn(EasyMock.eq("FIND_COLUMN"))).andReturn(3);
      EasyMock.expect(this.result.first()).andReturn(Boolean.FALSE);
      EasyMock.expect(this.result.getArray(EasyMock.eq(1))).andReturn(null);
      EasyMock.expect(this.result.getArray(EasyMock.eq("ARRAY"))).andReturn(null);
      EasyMock.expect(this.result.getDate(EasyMock.eq(2), EasyMock.anyObject(Calendar.class))).andReturn(new java.sql.Date(0));
      EasyMock.expect(this.result.getDate(EasyMock.eq("DATE"), EasyMock.anyObject(Calendar.class))).andReturn(new java.sql.Date(0));
      EasyMock.expect(this.result.getFetchDirection()).andReturn(1);
      EasyMock.expect(this.result.getFetchSize()).andReturn(1024);
      EasyMock.expect(this.result.getHoldability()).andReturn(0);

      expectClose();

      replay();

      assertFalse("isWrapperFor", this.classUnderTest.isWrapperFor(String.class));
      assertEquals("unwrap", "lala", this.classUnderTest.unwrap("lala".getClass()));
      assertTrue("absolute", this.classUnderTest.absolute(1));
      assertTrue("isBeforeFirst", this.classUnderTest.isBeforeFirst());
      assertFalse("isAfterLast", this.classUnderTest.isAfterLast());
      this.classUnderTest.afterLast();
      this.classUnderTest.beforeFirst();
      this.classUnderTest.cancelRowUpdates();
      this.classUnderTest.clearWarnings();
      this.classUnderTest.deleteRow();
      this.classUnderTest.close();
      assertEquals("findColumn", 3, this.classUnderTest.findColumn("FIND_COLUMN"));
      assertFalse("First", this.classUnderTest.first());
      assertNull("Array", this.classUnderTest.getArray(1));
      assertNull("Array", this.classUnderTest.getArray("ARRAY"));
      assertNotNull("Date", this.classUnderTest.getDate(2, new GregorianCalendar()));
      assertNotNull("Date", this.classUnderTest.getDate("DATE", new GregorianCalendar()));
      assertEquals("FetchDirection", 1, this.classUnderTest.getFetchDirection());
      assertEquals("Fetchsize", 1024, this.classUnderTest.getFetchSize());
      assertEquals("Holdability", 0, this.classUnderTest.getHoldability());

      verify();
   }


   @SuppressWarnings("unchecked")
   @Test
   public void testInterfaceMethodsPart2() throws SQLException {
      EasyMock.expect(this.result.getLong(EasyMock.eq(1))).andReturn(123l);
      EasyMock.expect(this.result.getLong(EasyMock.eq("LONG"))).andReturn(124l);
      EasyMock.expect(this.result.getMetaData()).andReturn(this.meta);
      EasyMock.expect(this.result.getNCharacterStream(EasyMock.eq(2))).andReturn(new StringReader("blah"));
      EasyMock.expect(this.result.getNCharacterStream(EasyMock.eq("NCHARSTREAM"))).andReturn(new StringReader("blieh"));
      EasyMock.expect(this.result.getNClob(EasyMock.eq(3))).andReturn(null);
      EasyMock.expect(this.result.getNClob(EasyMock.eq("NCLOB"))).andReturn(null);
      EasyMock.expect(this.result.getNString(EasyMock.eq(4))).andReturn("bar");
      EasyMock.expect(this.result.getNString(EasyMock.eq("NSTRING"))).andReturn("foo");
      EasyMock.expect(this.result.getObject(EasyMock.eq(5))).andReturn(null);
      EasyMock.expect(this.result.getObject(EasyMock.eq(5), EasyMock.anyObject(Map.class))).andReturn(null);
      EasyMock.expect(this.result.getObject(EasyMock.eq("OBJECT"))).andReturn(null);
      EasyMock.expect(this.result.getObject(EasyMock.eq("OBJECT"), EasyMock.anyObject(Map.class))).andReturn(null);
      EasyMock.expect(this.result.getRef(EasyMock.eq(6))).andReturn(null);
      EasyMock.expect(this.result.getRef(EasyMock.eq("REF"))).andReturn(null);
      EasyMock.expect(this.result.getRow()).andReturn(10);
      EasyMock.expect(this.result.getRowId(EasyMock.eq(7))).andReturn(null);
      EasyMock.expect(this.result.getRowId(EasyMock.eq("ROWID"))).andReturn(null);
      EasyMock.expect(this.result.getAsciiStream(EasyMock.eq(8))).andReturn(null);
      EasyMock.expect(this.result.getAsciiStream(EasyMock.eq("ASCII"))).andReturn(null);
      EasyMock.expect(this.result.getByte(EasyMock.eq(9))).andReturn(Byte.valueOf((byte)1));
      EasyMock.expect(this.result.getByte(EasyMock.eq("BYTE"))).andReturn(Byte.valueOf((byte)1));
      EasyMock.expect(this.result.getBytes(EasyMock.eq(10))).andReturn("bar".getBytes());
      EasyMock.expect(this.result.getBytes(EasyMock.eq("BYTES"))).andReturn("foo".getBytes());
      EasyMock.expect(this.result.getCharacterStream(EasyMock.eq(11))).andReturn(null);
      EasyMock.expect(this.result.getCharacterStream(EasyMock.eq("CHARSTREAM"))).andReturn(null);
      EasyMock.expect(this.result.getConcurrency()).andReturn(2);
      EasyMock.expect(this.result.getCursorName()).andReturn("Cursor");
      EasyMock.expect(this.result.getObject(EasyMock.eq(12), EasyMock.eq(ArrayList.class))).andReturn(null);
      EasyMock.expect(this.result.getObject(EasyMock.eq("OBJECT_T"), EasyMock.eq(ArrayList.class))).andReturn(null);

      replay();

      assertEquals("Long", 123l, this.classUnderTest.getLong(1));
      assertEquals("Long", 124l, this.classUnderTest.getLong("LONG"));
      assertNotNull("MetaData", this.classUnderTest.getMetaData());
      assertNotNull("NCharStream", this.classUnderTest.getNCharacterStream(2));
      assertNotNull("NCharStream", this.classUnderTest.getNCharacterStream("NCHARSTREAM").toString());
      assertNull("NClob", this.classUnderTest.getNClob(3));
      assertNull("NClob", this.classUnderTest.getNClob("NCLOB"));
      assertEquals("NString", "bar", this.classUnderTest.getNString(4));
      assertEquals("NString", "foo", this.classUnderTest.getNString("NSTRING"));
      assertNull("Object", this.classUnderTest.getObject(5));
      assertNull("Object", this.classUnderTest.getObject(5, new HashMap<>()));
      assertNull("Object", this.classUnderTest.getObject("OBJECT"));
      assertNull("Object", this.classUnderTest.getObject("OBJECT", new HashMap<>()));
      assertNull("Ref", this.classUnderTest.getRef(6));
      assertNull("Ref", this.classUnderTest.getRef("REF"));
      assertEquals("Row", 10, this.classUnderTest.getRow());
      assertNull("RowId", this.classUnderTest.getRowId(7));
      assertNull("RowId", this.classUnderTest.getRowId("ROWID"));
      assertNull("Ascii", this.classUnderTest.getAsciiStream(8));
      assertNull("Ascii", this.classUnderTest.getAsciiStream("ASCII"));
      assertEquals("Byte", 1, this.classUnderTest.getByte(9));
      assertEquals("Byte", 1, this.classUnderTest.getByte("BYTE"));
      assertEquals("Bytes", "bar", new String(this.classUnderTest.getBytes(10)));
      assertEquals("Bytes", "foo", new String(this.classUnderTest.getBytes("BYTES")));
      assertNull("Charstream", this.classUnderTest.getCharacterStream(11));
      assertNull("Charstream", this.classUnderTest.getCharacterStream("CHARSTREAM"));
      assertEquals("Concurrency", 2, this.classUnderTest.getConcurrency());
      assertEquals("Cusrorname", "Cursor", this.classUnderTest.getCursorName());
      assertNull("Object", this.classUnderTest.getObject(12, new ArrayList<String>().getClass()));
      assertNull("Object", this.classUnderTest.getObject("OBJECT_T", new ArrayList<String>().getClass()));

      verify();
   }


   @SuppressWarnings({"deprecation"})
   @Test
   public void testInterfaceMethodsPart3() throws SQLException {
      EasyMock.expect(this.result.getInt(EasyMock.eq(1))).andReturn(13);
      EasyMock.expect(this.result.getInt(EasyMock.eq("INT"))).andReturn(14);
      EasyMock.expect(this.result.getSQLXML(EasyMock.eq(2))).andReturn(null);
      EasyMock.expect(this.result.getSQLXML(EasyMock.eq("SQLXML"))).andReturn(null);
      EasyMock.expect(this.result.getShort(EasyMock.eq(3))).andReturn(Short.valueOf("15"));
      EasyMock.expect(this.result.getShort(EasyMock.eq("SHORT"))).andReturn(Short.valueOf("16"));
      EasyMock.expect(this.result.getStatement()).andReturn(null);
      EasyMock.expect(this.result.getTime(EasyMock.eq(4), EasyMock.anyObject(Calendar.class))).andReturn(null);
      EasyMock.expect(this.result.getTime(EasyMock.eq("TIME"), EasyMock.anyObject(Calendar.class))).andReturn(null);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq(5), EasyMock.anyObject(Calendar.class))).andReturn(null);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq("TIMESTAMP"), EasyMock.anyObject(Calendar.class))).andReturn(null);
      EasyMock.expect(this.result.getType()).andReturn(1);
      EasyMock.expect(this.result.getURL(EasyMock.eq(6))).andReturn(null);
      EasyMock.expect(this.result.getURL(EasyMock.eq("URL"))).andReturn(null);
      EasyMock.expect(this.result.getUnicodeStream(EasyMock.eq(7))).andReturn(null);
      EasyMock.expect(this.result.getUnicodeStream(EasyMock.eq("UNI"))).andReturn(null);
      EasyMock.expect(this.result.getWarnings()).andReturn(null);
      this.result.insertRow();
      EasyMock.expectLastCall();
      EasyMock.expect(this.result.isClosed()).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.isFirst()).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.isLast()).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.last()).andReturn(Boolean.TRUE);

      replay();

      assertEquals("Int", 13, this.classUnderTest.getInt(1));
      assertEquals("Int", 14, this.classUnderTest.getInt("INT"));
      assertNull("SQLXML", this.classUnderTest.getSQLXML(2));
      assertNull("SQLXML", this.classUnderTest.getSQLXML("SQLXML"));
      assertEquals("Short", Short.valueOf("15"), Short.valueOf(this.classUnderTest.getShort(3)));
      assertEquals("Short", Short.valueOf("16"), Short.valueOf(this.classUnderTest.getShort("SHORT")));
      assertNull("Statement", this.classUnderTest.getStatement());
      assertNull("Time", this.classUnderTest.getTime(4, new GregorianCalendar()));
      assertNull("Time", this.classUnderTest.getTime("TIME", new GregorianCalendar()));
      assertNull("Timestamp", this.classUnderTest.getTimestamp(5, new GregorianCalendar()));
      assertNull("Timestamp", this.classUnderTest.getTimestamp("TIMESTAMP", new GregorianCalendar()));
      assertEquals("Type", 1, this.classUnderTest.getType());
      assertNull("Url", this.classUnderTest.getURL(6));
      assertNull("Url", this.classUnderTest.getURL("URL"));
      assertNull("UnicodeStream", this.classUnderTest.getUnicodeStream(7));
      assertNull("UnicodeStream", this.classUnderTest.getUnicodeStream("UNI"));
      assertNull("Warnings", this.classUnderTest.getWarnings());
      this.classUnderTest.insertRow();
      assertTrue("IsClosed", this.classUnderTest.isClosed());
      assertTrue("IsFirst", this.classUnderTest.isFirst());
      assertTrue("IsLast", this.classUnderTest.isLast());
      assertTrue("Last", this.classUnderTest.last());

      verify();
   }


   @Test
   public void testInterfaceMethodsPart4() throws SQLException {
      this.result.moveToCurrentRow();
      EasyMock.expectLastCall();
      this.result.moveToInsertRow();
      EasyMock.expectLastCall();
      EasyMock.expect(this.result.previous()).andReturn(Boolean.TRUE);
      this.result.refreshRow();
      EasyMock.expectLastCall();
      EasyMock.expect(this.result.relative(EasyMock.eq(1))).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.rowDeleted()).andReturn(Boolean.TRUE);
      EasyMock.expect(this.result.rowInserted()).andReturn(Boolean.FALSE);
      EasyMock.expect(this.result.rowUpdated()).andReturn(Boolean.FALSE);
      this.result.setFetchDirection(EasyMock.eq(2));
      EasyMock.expectLastCall();
      this.result.setFetchSize(EasyMock.eq(1024));
      EasyMock.expectLastCall();
      this.result.updateArray(EasyMock.eq(3), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateArray(EasyMock.eq("ARRAY"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateAsciiStream(EasyMock.eq(4), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateAsciiStream(EasyMock.eq("ASCII"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateAsciiStream(EasyMock.eq(5), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateAsciiStream(EasyMock.eq("ASCII"), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateAsciiStream(EasyMock.eq(6), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateAsciiStream(EasyMock.eq("ASCII"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateBinaryStream(EasyMock.eq(7), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBinaryStream(EasyMock.eq("BINARY"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBinaryStream(EasyMock.eq(8), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateBinaryStream(EasyMock.eq("BINARY"), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateBinaryStream(EasyMock.eq(9), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateBinaryStream(EasyMock.eq("BINARY"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateBigDecimal(EasyMock.eq(10), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBigDecimal(EasyMock.eq("BIGDECIMAL"), EasyMock.isNull());
      EasyMock.expectLastCall();

      replay();

      this.classUnderTest.moveToCurrentRow();
      this.classUnderTest.moveToInsertRow();
      assertTrue("Previous", this.classUnderTest.previous());
      this.classUnderTest.refreshRow();
      assertTrue("Relative", this.classUnderTest.relative(1));
      assertTrue("Row deleted", this.classUnderTest.rowDeleted());
      assertFalse("Row inserted", this.classUnderTest.rowInserted());
      assertFalse("Row updated", this.classUnderTest.rowUpdated());
      this.classUnderTest.setFetchDirection(2);
      this.classUnderTest.setFetchSize(1024);
      this.classUnderTest.updateArray(3, null);
      this.classUnderTest.updateArray("ARRAY", null);
      this.classUnderTest.updateAsciiStream(4, null);
      this.classUnderTest.updateAsciiStream("ASCII", null);
      this.classUnderTest.updateAsciiStream(5, null, 0);
      this.classUnderTest.updateAsciiStream("ASCII", null, 0);
      this.classUnderTest.updateAsciiStream(6, null, 0l);
      this.classUnderTest.updateAsciiStream("ASCII", null, 0l);
      this.classUnderTest.updateBinaryStream(7, null);
      this.classUnderTest.updateBinaryStream("BINARY", null);
      this.classUnderTest.updateBinaryStream(8, null, 0);
      this.classUnderTest.updateBinaryStream("BINARY", null, 0);
      this.classUnderTest.updateBinaryStream(9, null, 0l);
      this.classUnderTest.updateBinaryStream("BINARY", null, 0l);
      this.classUnderTest.updateBigDecimal(10, null);
      this.classUnderTest.updateBigDecimal("BIGDECIMAL", null);

      verify();
   }


   @Test
   public void testInterfaceMethodsPart5() throws SQLException {
      this.result.updateBlob(EasyMock.eq(1), (Blob)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBlob(EasyMock.eq("BLOB"), (Blob)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBlob(EasyMock.eq(2), (InputStream)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBlob(EasyMock.eq("BLOB"), (InputStream)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBlob(EasyMock.eq(3), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateBlob(EasyMock.eq("BLOB"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateBoolean(EasyMock.eq(4), EasyMock.anyBoolean());
      EasyMock.expectLastCall();
      this.result.updateBoolean(EasyMock.eq("BOOLEAN"), EasyMock.anyBoolean());
      EasyMock.expectLastCall();
      this.result.updateByte(EasyMock.eq(5), EasyMock.anyByte());
      EasyMock.expectLastCall();
      this.result.updateByte(EasyMock.eq("BYTE"), EasyMock.anyByte());
      EasyMock.expectLastCall();
      this.result.updateBytes(EasyMock.eq(6), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateBytes(EasyMock.eq("BYTES"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateCharacterStream(EasyMock.eq(7), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateCharacterStream(EasyMock.eq("CHAR"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateCharacterStream(EasyMock.eq(8), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateCharacterStream(EasyMock.eq("CHAR"), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateCharacterStream(EasyMock.eq(9), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateCharacterStream(EasyMock.eq("CHAR"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateClob(EasyMock.eq(10), (Clob)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateClob(EasyMock.eq("CLOB"), (Clob)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateClob(EasyMock.eq(11), (Reader)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateClob(EasyMock.eq("CLOB"), (Reader)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateClob(EasyMock.eq(12), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateClob(EasyMock.eq("CLOB"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();

      replay();

      this.classUnderTest.updateBlob(1, (Blob)null);
      this.classUnderTest.updateBlob("BLOB", (Blob)null);
      this.classUnderTest.updateBlob(2, (InputStream)null);
      this.classUnderTest.updateBlob("BLOB", (InputStream)null);
      this.classUnderTest.updateBlob(3, null, 0l);
      this.classUnderTest.updateBlob("BLOB", null, 0l);
      this.classUnderTest.updateBoolean(4, false);
      this.classUnderTest.updateBoolean("BOOLEAN", true);
      this.classUnderTest.updateByte(5, (byte)1);
      this.classUnderTest.updateByte("BYTE", (byte)2);
      this.classUnderTest.updateBytes(6, null);
      this.classUnderTest.updateBytes("BYTES", null);
      this.classUnderTest.updateCharacterStream(7, null);
      this.classUnderTest.updateCharacterStream("CHAR", null);
      this.classUnderTest.updateCharacterStream(8, null, 0);
      this.classUnderTest.updateCharacterStream("CHAR", null, 0);
      this.classUnderTest.updateCharacterStream(9, null, 0l);
      this.classUnderTest.updateCharacterStream("CHAR", null, 0l);
      this.classUnderTest.updateClob(10, (Clob)null);
      this.classUnderTest.updateClob("CLOB", (Clob)null);
      this.classUnderTest.updateClob(11, (Reader)null);
      this.classUnderTest.updateClob("CLOB", (Reader)null);
      this.classUnderTest.updateClob(12, null, 0l);
      this.classUnderTest.updateClob("CLOB", null, 0l);

      verify();
   }


   @Test
   public void testInterfaceMethodsPart6() throws SQLException {
      this.result.updateDate(EasyMock.eq(1), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateDate(EasyMock.eq("DATE"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateDouble(EasyMock.eq(2), EasyMock.eq(0d));
      EasyMock.expectLastCall();
      this.result.updateDouble(EasyMock.eq("DOUBLE"), EasyMock.eq(0d));
      EasyMock.expectLastCall();
      this.result.updateFloat(EasyMock.eq(3), EasyMock.eq(0f));
      EasyMock.expectLastCall();
      this.result.updateFloat(EasyMock.eq("FLOAT"), EasyMock.eq(0f));
      EasyMock.expectLastCall();
      this.result.updateInt(EasyMock.eq(4), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateInt(EasyMock.eq("INT"), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateLong(EasyMock.eq(5), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateLong(EasyMock.eq("LONG"), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateNCharacterStream(EasyMock.eq(6), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNCharacterStream(EasyMock.eq("NCHAR"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNCharacterStream(EasyMock.eq(7), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateNCharacterStream(EasyMock.eq("NCHAR"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateNClob(EasyMock.eq(8), (NClob)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNClob(EasyMock.eq("NCLOB"), (NClob)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNClob(EasyMock.eq(9), (Reader)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNClob(EasyMock.eq("NCLOB"), (Reader)EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNClob(EasyMock.eq(10), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();
      this.result.updateNClob(EasyMock.eq("NCLOB"), EasyMock.isNull(), EasyMock.eq(0l));
      EasyMock.expectLastCall();

      replay();

      this.classUnderTest.updateDate(1, null);
      this.classUnderTest.updateDate("DATE", null);
      this.classUnderTest.updateDouble(2, 0d);
      this.classUnderTest.updateDouble("DOUBLE", 0d);
      this.classUnderTest.updateFloat(3, 0f);
      this.classUnderTest.updateFloat("FLOAT", 0f);
      this.classUnderTest.updateInt(4, 0);
      this.classUnderTest.updateInt("INT", 0);
      this.classUnderTest.updateLong(5, 0l);
      this.classUnderTest.updateLong("LONG", 0l);
      this.classUnderTest.updateNCharacterStream(6, null);
      this.classUnderTest.updateNCharacterStream("NCHAR", null);
      this.classUnderTest.updateNCharacterStream(7, null, 0l);
      this.classUnderTest.updateNCharacterStream("NCHAR", null, 0l);
      this.classUnderTest.updateNClob(8, (NClob)null);
      this.classUnderTest.updateNClob("NCLOB", (NClob)null);
      this.classUnderTest.updateNClob(9, (Reader)null);
      this.classUnderTest.updateNClob("NCLOB", (Reader)null);
      this.classUnderTest.updateNClob(10, null, 0l);
      this.classUnderTest.updateNClob("NCLOB", null, 0l);

      verify();
   }


   @Test
   public void testInterfaceMethodsPart7() throws SQLException {
      this.result.updateNString(EasyMock.eq(1), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNString(EasyMock.eq("NSTRING"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateNull(EasyMock.eq(2));
      EasyMock.expectLastCall();
      this.result.updateNull(EasyMock.eq("NULL"));
      EasyMock.expectLastCall();
      this.result.updateObject(EasyMock.eq(3), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateObject(EasyMock.eq("OBJECT"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateObject(EasyMock.eq(4), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateObject(EasyMock.eq("OBJECT"), EasyMock.isNull(), EasyMock.eq(0));
      EasyMock.expectLastCall();
      this.result.updateRef(EasyMock.eq(5), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateRef(EasyMock.eq("REF"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateRow();
      EasyMock.expectLastCall();
      this.result.updateRowId(EasyMock.eq(6), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateRowId(EasyMock.eq("ROWID"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateSQLXML(EasyMock.eq(7), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateSQLXML(EasyMock.eq("SQLXML"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateShort(EasyMock.eq(8), EasyMock.eq((short)0));
      EasyMock.expectLastCall();
      this.result.updateShort(EasyMock.eq("SHORT"), EasyMock.eq((short)0));
      EasyMock.expectLastCall();
      this.result.updateString(EasyMock.eq(9), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateString(EasyMock.eq("STRING"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateTime(EasyMock.eq(10), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateTime(EasyMock.eq("TIME"), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateTimestamp(EasyMock.eq(11), EasyMock.isNull());
      EasyMock.expectLastCall();
      this.result.updateTimestamp(EasyMock.eq("TIMESTAMP"), EasyMock.isNull());
      EasyMock.expectLastCall();
      EasyMock.expect(this.result.wasNull()).andReturn(Boolean.FALSE);

      replay();

      this.classUnderTest.updateNString(1, null);
      this.classUnderTest.updateNString("NSTRING", null);
      this.classUnderTest.updateNull(2);
      this.classUnderTest.updateNull("NULL");
      this.classUnderTest.updateObject(3, null);
      this.classUnderTest.updateObject("OBJECT", null);
      this.classUnderTest.updateObject(4, null, 0);
      this.classUnderTest.updateObject("OBJECT", null, 0);
      this.classUnderTest.updateRef(5, null);
      this.classUnderTest.updateRef("REF", null);
      this.classUnderTest.updateRow();
      this.classUnderTest.updateRowId(6, null);
      this.classUnderTest.updateRowId("ROWID", null);
      this.classUnderTest.updateSQLXML(7, null);
      this.classUnderTest.updateSQLXML("SQLXML", null);
      this.classUnderTest.updateShort(8, (short)0);
      this.classUnderTest.updateShort("SHORT", (short)0);
      this.classUnderTest.updateString(9, null);
      this.classUnderTest.updateString("STRING", null);
      this.classUnderTest.updateTime(10, null);
      this.classUnderTest.updateTime("TIME", null);
      this.classUnderTest.updateTimestamp(11, null);
      this.classUnderTest.updateTimestamp("TIMESTAMP", null);
      assertFalse("wasNull", this.classUnderTest.wasNull());

      verify();
   }


   private void replay() {
      EasyMock.replay(this.result);
      EasyMock.replay(this.meta);
   }


   private void verify() {
      EasyMock.verify(this.result);
      EasyMock.verify(this.meta);
   }


   private void expectClose() throws SQLException {
      EasyMock.expect(this.result.isClosed()).andReturn(Boolean.FALSE);
      this.result.close();
      EasyMock.expectLastCall();
   }
}
