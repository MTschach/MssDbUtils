package de.mss.dbutils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;

public class BaseStatementTest extends DbBaseTest {

   private BaseStatementForTest classUnderTest = null;
   private List<QueryParam>     fields         = null;
   private List<QueryParam>     params         = null;

   @Override
   public void setUp() throws Exception {
      super.setUp();

      this.classUnderTest = new BaseStatementForTest();
      this.fields = new ArrayList<>();
      this.params = new ArrayList<>();
   }


   @Test
   public void testExecute() {
      String loggingId = "";
      assertEquals(BigInteger.valueOf(12), this.classUnderTest.execute(loggingId, Integer.valueOf(12), null));
   }


   @Test
   public void testGetSelectClause() {
      assertEquals("*", this.classUnderTest.getSelectClause(null));
      assertEquals("*", this.classUnderTest.getSelectClause(this.fields));

      this.fields.add(new QueryParam("ID_TEST_TABLE", null, java.sql.Types.INTEGER));
      assertEquals("ID_TEST_TABLE", this.classUnderTest.getSelectClause(this.fields));

      this.fields.add(new QueryParam("NAME", null, java.sql.Types.VARCHAR));
      assertEquals("ID_TEST_TABLE, NAME", this.classUnderTest.getSelectClause(this.fields));

      this.fields.add(new QueryParam("DATE_INSERT", null, java.sql.Types.TIMESTAMP));
      assertEquals("ID_TEST_TABLE, NAME, DATE_INSERT", this.classUnderTest.getSelectClause(this.fields));
   }


   @Test
   public void testGetSetClause() throws MssException {
      try {
         this.classUnderTest.getSetClause(null);
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5002, e.getError().getErrorCode());
      }
      try {
         this.classUnderTest.getSetClause(this.fields);
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5002, e.getError().getErrorCode());
      }

      this.fields.add(new QueryParam("ID_TEST_TABLE", Integer.valueOf(2), java.sql.Types.INTEGER));
      assertEquals("ID_TEST_TABLE = 2", this.classUnderTest.getSetClause(this.fields));

      this.fields.add(new QueryParam("NAME", "egal", java.sql.Types.VARCHAR));
      assertEquals("ID_TEST_TABLE = 2, NAME = 'egal'", this.classUnderTest.getSetClause(this.fields));

      java.util.Date stamp = new java.util.Date();
      this.fields.add(new QueryParam("DATE_INSERT", stamp, java.sql.Types.TIMESTAMP));
      assertEquals(
            "ID_TEST_TABLE = 2, NAME = 'egal', DATE_INSERT = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(stamp) + "'",
            this.classUnderTest.getSetClause(this.fields));
   }


   @Test
   public void testGetWhereClause() throws MssException {
      try {
         this.classUnderTest.getWhereClause(null);
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5002, e.getError().getErrorCode());
      }
      try {
         this.classUnderTest.getWhereClause(this.params);
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5002, e.getError().getErrorCode());
      }

      this.params.add(new QueryParam("ID_TEST_TABLE", Integer.valueOf(2), java.sql.Types.INTEGER));
      assertEquals("where ID_TEST_TABLE = 2", this.classUnderTest.getWhereClause(this.params));

      this.params.add(new QueryParam("NAME", "egal", java.sql.Types.VARCHAR));
      assertEquals("where ID_TEST_TABLE = 2 and NAME = 'egal'", this.classUnderTest.getWhereClause(this.params));

      java.util.Date stamp = new java.util.Date();
      this.params.add(new QueryParam("DATE_INSERT", stamp, java.sql.Types.TIMESTAMP));
      assertEquals(
            "where ID_TEST_TABLE = 2 and NAME = 'egal' and DATE_INSERT = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(stamp) + "'",
            this.classUnderTest.getWhereClause(this.params));
   }


   @Test
   public void testGetSelectStatement() throws MssException {
      java.util.Date stamp = new java.util.Date();
      setupFieldsAndParams(stamp);

      assertEquals(
            "select ID_TEST_TABLE, RECORD_STATE, DATE_UPDATE, ID_SYS_USER_UPDATE from TEST_TABLE where TABLE_NAME = 'SYS_USER' and DATE_INSERT = '"
                  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(stamp)
                  + "' and ID_TABLE_PK = 12 and FIELD_NAME is null",
            this.classUnderTest.getSelectStatement(this.fields, this.params));
   }


   @Test
   public void testGetUpdateStatement() throws MssException {
      java.util.Date stamp = new java.util.Date();
      setupFieldsAndParams(stamp);

      assertEquals(
            "update TEST_TABLE set ID_TEST_TABLE = 2, RECORD_STATE = 'A', DATE_UPDATE = '"
                  + new SimpleDateFormat("yyyy-MM-dd").format(stamp)
                  + "', ID_SYS_USER_UPDATE = 3 where TABLE_NAME = 'SYS_USER' and DATE_INSERT = '"
                  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(stamp)
                  + "' and ID_TABLE_PK = 12 and FIELD_NAME is null",
            this.classUnderTest.getUpdateStatement(this.fields, this.params));
   }


   @Test
   public void testGetDeleteStatement() throws MssException {
      java.util.Date stamp = new java.util.Date();
      setupFieldsAndParams(stamp);

      assertEquals(
            "delete from TEST_TABLE where TABLE_NAME = 'SYS_USER' and DATE_INSERT = '"
                  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(stamp)
                  + "' and ID_TABLE_PK = 12 and FIELD_NAME is null",
            this.classUnderTest.getDeleteStatement(this.params));
   }


   @Test
   public void testPrepareStatementNullCon() {
      String loggingId = Tools.getId(new Throwable());
      try {
         BaseStatement.prepareStatement(loggingId, null, getDefaultSql(), getDefaultValues(), getDefaultTypes());
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(2, e.getError().getErrorCode());
      }
   }


   @Test
   public void testPrepareStatementWrongTypes() throws SQLException {
      String loggingId = Tools.getId(new Throwable());
      setUpPreparedStatement(false);
      EasyMock.replay(this.connectionMock);
      try {
         BaseStatement.prepareStatement(loggingId, this.mssConnectionMock, getDefaultSql(), getDefaultValues(), getWrongTypes());
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5004, e.getError().getErrorCode());
      }
      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testPrepareStatementWithSQLException() throws SQLException {
      String loggingId = Tools.getId(new Throwable());
      setUpPreparedStatement(true);
      EasyMock.replay(this.connectionMock);
      try {
         BaseStatement.prepareStatement(loggingId, this.mssConnectionMock, getDefaultSql(), getDefaultValues(), getWrongTypes());
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5006, e.getError().getErrorCode());
      }
      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testPrepareStatement() throws SQLException, MssException {
      String loggingId = Tools.getId(new Throwable());
      try (PreparedStatement pstmtMock = setUpPreparedStatement(false)) {
         EasyMock.replay(this.connectionMock, pstmtMock);
         BaseStatement.prepareStatement(loggingId, this.mssConnectionMock, getDefaultSql(), getDefaultValues(), getDefaultTypes());

         EasyMock.verify(this.connectionMock, pstmtMock);
      }
   }


   @Test
   public void testPrepareCallNullCon() {
      String loggingId = Tools.getId(new Throwable());
      try {
         BaseStatement.prepareCall(loggingId, null, getDefaultSql(), getDefaultValues(), getDefaultTypes());
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(2, e.getError().getErrorCode());
      }
   }


   @Test
   public void testPrepareCallWrongTypes() throws SQLException {
      String loggingId = Tools.getId(new Throwable());
      setUpCallStatement(false);
      EasyMock.replay(this.connectionMock);
      try {
         BaseStatement.prepareCall(loggingId, this.mssConnectionMock, getDefaultSql(), getDefaultValues(), getWrongTypes());
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5004, e.getError().getErrorCode());
      }
      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testPrepareCallWithSQLException() throws SQLException {
      String loggingId = Tools.getId(new Throwable());
      setUpCallStatement(true);
      EasyMock.replay(this.connectionMock);
      try {
         BaseStatement.prepareCall(loggingId, this.mssConnectionMock, getDefaultSql(), getDefaultValues(), getWrongTypes());
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5006, e.getError().getErrorCode());
      }
      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testPrepareCall() throws SQLException, MssException {
      String loggingId = Tools.getId(new Throwable());
      try (PreparedStatement pstmtMock = setUpCallStatement(false)) {
         EasyMock.replay(this.connectionMock, pstmtMock);
         BaseStatement.prepareCall(loggingId, this.mssConnectionMock, getDefaultSql(), getDefaultValues(), getDefaultTypes());

         EasyMock.verify(this.connectionMock, pstmtMock);
      }
   }


   private void setupFieldsAndParams(java.util.Date stamp) {
      this.fields.add(new QueryParam("ID_TEST_TABLE", Integer.valueOf(2), java.sql.Types.INTEGER));
      this.fields.add(new QueryParam("RECORD_STATE", "A", java.sql.Types.VARCHAR));
      this.fields.add(new QueryParam("DATE_UPDATE", stamp, java.sql.Types.DATE));
      this.fields.add(new QueryParam("ID_SYS_USER_UPDATE", Integer.valueOf(3), java.sql.Types.INTEGER));

      this.params.add(new QueryParam("TABLE_NAME", "SYS_USER", java.sql.Types.VARCHAR));
      this.params.add(new QueryParam("DATE_INSERT", stamp, java.sql.Types.TIMESTAMP));
      this.params.add(new QueryParam("ID_TABLE_PK", Integer.valueOf(12), java.sql.Types.INTEGER));
      QueryParam q = new QueryParam("FIELD_NAME", null, java.sql.Types.VARCHAR);
      q.setNullable(true);
      this.params.add(q);
   }


   private Object[] getDefaultValues() {
      return new Object[] {Integer.valueOf(1), new String("lala"), new BigDecimal("1.23")};
   }


   private int[] getDefaultTypes() {
      return new int[] {java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.BIGINT};
   }


   private int[] getWrongTypes() {
      return new int[] {java.sql.Types.INTEGER, java.sql.Types.VARCHAR, java.sql.Types.BIGINT, java.sql.Types.NVARCHAR};
   }


   private String getDefaultSql() {
      return "select * from TABLE1 where ID_TABLE1 = ? and NAME = ? and VALUE = ?";
   }


   private PreparedStatement setUpPreparedStatement(boolean withException) throws SQLException {
      if (withException) {
         EasyMock
               .expect(
                     this.connectionMock
                           .prepareStatement(
                                 EasyMock.eq(getDefaultSql()),
                                 EasyMock.eq(1004),
                                 EasyMock.eq(1007)))
               .andThrow(new SQLException("Error while creating a prepared statement"));
         return null;
      }

      PreparedStatement pstmtMock = EasyMock.createMock(PreparedStatement.class);
               EasyMock
                     .expect(
                           this.connectionMock
                                 .prepareStatement(
                                       EasyMock.eq(getDefaultSql()),
                              EasyMock.eq(1004),
                              EasyMock.eq(1007)))
                     .andReturn(pstmtMock);

      setUpStatementCalls(pstmtMock);

      return pstmtMock;
   }


   private CallableStatement setUpCallStatement(boolean withException) throws SQLException {
      if (withException) {
         EasyMock
               .expect(
                     this.connectionMock
                           .prepareCall(
                                 EasyMock.eq(getDefaultSql()),
                                 EasyMock.eq(1004),
                                 EasyMock.eq(1007)))
               .andThrow(new SQLException("Error while creating a prepared statement"));
         return null;
      }

      CallableStatement pstmtMock = EasyMock.createMock(CallableStatement.class);
      EasyMock
            .expect(
                  this.connectionMock
                        .prepareCall(
                              EasyMock.eq(getDefaultSql()),
                              EasyMock.eq(1004),
                              EasyMock.eq(1007)))
            .andReturn(pstmtMock);

      setUpStatementCalls(pstmtMock);

      return pstmtMock;
   }


   private void setUpStatementCalls(PreparedStatement stmt) throws SQLException {
      stmt.setInt(EasyMock.eq(1), EasyMock.eq(1));
      EasyMock.expectLastCall();
      stmt.setString(EasyMock.eq(2), EasyMock.eq("lala"));
      EasyMock.expectLastCall();
      stmt.setBigDecimal(EasyMock.eq(3), EasyMock.eq(new BigDecimal("1.23")));
      EasyMock.expectLastCall();

      stmt.close();
      EasyMock.expectLastCall().anyTimes();

      EasyMock.expect(stmt.getConnection()).andReturn(this.connectionMock);
   }
}
