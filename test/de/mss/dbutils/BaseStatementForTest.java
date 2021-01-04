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
   public BigInteger doExecute(String loggingId, Integer in, MssConnection con) {
      return BigInteger.valueOf(in.intValue());
   }


   @Override
   public String getSelectStatement(List<QueryParam> fields, List<QueryParam> params) throws MssException {
      return super.getSelectStatement(fields, params);
   }


   @Override
   public String getUpdateStatement(List<QueryParam> newValues, List<QueryParam> params) throws MssException {
      return super.getUpdateStatement(newValues, params);
   }


   @Override
   public String getDeleteStatement(List<QueryParam> params) throws MssException {
      return super.getDeleteStatement(params);
   }


   @Override
   public String getWhereClause(List<QueryParam> params) throws MssException {
      return super.getWhereClause(params);
   }


   @Override
   public String getSetClause(List<QueryParam> values) throws MssException {
      return super.getSetClause(values);
   }


   @Override
   public String getSelectClause(List<QueryParam> fields) {
      return super.getSelectClause(fields);
   }
}
