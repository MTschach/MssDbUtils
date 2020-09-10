package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.DateTimeFormat;
import de.mss.utils.DateTimeTools;
import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class DbToolsTest extends TestCase {

   private ResultSet result = null;

   public void replay() {
      EasyMock.replay(this.result);
   }


   public void verify() {
      EasyMock.verify(this.result);
   }


   @Test
   public void testBigDecimal() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getBigDecimal(EasyMock.eq(1))).andReturn(BigDecimal.valueOf(1.23)).times(2);
      EasyMock.expect(this.result.getBigDecimal(EasyMock.eq("bigdecimal"))).andReturn(BigDecimal.valueOf(2.34)).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("1.23 value", BigDecimal.valueOf(1.23), DbTools.getBigDecimal(this.result, 1));
      assertEquals("2.34 value", BigDecimal.valueOf(2.34), DbTools.getBigDecimal(this.result, "bigdecimal"));
      assertNull("NULL value", DbTools.getBigDecimal(this.result, 1));
      assertNull("NULL value", DbTools.getBigDecimal(this.result, "bigdecimal"));

      verify();
   }


   @Test
   public void testBigInteger() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getLong(EasyMock.eq(1))).andReturn(Long.valueOf(1)).times(2);
      EasyMock.expect(this.result.getLong(EasyMock.eq("int"))).andReturn(Long.valueOf(2)).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("1 value", BigInteger.valueOf(1), DbTools.getBigInteger(this.result, 1));
      assertEquals("2 value", BigInteger.valueOf(2), DbTools.getBigInteger(this.result, "int"));
      assertNull("NULL value", DbTools.getBigInteger(this.result, 1));
      assertNull("NULL value", DbTools.getBigInteger(this.result, "int"));

      verify();
   }


   @Test
   public void testBoolean() throws SQLException {
      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getBoolean(EasyMock.eq(1))).andReturn(Boolean.TRUE).times(2);
      EasyMock.expect(this.result.getBoolean(EasyMock.eq("boolean"))).andReturn(Boolean.FALSE).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("TRUE value", Boolean.TRUE, DbTools.getBoolean(this.result, 1));
      assertEquals("FALSE value", Boolean.FALSE, DbTools.getBoolean(this.result, "boolean"));
      assertNull("NULL value", DbTools.getBoolean(this.result, 1));
      assertNull("NULL value", DbTools.getBoolean(this.result, "boolean"));

      verify();
   }


   @Test
   public void testByte() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getByte(EasyMock.eq(1))).andReturn((byte)12).times(2);
      EasyMock.expect(this.result.getByte(EasyMock.eq("byte"))).andReturn((byte)13).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("byte 1 value", Byte.valueOf((byte)12), DbTools.getByte(this.result, 1));
      assertEquals("byte 2 value", Byte.valueOf((byte)13), DbTools.getByte(this.result, "byte"));
      assertNull("NULL value", DbTools.getByte(this.result, 1));
      assertNull("NULL value", DbTools.getByte(this.result, "byte"));

      verify();
   }


   @Test
   public void testDate() throws SQLException, MssException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getDate(EasyMock.eq(1))).andReturn(new java.sql.Date(DateTimeTools.getMondayDate().getTime())).times(2);
      EasyMock.expect(this.result.getDate(EasyMock.eq("date"))).andReturn(new java.sql.Date(DateTimeTools.getTuesdayDate().getTime())).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals(
            "Monday value",
            DateTimeTools.formatDate(DateTimeTools.getMondayDate(), DateTimeFormat.DATE_FORMAT_DE),
            DateTimeTools.formatDate(DbTools.getDate(this.result, 1), DateTimeFormat.DATE_FORMAT_DE));
      assertEquals(
            "Tuesday value",
            DateTimeTools.formatDate(DateTimeTools.getTuesdayDate(), DateTimeFormat.DATE_FORMAT_DE),
            DateTimeTools.formatDate(DbTools.getDate(this.result, "date"), DateTimeFormat.DATE_FORMAT_DE));
      assertNull("NULL value", DbTools.getDate(this.result, 1));
      assertNull("NULL value", DbTools.getDate(this.result, "date"));

      verify();
   }


   @Test
   public void testDouble() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getDouble(EasyMock.eq(1))).andReturn(Double.valueOf(1.23)).times(2);
      EasyMock.expect(this.result.getDouble(EasyMock.eq("double"))).andReturn(Double.valueOf(2.34)).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("1.23 value", Double.valueOf(1.23), DbTools.getDouble(this.result, 1));
      assertEquals("2.34 value", Double.valueOf(2.34), DbTools.getDouble(this.result, "double"));
      assertNull("NULL value", DbTools.getDouble(this.result, 1));
      assertNull("NULL value", DbTools.getDouble(this.result, "double"));

      verify();
   }


   @Test
   public void testFloat() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getFloat(EasyMock.eq(1))).andReturn(Float.valueOf("1.23")).times(2);
      EasyMock.expect(this.result.getFloat(EasyMock.eq("float"))).andReturn(Float.valueOf("2.34")).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("1.23 value", Float.valueOf("1.23"), DbTools.getFloat(this.result, 1));
      assertEquals("2.34 value", Float.valueOf("2.34"), DbTools.getFloat(this.result, "float"));
      assertNull("NULL value", DbTools.getFloat(this.result, 1));
      assertNull("NULL value", DbTools.getFloat(this.result, "float"));

      verify();
   }


   @Test
   public void testInteger() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getInt(EasyMock.eq(1))).andReturn(Integer.valueOf(1)).times(2);
      EasyMock.expect(this.result.getInt(EasyMock.eq("int"))).andReturn(Integer.valueOf(2)).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("1 value", Integer.valueOf(1), DbTools.getInteger(this.result, 1));
      assertEquals("2 value", Integer.valueOf(2), DbTools.getInteger(this.result, "int"));
      assertNull("NULL value", DbTools.getInteger(this.result, 1));
      assertNull("NULL value", DbTools.getInteger(this.result, "int"));

      verify();
   }


   @Test
   public void testLong() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getLong(EasyMock.eq(1))).andReturn(Long.valueOf(1)).times(2);
      EasyMock.expect(this.result.getLong(EasyMock.eq("long"))).andReturn(Long.valueOf(2)).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("1 value", Long.valueOf(1), DbTools.getLong(this.result, 1));
      assertEquals("2 value", Long.valueOf(2), DbTools.getLong(this.result, "long"));
      assertNull("NULL value", DbTools.getLong(this.result, 1));
      assertNull("NULL value", DbTools.getLong(this.result, "long"));

      verify();
   }


   @Test
   public void testShort() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getShort(EasyMock.eq(1))).andReturn((short)12).times(2);
      EasyMock.expect(this.result.getShort(EasyMock.eq("short"))).andReturn((short)13).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("short 1 value", Short.valueOf((short)12), DbTools.getShort(this.result, 1));
      assertEquals("short 2 value", Short.valueOf((short)13), DbTools.getShort(this.result, "short"));
      assertNull("NULL value", DbTools.getShort(this.result, 1));
      assertNull("NULL value", DbTools.getShort(this.result, "short"));

      verify();
   }


   @Test
   public void testString() throws SQLException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getString(EasyMock.eq(1))).andReturn("string 1").times(2);
      EasyMock.expect(this.result.getString(EasyMock.eq("string"))).andReturn("string 2").times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals("string 1 value", "string 1", DbTools.getString(this.result, 1));
      assertEquals("string 2 value", "string 2", DbTools.getString(this.result, "string"));
      assertNull("NULL value", DbTools.getString(this.result, 1));
      assertNull("NULL value", DbTools.getString(this.result, "string"));

      verify();
   }


   @Test
   public void testTime() throws SQLException, MssException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getTime(EasyMock.eq(1))).andReturn(new java.sql.Time(DateTimeTools.getMondayDate().getTime())).times(2);
      EasyMock.expect(this.result.getTime(EasyMock.eq("time"))).andReturn(new java.sql.Time(DateTimeTools.getTuesdayDate().getTime())).times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals(
            "Monday value",
            DateTimeTools.formatDate(DateTimeTools.getMondayDate(), DateTimeFormat.DATE_TIME_FORMAT_DE),
            DateTimeTools.formatDate(DbTools.getTime(this.result, 1), DateTimeFormat.DATE_TIME_FORMAT_DE));
      assertEquals(
            "Tuesday value",
            DateTimeTools.formatDate(DateTimeTools.getTuesdayDate(), DateTimeFormat.DATE_TIME_FORMAT_DE),
            DateTimeTools.formatDate(DbTools.getTime(this.result, "time"), DateTimeFormat.DATE_TIME_FORMAT_DE));
      assertNull("NULL value", DbTools.getTime(this.result, 1));
      assertNull("NULL value", DbTools.getTime(this.result, "time"));

      verify();
   }


   @Test
   public void testTimestamp() throws SQLException, MssException {

      this.result = EasyMock.createNiceMock(ResultSet.class);
      EasyMock.expect(this.result.getTimestamp(EasyMock.eq(1))).andReturn(new java.sql.Timestamp(DateTimeTools.getMondayDate().getTime())).times(2);
      EasyMock
            .expect(this.result.getTimestamp(EasyMock.eq("timestamp")))
            .andReturn(new java.sql.Timestamp(DateTimeTools.getTuesdayDate().getTime()))
            .times(2);

      this.result.wasNull();
      EasyMock.expectLastCall().andReturn(Boolean.FALSE).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE).andReturn(Boolean.TRUE);

      replay();

      assertEquals(
            "Monday value",
            DateTimeTools.formatDate(DateTimeTools.getMondayDate(), DateTimeFormat.DATE_TIME_FORMAT_DE),
            DateTimeTools.formatDate(DbTools.getTimestamp(this.result, 1), DateTimeFormat.DATE_TIME_FORMAT_DE));
      assertEquals(
            "Tuesday value",
            DateTimeTools.formatDate(DateTimeTools.getTuesdayDate(), DateTimeFormat.DATE_TIME_FORMAT_DE),
            DateTimeTools.formatDate(DbTools.getTimestamp(this.result, "timestamp"), DateTimeFormat.DATE_TIME_FORMAT_DE));
      assertNull("NULL value", DbTools.getTimestamp(this.result, 1));
      assertNull("NULL value", DbTools.getTimestamp(this.result, "timestamp"));

      verify();
   }
}
