package de.mss.dbutils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

import de.mss.utils.exception.MssException;

public class QueryParam implements Serializable{

   private static final long serialVersionUID = -7665020505209285991L;

   private String  fieldName  = null;
   private Object  fieldValue = null;
   private int     fieldType  = 0;
   private boolean isNullable = false;


   public QueryParam() {}


   public QueryParam(String n, Object v, int t) {
      setFieldName(n);
      setFieldValue(v);
      setFieldType(t);
   }


   public void setNullable(boolean n) {
      this.isNullable = n;
   }


   public boolean isNullable() {
      return this.isNullable;
   }


   public void setFieldType(int t) {
      this.fieldType = t;
   }


   public int getFieldType() {
      return this.fieldType;
   }


   public void setFieldValue(Object v) {
      this.fieldValue = v;
   }


   public Object getFieldValue() {
      return this.fieldValue;
   }


   public boolean isNullValue() {
      return this.isNullable && this.fieldValue == null;
   }


   public String getTypedFieldValue() throws MssException {
      if (isNullValue())
         return "null";

      if (!this.isNullable && this.fieldValue == null)
         throw new MssException(
               de.mss.dbutils.exception.ErrorCodes.ERROR_NON_NULLABLE_VALUE_IS_NULL,
               "value for field " + this.fieldName + " must not be null");

      switch (this.fieldType) {
         case java.sql.Types.CHAR:
         case java.sql.Types.LONGNVARCHAR:
         case java.sql.Types.LONGVARCHAR:
         case java.sql.Types.NCHAR:
         case java.sql.Types.NVARCHAR:
         case java.sql.Types.VARCHAR:
            return getStringValue();

         case java.sql.Types.BIGINT:
         case java.sql.Types.INTEGER:
            return getIntegerValue();

         case java.sql.Types.DECIMAL:
         case java.sql.Types.DOUBLE:
         case java.sql.Types.FLOAT:
            return getDecimalValue();

         case java.sql.Types.DATE:
            return getDateValue();

         case java.sql.Types.TIME:
         case java.sql.Types.TIME_WITH_TIMEZONE:
            return getTimeValue();

         case java.sql.Types.TIMESTAMP:
         case java.sql.Types.TIMESTAMP_WITH_TIMEZONE:
            return getTimestampValue();

         case java.sql.Types.BOOLEAN:
            return getBooleanValue();

         default:
            return this.fieldValue.toString();
      }
   }


   public void setFieldName(String n) {
      this.fieldName = n;
   }


   public String getFieldName() {
      return this.fieldName;
   }


   protected String getStringValue() throws MssException {
      if (!(this.fieldValue instanceof String))
         throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE, "field " + this.fieldName + " is not an instance of string");

      return "'" + this.fieldValue + "'";
   }


   protected String getIntegerValue() throws MssException {
      if (this.fieldValue instanceof BigInteger)
         return this.fieldValue.toString();

      if (this.fieldValue instanceof Integer)
         return this.fieldValue.toString();

      throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE, "field " + this.fieldName + " is not an instance of integer");
   }


   protected String getDecimalValue() throws MssException {
      if (this.fieldValue instanceof BigDecimal)
         return this.fieldValue.toString();

      if (this.fieldValue instanceof Double)
         return this.fieldValue.toString();

      if (this.fieldValue instanceof Float)
         return this.fieldValue.toString();

      throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE, "field " + this.fieldName + " is not an instance of decimal");
   }


   protected String getDateValue() throws MssException {
      return "'" + getDateValue("yyyy-MM-dd") + "'";
   }


   protected String getTimeValue() throws MssException {
      return "'" + getDateValue("HH:mm:ss") + "'";
   }


   protected String getTimestampValue() throws MssException {
      return "'" + getDateValue("yyyy-MM-dd HH:mm:ss.SSS") + "'";
   }


   protected String getDateValue(String format) throws MssException {
      if (this.fieldValue instanceof java.util.Date)
         return new SimpleDateFormat(format).format(this.fieldValue);

      throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE, "field " + this.fieldName + " is not an instance of date");
   }


   protected String getBooleanValue() throws MssException {
      if (this.fieldValue instanceof Boolean)
         return Boolean.TRUE.compareTo((Boolean)this.fieldValue) == 0 ? "1" : "0";

      throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_WRONG_TYPE, "field " + this.fieldName + " is not an instance of boolean");
   }

}
