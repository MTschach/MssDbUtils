package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import de.mss.utils.DateTimeTools;
import de.mss.utils.Tools;

public class MssSingleResult {

   MssDataRow            columnNames     = null;
   ArrayList<MssDataRow> rowValues       = null;
   int                  resultSetNumber = 0;


   public MssSingleResult(int resNumber, int columnCount) {
      this.columnNames = new MssDataRow(columnCount);
      this.rowValues = new ArrayList<>();
      this.resultSetNumber = resNumber;
   }


   public int addRow() {
      this.rowValues.add(new MssDataRow(this.columnNames.getSize()));
      return this.rowValues.size();
   }


   public void setValue(int column, int row, String value) {
      if (column < 0 || column >= this.columnNames.getSize())
         return;

      if (row < 0 || row >= this.rowValues.size())
         return;

      this.rowValues.get(row).setValue(value, column);
   }


   public void setValue(String columnName, int row, String value) {
      setValue(getColumnIndex(columnName), row, value);
   }


   public String getValue(int column, int row) {
      return getValue(column, row, (String)null, (String)null);
   }


   public String getValue(int column, int row, String asStringValue) {
      return getValue(column, row, (String)null, asStringValue);
   }


   public String getValue(String columnName, int row, String defaultValue, String asStringValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asStringValue);
   }


   public String getValue(int column, int row, String defaultValue, String asStringValue) {
      if (column < 0 || column >= this.columnNames.getSize())
         return defaultValue;

      if (row < 0 || row >= this.rowValues.size())
         return defaultValue;

      return this.rowValues.get(row).getValue(column);
   }


   public Integer getValue(String columnName, int row, Integer asIntegerValue) {
      return getValue(getColumnIndex(columnName), row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(String columnName, int row, Integer defaultValue, Integer asIntegerValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asIntegerValue);
   }


   public Integer getValue(int column, int row, Integer asIntegerValue) {
      return getValue(column, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int column, int row, Integer defaultValue, Integer asIntegerValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      try {
         return Integer.valueOf(s);
      }
      catch (Exception e) {
         Tools.doNullLog(e);
      }
      return defaultValue;
   }


   public BigInteger getValue(String columnName, int row, BigInteger asBigIntegerValue) {
      return getValue(getColumnIndex(columnName), row, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(String columnName, int row, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asBigIntegerValue);
   }


   public BigInteger getValue(int column, int row, BigInteger asBigIntegerValue) {
      return getValue(column, row, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(int column, int row, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      try {
         return new BigInteger(s);
      }
      catch (Exception e) {
         Tools.doNullLog(e);
      }
      return defaultValue;
   }


   public BigDecimal getValue(String columnName, int row, BigDecimal asBigDecimalValue) {
      return getValue(getColumnIndex(columnName), row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(String columnName, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(int column, int row, BigDecimal asBigDecimalValue) {
      return getValue(column, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int column, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      try {
         return new BigDecimal(s);
      }
      catch (Exception e) {
         Tools.doNullLog(e);
      }
      return defaultValue;
   }


   public java.util.Date getValue(String columnName, int row, java.util.Date asDateValue) {
      return getValue(getColumnIndex(columnName), row, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(String columnName, int row, java.util.Date defaultValue, java.util.Date asDateValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asDateValue);
   }


   public java.util.Date getValue(int column, int row, java.util.Date asDateValue) {
      return getValue(column, row, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(int column, int row, java.util.Date defaultValue, java.util.Date asDateValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      try {
         return DateTimeTools.parseString2Date(s);
      }
      catch (Exception e) {
         Tools.doNullLog(e);
      }
      return defaultValue;
   }


   public Double getValue(String columnName, int row, Double asDoubleValue) {
      return getValue(getColumnIndex(columnName), row, (Double)null, asDoubleValue);
   }


   public Double getValue(String columnName, int row, Double defaultValue, Double asDoubleValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asDoubleValue);
   }


   public Double getValue(int column, int row, Double asDoubleValue) {
      return getValue(column, row, (Double)null, asDoubleValue);
   }


   public Double getValue(int column, int row, Double defaultValue, Double asDoubleValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      try {
         return Double.parseDouble(s);
      }
      catch (Exception e) {
         Tools.doNullLog(e);
      }
      return defaultValue;
   }


   public Float getValue(String columnName, int row, Float asFloatValue) {
      return getValue(getColumnIndex(columnName), row, (Float)null, asFloatValue);
   }


   public Float getValue(String columnName, int row, Float defaultValue, Float asFloatValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asFloatValue);
   }


   public Float getValue(int column, int row, Float asFloatValue) {
      return getValue(column, row, (Float)null, asFloatValue);
   }


   public Float getValue(int column, int row, Float defaultValue, Float asFloatValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      try {
         return Float.parseFloat(s);
      }
      catch (Exception e) {
         Tools.doNullLog(e);
      }
      return defaultValue;
   }


   public Boolean getValue(String columnName, int row, Boolean asBooleanValue) {
      return getValue(getColumnIndex(columnName), row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(String columnName, int row, Boolean defaultValue, Boolean asBooleanValue) {
      return getValue(getColumnIndex(columnName), row, defaultValue, asBooleanValue);
   }


   public Boolean getValue(int column, int row, Boolean asBooleanValue) {
      return getValue(column, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int column, int row, Boolean defaultValue, Boolean asBooleanValue) {
      String s = getValue(column, row, (String)null, (String)null);
      if (s == null)
         return defaultValue;

      if (Tools.isTrue(s))
         return Boolean.TRUE;

      return Boolean.FALSE;
   }


   public String getValue(String columnName, int row) {
      return getValue(getColumnIndex(columnName), row);
   }


   public int getColumnCount() {
      return this.columnNames.getSize();
   }


   public int getRowCount() {
      return this.rowValues.size();
   }


   private int getColumnIndex(String columnName) {
      for (int i = 0; i < this.columnNames.getSize(); i++ ) {
         if (this.columnNames.getValue(i).equals(columnName))
            return i;
      }

      return -1;
   }


   public void setColumnName(String columnName, int col) {
      if (col < 0 || col >= this.columnNames.getSize())
         return;

      this.columnNames.setValue(columnName, col);
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      sb.append("[" + this.resultSetNumber + ";" + this.columnNames.toString() + "]\n");
      for (MssDataRow row : this.rowValues)
         sb.append("[" + this.resultSetNumber + ";" + row.toString() + "]\n");

      return sb.toString();
   }
}
