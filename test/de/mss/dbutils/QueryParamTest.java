package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class QueryParamTest extends TestCase {

   @Test
   public void testEmptyConstructor() {
      QueryParam q = new QueryParam();

      assertNull("FielName", q.getFieldName());
      assertNull("FieldValue", q.getFieldValue());
      assertEquals("Type = 0", 0, q.getFieldType());
      assertFalse("Nullable", q.isNullable());
   }


   @Test
   public void testConstructor() {
      QueryParam q = new QueryParam("Field", "Value", java.sql.Types.VARCHAR);

      assertEquals("FielName", "Field", q.getFieldName());
      assertEquals("FieldValue", "Value", q.getFieldValue());
      assertEquals("Type = 0", java.sql.Types.VARCHAR, q.getFieldType());
      assertFalse("Nullable", q.isNullable());
   }


   @Test
   public void testSetter() {
      QueryParam q = new QueryParam();

      assertNull("FielName", q.getFieldName());
      assertNull("FieldValue", q.getFieldValue());
      assertEquals("Type = 0", 0, q.getFieldType());
      assertFalse("Nullable", q.isNullable());
      assertFalse("Nullvalue", q.isNullValue());

      q.setFieldName("Field");
      q.setNullable(true);
      assertTrue("Nullvalue", q.isNullValue());
      q.setFieldValue("Value");
      assertFalse("Nullvalue", q.isNullValue());
      q.setFieldType(java.sql.Types.NVARCHAR);

      assertEquals("FielName", "Field", q.getFieldName());
      assertEquals("FieldValue", "Value", q.getFieldValue());
      assertEquals("Type = 0", java.sql.Types.NVARCHAR, q.getFieldType());
      assertTrue("Nullable", q.isNullable());
   }


   @Test
   public void testGetStringFieldValue() throws MssException {
      checkGetTypedValue(
            "Value",
            "'Value'",
            Integer.valueOf(0),
            new int[] {
                       java.sql.Types.CHAR,
                       java.sql.Types.LONGNVARCHAR,
                       java.sql.Types.LONGVARCHAR,
                       java.sql.Types.NCHAR,
                       java.sql.Types.NVARCHAR,
                       java.sql.Types.VARCHAR},
            "string");
   }


   @Test
   public void testGetIntegerFieldValue() throws MssException {
      checkGetTypedValue(Integer.valueOf(12), "12", "", new int[] {java.sql.Types.BIGINT, java.sql.Types.INTEGER}, "integer");
      checkGetTypedValue(BigInteger.valueOf(12), "12", "", new int[] {java.sql.Types.BIGINT, java.sql.Types.INTEGER}, "integer");
   }


   @Test
   public void testGetDecimalFieldValue() throws MssException {
      checkGetTypedValue(
            new BigDecimal("12.34"),
            "12.34",
            "",
            new int[] {java.sql.Types.DECIMAL, java.sql.Types.DOUBLE, java.sql.Types.FLOAT},
            "decimal");
      checkGetTypedValue(
            Double.parseDouble("12.34"),
            "12.34",
            "",
            new int[] {java.sql.Types.DECIMAL, java.sql.Types.DOUBLE, java.sql.Types.FLOAT},
            "decimal");
      checkGetTypedValue(Float.parseFloat("12.34"), "12.34", "", new int[] {java.sql.Types.DECIMAL, java.sql.Types.DOUBLE, java.sql.Types.FLOAT}, "decimal");
   }


   @Test
   public void testGetDateFieldValue() throws MssException {
      java.util.Date date = new java.util.Date();

      checkGetTypedValue(date, "'" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "'", "", new int[] {java.sql.Types.DATE}, "date");
   }


   @Test
   public void testGetTimeFieldValue() throws MssException {
      java.util.Date date = new java.util.Date();

      checkGetTypedValue(
            date,
            "'" + new SimpleDateFormat("HH:mm:ss").format(date) + "'",
            "",
            new int[] {java.sql.Types.TIME, java.sql.Types.TIME_WITH_TIMEZONE},
            "date");
   }


   @Test
   public void testGetTimestampFieldValue() throws MssException {
      java.util.Date date = new java.util.Date();

      checkGetTypedValue(
            date,
            "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date) + "'",
            "",
            new int[] {java.sql.Types.TIMESTAMP, java.sql.Types.TIMESTAMP_WITH_TIMEZONE},
            "date");
   }


   @Test
   public void testGetBooleanFieldValue() throws MssException {
      checkGetTypedValue(Boolean.FALSE, "0", "", new int[] {java.sql.Types.BOOLEAN}, "boolean");
      checkGetTypedValue(Boolean.TRUE, "1", "", new int[] {java.sql.Types.BOOLEAN}, "boolean");
   }


   @Test
   public void testGetDefaultFieldValue() throws MssException {
      QueryParam q = new QueryParam();
      q.setFieldName("Field");
      q.setFieldValue("value");
      q.setFieldType(java.sql.Types.DISTINCT);
      assertEquals("Value", "value", q.getTypedFieldValue());

   }


   private void checkGetTypedValue(Object value, String expValue, Object wrongValue, int[] types, String type) throws MssException {
      QueryParam q = new QueryParam();
      q.setFieldName("Field");
      q.setNullable(true);

      assertEquals("Nullvalue", "null", q.getTypedFieldValue());

      q.setNullable(false);
      try {
         q.getTypedFieldValue();
         fail();
      }
      catch (MssException e) {
         assertNotNull("error", e.getError());
         assertEquals("ErrorCode", de.mss.dbutils.exception.ErrorCodes.ERROR_NON_NULLABLE_VALUE_IS_NULL.getErrorCode(), e.getError().getErrorCode());
         assertEquals("ErrorText", "value for field Field must not be null", e.getAltErrorText());
      }

      q.setNullable(true);
      q.setFieldType(types[0]);
      q.setFieldValue(wrongValue);
      try {
         q.getTypedFieldValue();
      }
      catch (MssException e) {
         assertNotNull("error", e.getError());
         assertEquals("ErrorCode", de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE.getErrorCode(), e.getError().getErrorCode());
         assertEquals("ErrorText", "field Field is not an instance of " + type, e.getAltErrorText());
      }

      q.setFieldValue(value);
      for (int i = 0; i < types.length; i++ ) {
         q.setFieldType(types[i]);
         assertEquals("Value", expValue, q.getTypedFieldValue());
      }
   }
}
