package de.mss.dbutils;

import java.math.BigInteger;
import java.util.List;

import de.mss.utils.exception.MssException;

public class BaseStatementForTest extends BaseStatement<BigInteger, Integer> {

   private static final long serialVersionUID = 1L;


   public BaseStatementForTest() {
      this.tableName = "TEST_TABLE";
   }


   @Override
   public BigInteger execute(String loggingId, Integer in, MssConnection con) {
      return BigInteger.valueOf(in.intValue());
   }


   public String getSelectStatement(List<QueryParam> fields, List<QueryParam> params) throws MssException {
      return super.getSelectStatement(fields, params);
   }


   public String getUpdateStatement(List<QueryParam> newValues, List<QueryParam> params) throws MssException {
      return super.getUpdateStatement(newValues, params);
   }


   public String getDeleteStatement(List<QueryParam> params) throws MssException {
      return super.getDeleteStatement(params);
   }


   public String getWhereClause(List<QueryParam> params) throws MssException {
      return super.getWhereClause(params);
   }


   public String getSetClause(List<QueryParam> values) throws MssException {
      return super.getSetClause(values);
   }


   public String getSelectClause(List<QueryParam> fields) {
      return super.getSelectClause(fields);
   }
}
