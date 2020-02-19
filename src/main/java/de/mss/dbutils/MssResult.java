package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class MssResult {

   public static final String         AS_STRING_VALUE     = null;
   public static final Integer        AS_INTEGER_VALUE    = null;
   public static final Boolean        AS_BOOLEAN_VALUE    = null;
   public static final BigDecimal     AS_BIGDECIMAL_VALUE = null;
   public static final BigInteger     AS_BIGINTEGER_VALUE = null;
   public static final Double         AS_DOUBLE_VALUE     = null;
   public static final Float          AS_FLOAT_VALUE      = null;
   public static final java.util.Date AS_DATE_VALUE       = null;

   ArrayList<MssSingleResult>         resultList          = null;

   protected int                      currentResultSet    = -1;
   protected int                      currentRow          = -1;


   public MssResult() {
      this.resultList = new ArrayList<>();
   }


   public int getNumberOfResults() {
      return this.resultList.size();
   }


   public int getCurrentResultSet() {
      return this.currentResultSet;
   }


   public void setCurrentResultSet(int v) {
      this.currentResultSet = v;
      this.currentRow = -1;
   }


   public void setCurrentRow(int v) {
      this.currentRow = v;
   }


   @Deprecated
   public boolean next() {
      return nextResult();
   }


   public boolean nextResult() {
      if (this.resultList == null)
         return false;
      if (this.currentResultSet >= (this.resultList.size() - 1))
         return false;

      this.currentResultSet++ ;
      this.currentRow = -1;
      return true;
   }


   public boolean nextRow() {
      if (this.resultList == null)
         return false;
      if (this.currentResultSet >= (this.resultList.size() - 1))
         return false;
      if (this.currentRow >= getRowCount())
         return false;

      this.currentRow++ ;
      return true;
   }


   public int getColumnCount() {
      return getColumnCount(this.currentResultSet);
   }


   public int getColumnCount(int resultSetNumber) {
      if (resultSetNumber < 0 || resultSetNumber >= this.resultList.size())
         return 0;

      return this.resultList.get(resultSetNumber).getColumnCount();
   }


   public int getRowCount() {
      return getRowCount(this.currentResultSet);
   }


   public int getRowCount(int resultSetNumber) {
      if (resultSetNumber < 0 || resultSetNumber >= this.resultList.size())
         return 0;

      return this.resultList.get(resultSetNumber).getRowCount();
   }


   public int addResult(MssSingleResult result) {
      if (result == null)
         return -1;

      this.resultList.add(result);

      return this.resultList.size();
   }


   public BigInteger getValue(int column, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(int column, int row, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, column, row, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(int resultSetNumber, int column, int row, BigInteger asBigIntegerValue) {
      return getValue(resultSetNumber, column, row, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(int column, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asBigIntegerValue);
   }


   public BigInteger getValue(int column, int row, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asBigIntegerValue);
   }


   public BigInteger getValue(int resultSetNumber, int column, int row, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asBigIntegerValue);
   }


   public BigInteger getValue(String columnName, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(String columnName, int row, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, columnName, row, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(int resultSetNumber, String columnName, int row, BigInteger asBigIntegerValue) {
      return getValue(resultSetNumber, columnName, row, (BigInteger)null, asBigIntegerValue);
   }


   public BigInteger getValue(String columnName, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asBigIntegerValue);
   }


   public BigInteger getValue(String columnName, int row, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asBigIntegerValue);
   }


   public BigInteger getValue(int resultSetNumber, String columnName, int row, BigInteger defaultValue, BigInteger asBigIntegerValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asBigIntegerValue);
   }


   public java.util.Date getValue(int column, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(int column, int row, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, column, row, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(int resultSetNumber, int column, int row, java.util.Date asDateValue) {
      return this.resultList.get(resultSetNumber).getValue(column, row, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(int column, java.util.Date defaultValue, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asDateValue);
   }


   public java.util.Date getValue(int column, int row, java.util.Date defaultValue, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asDateValue);
   }


   public java.util.Date getValue(int resultSetNumber, int column, int row, java.util.Date defaultValue, java.util.Date asDateValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asDateValue);
   }


   public java.util.Date getValue(String columnName, int row, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, columnName, row, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(String columnName, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(int resultSetNumber, String columnName, int row, java.util.Date asDateValue) {
      return getValue(resultSetNumber, columnName, row, (java.util.Date)null, asDateValue);
   }


   public java.util.Date getValue(String columnName, java.util.Date defaultValue, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asDateValue);
   }


   public java.util.Date getValue(String columnName, int row, java.util.Date defaultValue, java.util.Date asDateValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asDateValue);
   }


   public java.util.Date getValue(int resultSetNumber, String columnName, int row, java.util.Date defaultValue, java.util.Date asDateValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asDateValue);
   }


   public BigDecimal getValue(int column, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int column, int row, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, column, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, int column, int row, BigDecimal asBigDecimalValue) {
      return getValue(resultSetNumber, column, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int column, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(int column, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, int column, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(String columnName, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(String columnName, int row, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, columnName, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, String columnName, int row, BigDecimal asBigDecimalValue) {
      return getValue(resultSetNumber, columnName, row, (BigDecimal)null, asBigDecimalValue);
   }


   public BigDecimal getValue(String columnName, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(String columnName, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asBigDecimalValue);
   }


   public BigDecimal getValue(int resultSetNumber, String columnName, int row, BigDecimal defaultValue, BigDecimal asBigDecimalValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asBigDecimalValue);
   }


   public Double getValue(int column, Double asDoubleValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (Double)null, asDoubleValue);
   }


   public Double getValue(int column, int row, Double asDoubleValue) {
      return getValue(this.currentResultSet, column, row, (Double)null, asDoubleValue);
   }


   public Double getValue(int resultSetNumber, int column, int row, Double asDoubleValue) {
      return getValue(resultSetNumber, column, row, (Double)null, asDoubleValue);
   }


   public Double getValue(int column, Double defaultValue, Double asDoubleValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asDoubleValue);
   }


   public Double getValue(int column, int row, Double defaultValue, Double asDoubleValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asDoubleValue);
   }


   public Double getValue(int resultSetNumber, int column, int row, Double defaultValue, Double asDoubleValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asDoubleValue);
   }


   public Double getValue(String columnName, Double asDoubleValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (Double)null, asDoubleValue);
   }


   public Double getValue(String columnName, int row, Double asDoubleValue) {
      return getValue(this.currentResultSet, columnName, row, (Double)null, asDoubleValue);
   }


   public Double getValue(int resultSetNumber, String columnName, int row, Double asDoubleValue) {
      return getValue(resultSetNumber, columnName, row, (Double)null, asDoubleValue);
   }


   public Double getValue(String columnName, Double defaultValue, Double asDoubleValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asDoubleValue);
   }


   public Double getValue(String columnName, int row, Double defaultValue, Double asDoubleValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asDoubleValue);
   }


   public Double getValue(int resultSetNumber, String columnName, int row, Double defaultValue, Double asDoubleValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asDoubleValue);
   }


   public Float getValue(int column, Float asFloatValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (Float)null, asFloatValue);
   }


   public Float getValue(int column, int row, Float asFloatValue) {
      return getValue(this.currentResultSet, column, row, (Float)null, asFloatValue);
   }


   public Float getValue(int resultSetNumber, int column, int row, Float asFloatValue) {
      return getValue(resultSetNumber, column, row, (Float)null, asFloatValue);
   }


   public Float getValue(int column, Float defaultValue, Float asFloatValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asFloatValue);
   }


   public Float getValue(int column, int row, Float defaultValue, Float asFloatValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asFloatValue);
   }


   public Float getValue(int resultSetNumber, int column, int row, Float defaultValue, Float asFloatValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asFloatValue);
   }


   public Float getValue(String columnName, Float asFloatValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (Float)null, asFloatValue);
   }


   public Float getValue(String columnName, int row, Float asFloatValue) {
      return getValue(this.currentResultSet, columnName, row, (Float)null, asFloatValue);
   }


   public Float getValue(int resultSetNumber, String columnName, int row, Float asFloatValue) {
      return getValue(resultSetNumber, columnName, row, (Float)null, asFloatValue);
   }


   public Float getValue(String columnName, Float defaultValue, Float asFloatValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asFloatValue);
   }


   public Float getValue(String columnName, int row, Float defaultValue, Float asFloatValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asFloatValue);
   }


   public Float getValue(int resultSetNumber, String columnName, int row, Float defaultValue, Float asFloatValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asFloatValue);
   }


   public Boolean getValue(int column, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int column, int row, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, column, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, int column, int row, Boolean asBooleanValue) {
      return getValue(resultSetNumber, column, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int column, Boolean defaultValue, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asBooleanValue);
   }


   public Boolean getValue(int column, int row, Boolean defaultValue, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, int column, int row, Boolean defaultValue, Boolean asBooleanValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asBooleanValue);
   }


   public Boolean getValue(String columnName, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(String columnName, int row, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, columnName, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, String columnName, int row, Boolean asBooleanValue) {
      return getValue(resultSetNumber, columnName, row, (Boolean)null, asBooleanValue);
   }


   public Boolean getValue(String columnName, Boolean defaultValue, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asBooleanValue);
   }


   public Boolean getValue(String columnName, int row, Boolean defaultValue, Boolean asBooleanValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asBooleanValue);
   }


   public Boolean getValue(int resultSetNumber, String columnName, int row, Boolean defaultValue, Boolean asBooleanValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asBooleanValue);
   }


   public Integer getValue(int column, Integer asIntegerValue) {
      return getValue(this.currentResultSet, column, this.currentRow, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int column, int row, Integer asIntegerValue) {
      return getValue(this.currentResultSet, column, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, int column, int row, Integer asIntegerValue) {
      return getValue(resultSetNumber, column, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int column, Integer defaultValue, Integer asIntegerValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asIntegerValue);
   }


   public Integer getValue(int column, int row, Integer defaultValue, Integer asIntegerValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, int column, int row, Integer defaultValue, Integer asIntegerValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asIntegerValue);
   }


   public Integer getValue(String columnName, Integer asIntegerValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (Integer)null, asIntegerValue);
   }


   public Integer getValue(String columnName, int row, Integer asIntegerValue) {
      return getValue(this.currentResultSet, columnName, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, String columnName, int row, Integer asIntegerValue) {
      return getValue(resultSetNumber, columnName, row, (Integer)null, asIntegerValue);
   }


   public Integer getValue(String columnName, Integer defaultValue, Integer asIntegerValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asIntegerValue);
   }


   public Integer getValue(String columnName, int row, Integer defaultValue, Integer asIntegerValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asIntegerValue);
   }


   public Integer getValue(int resultSetNumber, String columnName, int row, Integer defaultValue, Integer asIntegerValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asIntegerValue);
   }


   public String getValue(String columnName) {
      return getValue(this.currentResultSet, columnName, this.currentRow, (String)null, (String)null);
   }


   public String getValue(String columnName, int row) {
      return getValue(this.currentResultSet, columnName, row, (String)null, (String)null);
   }


   public String getValue(int resultSetNumber, String columnName, int row) {
      return getValue(resultSetNumber, columnName, row, (String)null, (String)null);
   }


   public String getValue(String columnName, String defaultValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, (String)null);
   }


   public String getValue(String columnName, int row, String defaultValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, (String)null);
   }


   public String getValue(int resultSetNumber, String columnName, int row, String defaultValue) {
      return getValue(resultSetNumber, columnName, row, defaultValue, (String)null);
   }


   public String getValue(String columnName, String defaultValue, String asStringValue) {
      return getValue(this.currentResultSet, columnName, this.currentRow, defaultValue, asStringValue);
   }


   public String getValue(String columnName, int row, String defaultValue, String asStringValue) {
      return getValue(this.currentResultSet, columnName, row, defaultValue, asStringValue);
   }


   public String getValue(int resultSetNumber, String columnName, int row, String defaultValue, String asStringValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(columnName, row, defaultValue, asStringValue);
   }


   public String getValue(int column) {
      return getValue(this.currentResultSet, column, this.currentRow, (String)null, (String)null);
   }


   public String getValue(int column, int row) {
      return getValue(this.currentResultSet, column, row, (String)null, (String)null);
   }


   public String getValue(int resultSetNumber, int column, int row) {
      return getValue(resultSetNumber, column, row, (String)null, (String)null);
   }


   public String getValue(int column, String defaultValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, (String)null);
   }


   public String getValue(int column, int row, String defaultValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, (String)null);
   }


   public String getValue(int resultSetNumber, int column, int row, String defaultValue) {
      return getValue(resultSetNumber, column, row, defaultValue, (String)null);
   }


   public String getValue(int column, String defaultValue, String asStringValue) {
      return getValue(this.currentResultSet, column, this.currentRow, defaultValue, asStringValue);
   }


   public String getValue(int column, int row, String defaultValue, String asStringValue) {
      return getValue(this.currentResultSet, column, row, defaultValue, asStringValue);
   }


   public String getValue(int resultSetNumber, int column, int row, String defaultValue, String asStringValue) {
      if (!checkResultSetNumber(resultSetNumber))
         return defaultValue;

      return this.resultList.get(resultSetNumber).getValue(column, row, defaultValue, asStringValue);
   }


   private boolean checkResultSetNumber(int resultSetNumber) {
      return (resultSetNumber >= 0 && resultSetNumber < this.resultList.size());
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();

      for (MssSingleResult res : this.resultList)
         sb.append(res.toString());

      return sb.toString();
   }
}
