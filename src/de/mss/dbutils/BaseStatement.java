package de.mss.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import de.mss.utils.exception.MssException;

public abstract class BaseStatement<R, I> implements Serializable {

   private static final long serialVersionUID = -6182585533542075363L;

   protected String          tableName        = null;


   protected void checkInput(String loggingId, I in, MssConnection con) throws MssException {
      if (in == null) {
         throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_INVALID_STATEMENT_INPUT, "the input for a statement must not be null");
      }
   }


   protected abstract R doExecute(String loggingId, I in, MssConnection con) throws MssException;


   public R execute(String loggingId, I in, MssConnection con) throws MssException {
      checkInput(loggingId, in, con);

      final R ret = doExecute(loggingId, in, con);

      return workOnOutput(loggingId, ret);
   }


   @SuppressWarnings("unused")
   protected R workOnOutput(String loggingId, R output) throws MssException {
      return output;
   }


   protected String getSelectStatement(List<QueryParam> fields, List<QueryParam> params) throws MssException {
      return "select " + getSelectClause(fields) + " from " + this.tableName + " " + getWhereClause(params);
   }


   protected String getUpdateStatement(List<QueryParam> newValues, List<QueryParam> params) throws MssException {
      return "update " + this.tableName + " set " + getSetClause(newValues) + " " + getWhereClause(params);
   }


   protected String getDeleteStatement(List<QueryParam> params) throws MssException {
      return "delete from " + this.tableName + " " + getWhereClause(params);
   }


   @SuppressWarnings("static-method")
   protected String getWhereClause(List<QueryParam> params) throws MssException {
      final StringBuilder sb = new StringBuilder();

      if (params != null && !params.isEmpty()) {
         sb.append("where ");
         boolean first = true;
         for (final QueryParam q : params) {
            if (!first) {
               sb.append(" and ");
            }

            sb.append(q.getFieldName());
            if (q.isNullValue()) {
               sb.append(" is ");
            } else {
               sb.append(" = ");
            }

            sb.append(q.getTypedFieldValue());

            first = false;
         }
      }

      return sb.toString();
   }


   @SuppressWarnings("static-method")
   protected String getSetClause(List<QueryParam> values) throws MssException {
      final StringBuilder sb = new StringBuilder();

      if (values == null || values.isEmpty()) {
         throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_SET_LIST_IS_EMPTY, "Set clause must not be empty");
      }

      boolean first = true;

      for (final QueryParam v : values) {
         if (!first) {
            sb.append(", ");
         }

         sb.append(v.getFieldName());
         sb.append(" = ");
         sb.append(v.getTypedFieldValue());
         first = false;
      }

      return sb.toString();
   }


   @SuppressWarnings("static-method")
   protected String getSelectClause(List<QueryParam> fields) {
      final StringBuilder sb = new StringBuilder();

      if (fields == null || fields.isEmpty()) {
         return "*";
      }

      boolean first = true;

      for (final QueryParam f : fields) {
         if (!first) {
            sb.append(", ");
         }

         sb.append(f.getFieldName());
         first = false;
      }

      return sb.toString();
   }


   protected static MssPreparedStatement prepareStatement(String loggingId, MssConnection con, String sql, Object[] values, int[] types)
         throws MssException {
      return prepareStatement(loggingId, con, sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, values, types);
   }


   @SuppressWarnings("resource")
   protected static MssPreparedStatement prepareStatement(
         String loggingId,
         MssConnection con,
         String sql,
         int resultSetType,
         int resultSetConcurrency,
         Object[] values,
         int[] types)
         throws MssException {
      if (con == null) {
         throw new MssException(de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM, "no connection given");
      }

      MssPreparedStatement pstmt = null;
      try {
         pstmt = (MssPreparedStatement)con.prepareStatement(loggingId, sql, resultSetType, resultSetConcurrency);
         pstmt.setSqlParams(values, types);
         return pstmt;
      }
      catch (final SQLException e) {
         throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_PREPARING_STATEMENT, e, "could not prepare statement");
      }
   }


   protected static MssCallableStatement prepareCall(String loggingId, MssConnection con, String sql, Object[] values, int[] types)
         throws MssException {
      return prepareCall(loggingId, con, sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, values, types);
   }


   @SuppressWarnings("resource")
   protected static MssCallableStatement prepareCall(
         String loggingId,
         MssConnection con,
         String sql,
         int resultSetType,
         int resultSetConcurrency,
         Object[] values,
         int[] types)
         throws MssException {
      if (con == null) {
         throw new MssException(de.mss.utils.exception.ErrorCodes.ERROR_INVALID_PARAM, "no connection given");
      }

      MssCallableStatement pstmt = null;
      try {
         pstmt = (MssCallableStatement)con.prepareCall(loggingId, sql, resultSetType, resultSetConcurrency);
         pstmt.setSqlParams(values, types);
         return pstmt;
      }
      catch (final SQLException e) {
         throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_PREPARING_STATEMENT, e, "could not prepare statement");
      }
   }
}
