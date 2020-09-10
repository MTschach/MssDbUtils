package de.mss.dbutils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;

public class MssSingleConnectionTest extends DbBaseTest {

   @Override
   public void setUp() throws Exception {
      super.setUp();
   }


   @Test
   public void testConstructor() throws MssException {
      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);

      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server);
      
      assertFalse("is busy", c.isBusy());
      assertEquals("Used count", Long.valueOf(0), Long.valueOf(c.getUsedCount()));

      c = new MssSingleConnection(null, this.server, this.connectionMock);

      assertEquals("DBConnection", this.connectionMock, c.getDbConnection());
      assertFalse("is busy", c.isBusy());
      assertEquals("Used count", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testConstructorNullLogger() throws MssException {
      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server);

      assertFalse("is busy", c.isBusy());
      assertEquals("Used count", Long.valueOf(0), Long.valueOf(c.getUsedCount()));

      c = new MssSingleConnection(null, this.server, this.connectionMock);

      assertEquals("DBConnection", this.connectionMock, c.getDbConnection());
      assertFalse("is busy", c.isBusy());
      assertEquals("Used count", Long.valueOf(0), Long.valueOf(c.getUsedCount()));

   }


   @SuppressWarnings("boxing")
   @Test
   public void testCloseConnectionIsNull() throws SQLException, MssException {
      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).times(2);
      this.connectionMock.close();
      EasyMock.expectLastCall();

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);

      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server);
      c.close();

      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));

      EasyMock.verify(this.connectionMock);
   }


   @SuppressWarnings("boxing")
   @Test
   public void testCloseAlreadyClosed() throws SQLException {
      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.TRUE);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);

      c.close();
      assertNull("DBConnection", c.getDbConnection());

      EasyMock.verify(this.connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @SuppressWarnings("boxing")
   @Test
   public void testCloseWithException() throws SQLException {
      EasyMock.expect(this.connectionMock.isClosed()).andThrow(new SQLException());

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);

      try {
         c.close();
         fail();
      }
      catch (SQLException e) {
         e.printStackTrace();
      }

      EasyMock.verify(this.connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @SuppressWarnings("boxing")
   @Test
   public void testCloseWithExceptionOnClose() throws SQLException {
      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).times(2);
      this.connectionMock.close();
      EasyMock.expectLastCall().andThrow(new SQLException());

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);

      try {
         c.close();
         fail();
      }
      catch (SQLException e) {
         e.printStackTrace();
      }

      EasyMock.verify(this.connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @SuppressWarnings("boxing")
   @Test
   public void testCloseAlreadyClosed_0002() throws SQLException {
      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);

      c.close();

      EasyMock.verify(this.connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @SuppressWarnings("boxing")
   @Test
   public void testCloseOk() throws SQLException {
      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).times(2);
      this.connectionMock.close();
      EasyMock.expectLastCall();

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);

      c.close();

      EasyMock.verify(this.connectionMock);
      assertNull("DBConnection", c.getDbConnection());
      assertEquals("UsedCount", Long.valueOf(0), Long.valueOf(c.getUsedCount()));
   }


   @Test
   public void testConnectAndIsConnected() throws MssException {
      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server);

      try {
         c.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertEquals("DBConnection", this.connectionMock, c.getDbConnection());
      assertTrue("connected", c.isConnected());
   }


   @Test
   public void testExecuteUpdateExceptioOnPrepare() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2")))
            .andThrow(new SQLException());

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);
      try {
         c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1007, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testExecuteUpdateStmtNull() {
      String loggingId = Tools.getId(new Throwable());

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      try {
         c.executeUpdate(loggingId, (PreparedStatement)null);
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @SuppressWarnings("boxing")
   @Test
   public void testExecuteUpdateExceptionWhileExec() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andThrow(new SQLException());

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock);
      try {
         c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1007, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @SuppressWarnings("boxing")
   @Test
   public void testExecuteUpdateNonEffected() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(0);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock);
      try {
         int ret = c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");

         assertEquals("Rows affected", 0, ret);
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @SuppressWarnings("boxing")
   @Test
   public void testExecuteUpdateEffected() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(2);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column1 = 1 where column2 = 2"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock);
      try {
         int ret = c.executeUpdate(loggingId, "update table1 set column1 = 1 where column2 = 2");

         assertEquals("Rows affected", 2, ret);
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testExecuteQueryExceptioOnPrepare() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andThrow(new SQLException());

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1006, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testExecuteQueryStmtNull() {
      String loggingId = Tools.getId(new Throwable());

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      try {
         c.executeQuery(loggingId, (PreparedStatement)null);
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 2, e.getError().getErrorCode());
      }
   }


   @Test
   public void testExecuteQueryExceptionWhileExec() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andThrow(new SQLException());

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1006, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @SuppressWarnings({"boxing", "null"})
   @Test
   public void testExecuteQueryResultNull() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(null);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock);
      MssResultSet res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);

      assertNotNull("Result not null", res);
   }


   @Test
   public void testExecuteQueryOk() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.isClosed()).andReturn(false);
      resultMock.close();
      EasyMock.expectLastCall();

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock);
      try (MssResultSet res = c.executeQuery(loggingId, "select * from table1");) {
         assertNotNull("Result not null", res);

         checkResult(res, 0, "Cell");
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock);

   }


   private void checkResult(MssResultSet res, int resultSetNumber, String prefix) {
   }
}
