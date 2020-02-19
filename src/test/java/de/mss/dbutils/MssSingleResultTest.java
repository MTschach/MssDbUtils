package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

import junit.framework.TestCase;

public class MssSingleResultTest extends TestCase {


   @Test
   public void testConstructor() {
      MssSingleResult res = new MssSingleResult(2, 3);

      assertEquals("ColumnCount", 3, res.getColumnCount());
      assertEquals("RowCount", 0, res.getRowCount());
   }


   @Test
   public void testSetColumnNames() {
      MssSingleResult res = new MssSingleResult(2, 3);

      res.setColumnName("COL-1", -1);
      res.setColumnName("COL0", 0);
      res.setColumnName("COL1", 1);
      res.setColumnName("COL2", 2);
      res.setColumnName("COL3", 3);

      assertEquals("Column 0", "[2;COL0;COL1;COL2]\n", res.toString());
   }


   @Test
   public void testAddRowAndSetValue() {
      MssSingleResult res = new MssSingleResult(2, 3);

      res.setColumnName("COL0", 0);
      res.setColumnName("COL1", 1);
      res.setColumnName("COL2", 2);

      res.addRow();

      res.setValue(0, 0, "VAL0x0");
      res.setValue(0, -1, "VAL0x-1");
      res.setValue(0, 4, "VAL0x4");
      res.setValue(-1, 0, "VAL-1x0");
      res.setValue(4, 0, "VAL4x0");

      res.setValue("COL1", 0, "VAL1x0");
      res.setValue("COL2", 0, "VAL2x0");

      res.addRow();

      res.setValue(0, 1, "VAL0x1");
      res.setValue(1, 1, "VAL1x1");
      res.setValue(2, 1, "VAL2x1");

      assertEquals("Result", "[2;COL0;COL1;COL2]\n[2;VAL0x0;VAL1x0;VAL2x0]\n[2;VAL0x1;VAL1x1;VAL2x1]\n", res.toString());

   }


   @Test
   public void testGetValue() {
      MssSingleResult res = new MssSingleResult(2, 5);

      res.setColumnName("COL0", 0);
      res.setColumnName("COL1", 1);
      res.setColumnName("COL2", 2);
      res.setColumnName("COL3", 3);
      res.setColumnName("COL4", 4);

      res.addRow();
      res.setValue(0, 0, "lala");
      res.setValue(1, 0, "1");
      res.setValue(2, 0, "true");
      res.setValue(3, 0, "1.234");
      res.setValue(4, 0, null);

      assertEquals("getStringValue", "lala", res.getValue(0, 0));
      assertEquals("getStringValue", "lala", res.getValue("COL0", 0));
      assertEquals("getStringValue", "lala", res.getValue(0, 0, (String)null));
      assertEquals("getStringValue", "lala", res.getValue(0, 0, "blah", (String)null));
      assertEquals("getStringValue", "blah", res.getValue(10, 0, "blah", (String)null));
      assertEquals("getStringValue", "blah", res.getValue(0, -1, "blah", (String)null));
      assertEquals("getStringValue", "blah", res.getValue(0, 10, "blah", (String)null));
      assertEquals("getStringValue", "blah", res.getValue("COL10", 0, "blah", (String)null));

      assertEquals("getIntegerValue", Integer.valueOf(1), res.getValue(1, 0, (Integer)null));
      assertEquals("getIntegerValue", Integer.valueOf(1), res.getValue(1, 0, Integer.valueOf(2), (Integer)null));
      assertEquals("getIntegerValue", Integer.valueOf(2), res.getValue(4, 0, Integer.valueOf(2), (Integer)null));
      assertEquals("getIntegerValue", Integer.valueOf(1), res.getValue("COL1", 0, (Integer)null));
      assertEquals("getIntegerValue", Integer.valueOf(1), res.getValue("COL1", 0, Integer.valueOf(2), (Integer)null));
      assertEquals("getIntegerValue", Integer.valueOf(2), res.getValue("COL0", 0, Integer.valueOf(2), (Integer)null));

      assertEquals("getBigIntegerValue", BigInteger.valueOf(1), res.getValue(1, 0, (BigInteger)null));
      assertEquals("getBigIntegerValue", BigInteger.valueOf(1), res.getValue(1, 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("getBigIntegerValue", BigInteger.valueOf(2), res.getValue(4, 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("getBigIntegerValue", BigInteger.valueOf(1), res.getValue("COL1", 0, (BigInteger)null));
      assertEquals("getBigIntegerValue", BigInteger.valueOf(1), res.getValue("COL1", 0, BigInteger.valueOf(2), (BigInteger)null));
      assertEquals("getBigIntegerValue", BigInteger.valueOf(2), res.getValue("COL0", 0, BigInteger.valueOf(2), (BigInteger)null));

      assertEquals("getBooleanValue", Boolean.TRUE, res.getValue(2, 0, (Boolean)null));
      assertEquals("getBooleanValue", Boolean.TRUE, res.getValue(2, 0, Boolean.FALSE, (Boolean)null));
      assertEquals("getBooleanValue", Boolean.FALSE, res.getValue(4, 0, Boolean.FALSE, (Boolean)null));
      assertEquals("getBooleanValue", Boolean.TRUE, res.getValue("COL2", 0, (Boolean)null));
      assertEquals("getBooleanValue", Boolean.TRUE, res.getValue("COL2", 0, Boolean.FALSE, (Boolean)null));
      assertEquals("getBooleanValue", Boolean.FALSE, res.getValue("COL0", 0, Boolean.FALSE, (Boolean)null));

      assertEquals("getBigDecimalValue", BigDecimal.valueOf(1.234), res.getValue(3, 0, (BigDecimal)null));
      assertEquals("getBigDecimalValue", BigDecimal.valueOf(1.234), res.getValue(3, 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("getBigDecimalValue", BigDecimal.valueOf(2.34), res.getValue(4, 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("getBigDecimalValue", BigDecimal.valueOf(1.234), res.getValue("COL3", 0, (BigDecimal)null));
      assertEquals("getBigDecimalValue", BigDecimal.valueOf(1.234), res.getValue("COL3", 0, BigDecimal.valueOf(2.34), (BigDecimal)null));
      assertEquals("getBigDecimalValue", BigDecimal.valueOf(2.34), res.getValue("COL0", 0, BigDecimal.valueOf(2.34), (BigDecimal)null));

      assertEquals("getDoubleValue", Double.valueOf(1.234), res.getValue(3, 0, (Double)null));
      assertEquals("getDoubleValue", Double.valueOf(1.234), res.getValue(3, 0, Double.valueOf(2.34), (Double)null));
      assertEquals("getDoubleValue", Double.valueOf(2.34), res.getValue(4, 0, Double.valueOf(2.34), (Double)null));
      assertEquals("getDoubleValue", Double.valueOf(1.234), res.getValue("COL3", 0, (Double)null));
      assertEquals("getDoubleValue", Double.valueOf(1.234), res.getValue("COL3", 0, Double.valueOf(2.34), (Double)null));
      assertEquals("getDoubleValue", Double.valueOf(2.34), res.getValue("COL0", 0, Double.valueOf(2.34), (Double)null));

      assertEquals("getFloatValue", Float.valueOf("1.234"), res.getValue(3, 0, (Float)null));
      assertEquals("getFloatValue", Float.valueOf("1.234"), res.getValue(3, 0, Float.valueOf("2.34"), (Float)null));
      assertEquals("getFloatValue", Float.valueOf("2.34"), res.getValue(4, 0, Float.valueOf("2.34"), (Float)null));
      assertEquals("getFloatValue", Float.valueOf("1.234"), res.getValue("COL3", 0, (Float)null));
      assertEquals("getFloatValue", Float.valueOf("1.234"), res.getValue("COL3", 0, Float.valueOf("2.34"), (Float)null));
      assertEquals("getFloatValue", Float.valueOf("2.34"), res.getValue("COL0", 0, Float.valueOf("2.34"), (Float)null));

   }
}
