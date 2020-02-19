package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import junit.framework.TestCase;

public class MssResultTest extends TestCase {


   @Test
   public void testConstructor() {
      MssResult res = new MssResult();

      assertEquals("Size", 0, res.getNumberOfResults());
   }


   @Test
   public void testAddRow() {
      MssResult res = new MssResult();

      assertEquals("Res", -1, res.addResult(null));
   }


   @Test
   public void testGetColumnCount() {
      MssResult res = setupResult();

      assertEquals("Size", 2, res.getNumberOfResults());

      assertEquals("Size Result -1", 0, res.getColumnCount(-1));
      assertEquals("Size Result 0", 3, res.getColumnCount(0));
      assertEquals("Size Result 1", 4, res.getColumnCount(1));
      assertEquals("Size Result 2", 0, res.getColumnCount(2));
   }


   @Test
   public void testGetRowCount() {
      MssResult res = setupResult();

      assertEquals("RowCount -1", 0, res.getRowCount(-1));
      assertEquals("RowCount 0", 1, res.getRowCount(0));
      assertEquals("RowCount 1", 1, res.getRowCount(1));
      assertEquals("RowCount 2", 0, res.getRowCount(2));
   }


   @Test
   public void testGetResultSet() {
      MssResult res = setupResult();

      assertNull("ResultSet -1", res.getValue(-1, 0, 0));
      assertNull("ResultSet 2", res.getValue(2, 0, 0));
   }


   @Test
   public void testGetBigDecimalValue() {
      MssResult res = setupResult();

      assertEquals("Col0", BigDecimal.valueOf(1.234), res.getValue(1, 0, 0, (BigDecimal)null));
      assertEquals("Col0", BigDecimal.valueOf(1.234), res.getValue(1, 0, 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("Col0", BigDecimal.valueOf(2.34), res.getValue(-1, 0, 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("Col0", BigDecimal.valueOf(2.34), res.getValue(10, 0, 0, BigDecimal.valueOf(2.34), (BigDecimal)null));

      assertEquals("Col0", BigDecimal.valueOf(1.234), res.getValue(1, "COLUMN0", 0, (BigDecimal)null));
      assertEquals("Col0", BigDecimal.valueOf(1.234), res.getValue(1, "COLUMN0", 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("Col0", BigDecimal.valueOf(2.34), res.getValue(-1, "COLUMN0", 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("Col0", BigDecimal.valueOf(2.34), res.getValue(10, "COLUMN0", 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
   }


   @Test
   public void testGetBooleanValue() {
      MssResult res = setupResult();

      assertEquals("Col0", Boolean.TRUE, res.getValue(1, 1, 0, (Boolean)null));
      assertEquals("Col0", Boolean.TRUE, res.getValue(1, 1, 0, Boolean.FALSE, (Boolean)null));
      assertEquals("Col0", Boolean.FALSE, res.getValue(-1, 1, 0, Boolean.FALSE, (Boolean)null));
      assertEquals("Col0", Boolean.FALSE, res.getValue(10, 1, 0, Boolean.FALSE, (Boolean)null));

      assertEquals("Col0", Boolean.TRUE, res.getValue(1, "COLUMN1", 0, (Boolean)null));
      assertEquals("Col0", Boolean.TRUE, res.getValue(1, "COLUMN1", 0, Boolean.FALSE, (Boolean)null));
      assertEquals("Col0", Boolean.FALSE, res.getValue(-1, "COLUMN1", 0, Boolean.FALSE, (Boolean)null));
      assertEquals("Col0", Boolean.FALSE, res.getValue(10, "COLUMN1", 0, Boolean.FALSE, (Boolean)null));
   }


   @Test
   public void testGetIntegerValue() {
      MssResult res = setupResult();

      assertEquals("Col0", Integer.valueOf(1), res.getValue(1, 2, 0, (Integer)null));
      assertEquals("Col0", Integer.valueOf(1), res.getValue(1, 2, 0, Integer.valueOf(2), (Integer)null));
      assertEquals("Col0", Integer.valueOf(2), res.getValue(-1, 2, 0, Integer.valueOf(2), (Integer)null));
      assertEquals("Col0", Integer.valueOf(2), res.getValue(10, 2, 0, Integer.valueOf(2), (Integer)null));

      assertEquals("Col0", Integer.valueOf(1), res.getValue(1, "COLUMN2", 0, (Integer)null));
      assertEquals("Col0", Integer.valueOf(1), res.getValue(1, "COLUMN2", 0, Integer.valueOf(2), (Integer)null));
      assertEquals("Col0", Integer.valueOf(2), res.getValue(-1, "COLUMN2", 0, Integer.valueOf(2), (Integer)null));
      assertEquals("Col0", Integer.valueOf(2), res.getValue(10, "COLUMN2", 0, Integer.valueOf(2), (Integer)null));
   }


   @Test
   public void testGetBigIntegerValue() {
      MssResult res = setupResult();

      assertEquals("Col0", BigInteger.valueOf(1), res.getValue(1, 2, 0, (BigInteger)null));
      assertEquals("Col0", BigInteger.valueOf(1), res.getValue(1, 2, 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("Col0", BigInteger.valueOf(2), res.getValue(-1, 2, 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("Col0", BigInteger.valueOf(2), res.getValue(10, 2, 0, BigInteger.valueOf(2), (BigInteger)null));

      assertEquals("Col0", BigInteger.valueOf(1), res.getValue(1, "COLUMN2", 0, (BigInteger)null));
      assertEquals("Col0", BigInteger.valueOf(1), res.getValue(1, "COLUMN2", 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("Col0", BigInteger.valueOf(2), res.getValue(-1, "COLUMN2", 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("Col0", BigInteger.valueOf(2), res.getValue(10, "COLUMN2", 0, BigInteger.valueOf(2), (BigInteger)null));
   }


   @Test
   public void testGetDoubleValue() {
      MssResult res = setupResult();

      assertEquals("Col0", Double.valueOf(1), res.getValue(1, 2, 0, (Double)null));
      assertEquals("Col0", Double.valueOf(1), res.getValue(1, 2, 0, Double.valueOf(2), (Double)null));
      assertEquals("Col0", Double.valueOf(2), res.getValue(-1, 2, 0, Double.valueOf(2), (Double)null));
      assertEquals("Col0", Double.valueOf(2), res.getValue(10, 2, 0, Double.valueOf(2), (Double)null));

      assertEquals("Col0", Double.valueOf(1), res.getValue(1, "COLUMN2", 0, (Double)null));
      assertEquals("Col0", Double.valueOf(1), res.getValue(1, "COLUMN2", 0, Double.valueOf(2), (Double)null));
      assertEquals("Col0", Double.valueOf(2), res.getValue(-1, "COLUMN2", 0, Double.valueOf(2), (Double)null));
      assertEquals("Col0", Double.valueOf(2), res.getValue(10, "COLUMN2", 0, Double.valueOf(2), (Double)null));
   }


   @Test
   public void testGetFloatValue() {
      MssResult res = setupResult();

      assertEquals("Col0", Float.valueOf(1), res.getValue(1, 2, 0, (Float)null));
      assertEquals("Col0", Float.valueOf(1), res.getValue(1, 2, 0, Float.valueOf(2), (Float)null));
      assertEquals("Col0", Float.valueOf(2), res.getValue(-1, 2, 0, Float.valueOf(2), (Float)null));
      assertEquals("Col0", Float.valueOf(2), res.getValue(10, 2, 0, Float.valueOf(2), (Float)null));

      assertEquals("Col0", Float.valueOf(1), res.getValue(1, "COLUMN2", 0, (Float)null));
      assertEquals("Col0", Float.valueOf(1), res.getValue(1, "COLUMN2", 0, Float.valueOf(2), (Float)null));
      assertEquals("Col0", Float.valueOf(2), res.getValue(-1, "COLUMN2", 0, Float.valueOf(2), (Float)null));
      assertEquals("Col0", Float.valueOf(2), res.getValue(10, "COLUMN2", 0, Float.valueOf(2), (Float)null));
   }


   @Test
   public void testGetStringValue() {
      MssResult res = setupResult();

      assertEquals("Col0", "lala", res.getValue(0, 0, 0));
      assertEquals("Col0", "lala", res.getValue(0, 0, 0, "foobar"));
      assertEquals("Col0", "lala", res.getValue(0, 0, 0, "foobar", (String)null));
      assertEquals("Col0", "foobar", res.getValue(-1, 5, 0, "foobar", (String)null));
      assertEquals("Col0", "foobar", res.getValue(10, 5, 0, "foobar", (String)null));

      assertEquals("Col1", "foo", res.getValue(0, "COL1", 0));
      assertEquals("Col1", "foo", res.getValue(0, "COL1", 0, "foobar"));
      assertEquals("Col1", "foo", res.getValue(0, "COL1", 0, "foobar", (String)null));
      assertEquals("Col1", "foobar", res.getValue(-1, "COL1", 0, "foobar", (String)null));
      assertEquals("Col1", "foobar", res.getValue(10, "COL1", 0, "foobar", (String)null));
   }


   @Test
   public void testToString() {
      MssResult res = setupResult();

      assertEquals("ToString", "[0;COL0;COL1;COL2]\n[0;lala;foo;bar]\n[1;COLUMN0;COLUMN1;COLUMN2;COLUMN3]\n[1;1.234;true;1;lala]\n", res.toString());
   }


   private MssResult setupResult() {
      MssResult res = new MssResult();

      MssSingleResult singleResult = new MssSingleResult(0, 3);

      singleResult.setColumnName("COL0", 0);
      singleResult.setColumnName("COL1", 1);
      singleResult.setColumnName("COL2", 2);

      singleResult.addRow();
      singleResult.setValue(0, 0, "lala");
      singleResult.setValue(1, 0, "foo");
      singleResult.setValue(2, 0, "bar");

      res.addResult(singleResult);

      singleResult = new MssSingleResult(1, 4);
      singleResult.setColumnName("COLUMN0", 0);
      singleResult.setColumnName("COLUMN1", 1);
      singleResult.setColumnName("COLUMN2", 2);
      singleResult.setColumnName("COLUMN3", 3);

      singleResult.addRow();
      singleResult.setValue(0, 0, "1.234");
      singleResult.setValue(1, 0, "true");
      singleResult.setValue(2, 0, "1");
      singleResult.setValue(3, 0, "lala");

      res.addResult(singleResult);

      return res;
   }


}
