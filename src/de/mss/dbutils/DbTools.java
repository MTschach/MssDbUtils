package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DbTools {

   private DbTools() {}


   public static BigDecimal getBigDecimal(ResultSet res, int i) throws SQLException {
      return getBigDecimal(res, i, null);
   }


   public static BigDecimal getBigDecimal(ResultSet res, int i, BigDecimal defaultValue) throws SQLException {
      final BigDecimal ret = res.getBigDecimal(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static BigDecimal getBigDecimal(ResultSet res, String n) throws SQLException {
      return getBigDecimal(res, n, null);
   }


   public static BigDecimal getBigDecimal(ResultSet res, String n, BigDecimal defaultValue) throws SQLException {
      final BigDecimal ret = res.getBigDecimal(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static BigInteger getBigInteger(ResultSet res, int i) throws SQLException {
      return getBigInteger(res, i, null);
   }


   public static BigInteger getBigInteger(ResultSet res, int i, BigInteger defaultValue) throws SQLException {
      final BigInteger ret = BigInteger.valueOf(res.getLong(i));
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static BigInteger getBigInteger(ResultSet res, String n) throws SQLException {
      return getBigInteger(res, n, null);
   }


   public static BigInteger getBigInteger(ResultSet res, String n, BigInteger defaultValue) throws SQLException {
      final BigInteger ret = BigInteger.valueOf(res.getLong(n));
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Boolean getBoolean(ResultSet res, int i) throws SQLException {
      return getBoolean(res, i, null);
   }


   public static Boolean getBoolean(ResultSet res, int i, Boolean defaultValue) throws SQLException {
      final Boolean ret = res.getBoolean(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Boolean getBoolean(ResultSet res, String n) throws SQLException {
      return getBoolean(res, n, null);
   }


   public static Boolean getBoolean(ResultSet res, String n, Boolean defaultValue) throws SQLException {
      final Boolean ret = res.getBoolean(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Byte getByte(ResultSet res, int i) throws SQLException {
      return getByte(res, i, null);
   }


   public static Byte getByte(ResultSet res, int i, Byte defaultValue) throws SQLException {
      final Byte ret = res.getByte(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Byte getByte(ResultSet res, String n) throws SQLException {
      return getByte(res, n, null);
   }


   public static Byte getByte(ResultSet res, String n, Byte defaultValue) throws SQLException {
      final Byte ret = res.getByte(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Date getDate(ResultSet res, int i) throws SQLException {
      return getDate(res, i, null);
   }


   public static Date getDate(ResultSet res, int i, Date defaultValue) throws SQLException {
      final Date ret = res.getDate(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Date getDate(ResultSet res, String n) throws SQLException {
      return getDate(res, n, null);
   }


   public static Date getDate(ResultSet res, String n, Date defaultValue) throws SQLException {
      final Date ret = res.getDate(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Double getDouble(ResultSet res, int i) throws SQLException {
      return getDouble(res, i, null);
   }


   public static Double getDouble(ResultSet res, int i, Double defaultValue) throws SQLException {
      final Double ret = res.getDouble(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Double getDouble(ResultSet res, String n) throws SQLException {
      return getDouble(res, n, null);
   }


   public static Double getDouble(ResultSet res, String n, Double defaultValue) throws SQLException {
      final Double ret = res.getDouble(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Float getFloat(ResultSet res, int i) throws SQLException {
      return getFloat(res, i, null);
   }


   public static Float getFloat(ResultSet res, int i, Float defaultValue) throws SQLException {
      final Float ret = res.getFloat(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Float getFloat(ResultSet res, String n) throws SQLException {
      return getFloat(res, n, null);
   }


   public static Float getFloat(ResultSet res, String n, Float defaultValue) throws SQLException {
      final Float ret = res.getFloat(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Integer getInteger(ResultSet res, int i) throws SQLException {
      return getInteger(res, i, null);
   }


   public static Integer getInteger(ResultSet res, int i, Integer defaultValue) throws SQLException {
      final Integer ret = res.getInt(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Integer getInteger(ResultSet res, String n) throws SQLException {
      return getInteger(res, n, null);
   }


   public static Integer getInteger(ResultSet res, String n, Integer defaultValue) throws SQLException {
      final Integer ret = res.getInt(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Long getLong(ResultSet res, int i) throws SQLException {
      return getLong(res, i, null);
   }


   public static Long getLong(ResultSet res, int i, Long defaultValue) throws SQLException {
      final Long ret = res.getLong(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Long getLong(ResultSet res, String n) throws SQLException {
      return getLong(res, n, null);
   }


   public static Long getLong(ResultSet res, String n, Long defaultValue) throws SQLException {
      final Long ret = res.getLong(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Short getShort(ResultSet res, int i) throws SQLException {
      return getShort(res, i, null);
   }


   public static Short getShort(ResultSet res, int i, Short defaultValue) throws SQLException {
      final Short ret = res.getShort(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Short getShort(ResultSet res, String n) throws SQLException {
      return getShort(res, n, null);
   }


   public static Short getShort(ResultSet res, String n, Short defaultValue) throws SQLException {
      final Short ret = res.getShort(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static String getString(ResultSet res, int i) throws SQLException {
      return getString(res, i, null);
   }


   public static String getString(ResultSet res, int i, String defaultValue) throws SQLException {
      final String ret = res.getString(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static String getString(ResultSet res, String n) throws SQLException {
      return getString(res, n, null);
   }


   public static String getString(ResultSet res, String n, String defaultValue) throws SQLException {
      final String ret = res.getString(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Date getTime(ResultSet res, int i) throws SQLException {
      return getTime(res, i, null);
   }


   public static Date getTime(ResultSet res, int i, Date defaultValue) throws SQLException {
      final Date ret = res.getTime(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Date getTime(ResultSet res, String n) throws SQLException {
      return getTime(res, n, null);
   }


   public static Date getTime(ResultSet res, String n, Date defaultValue) throws SQLException {
      final Date ret = res.getTime(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Date getTimestamp(ResultSet res, int i) throws SQLException {
      return getTimestamp(res, i, null);
   }


   public static Date getTimestamp(ResultSet res, int i, Date defaultValue) throws SQLException {
      final Date ret = res.getTimestamp(i);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }


   public static Date getTimestamp(ResultSet res, String n) throws SQLException {
      return getTimestamp(res, n, null);
   }


   public static Date getTimestamp(ResultSet res, String n, Date defaultValue) throws SQLException {
      final Date ret = res.getTimestamp(n);
      if (res.wasNull()) {
         return defaultValue;
      }

      return ret;
   }
}
