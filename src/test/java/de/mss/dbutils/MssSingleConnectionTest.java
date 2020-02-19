package de.mss.dbutils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.easymock.EasyMock;
import org.junit.Test;

import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;

public class MssSingleConnectionTest extends DbBaseTest {

   @Override
   public void setUp() throws MssException {
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
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.FALSE);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock);
      MssResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 0", 0, res.getNumberOfResults());
   }


   @SuppressWarnings({"boxing", "null"})
   @Test
   public void testExecuteQuerySingleResult() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.FALSE);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      MssResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 1", 1, res.getNumberOfResults());
      assertEquals("ColumnCount = 4", 4, res.getColumnCount(0));
      assertEquals("RowCount = 2", 2, res.getRowCount(0));

      checkResult(res, 0, "Cell");
   }


   @SuppressWarnings({"boxing", "null"})
   @Test
   public void testExecuteQuerySingleResult2ndNull() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.TRUE).andReturn(Boolean.FALSE);
      EasyMock.expect(stmtMock.getResultSet()).andReturn(null);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      MssResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 1", 1, res.getNumberOfResults());
      assertEquals("ColumnCount = 4", 4, res.getColumnCount(0));
      assertEquals("RowCount = 2", 2, res.getRowCount(0));

      checkResult(res, 0, "Cell");
   }


   @SuppressWarnings("boxing")
   @Test
   public void testExecuteQuerySingleResult2ndException() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andThrow(new SQLException());

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1008, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);
   }


   @SuppressWarnings("boxing")
   @Test
   public void testExecuteQuerySingleResult2ndException_0002() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.TRUE);
      EasyMock.expect(stmtMock.getResultSet()).andThrow(new SQLException());

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      try {
         c.executeQuery(loggingId, "select * from table1");
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1008, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);
   }


   @SuppressWarnings({"boxing", "null"})
   @Test
   public void testExecuteQueryMultipleResult() throws SQLException {
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6).andReturn(Integer.valueOf(2)).times(4);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1").andReturn("VALUE1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2").andReturn("Value2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      @SuppressWarnings("resource")
      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock).times(2);
      EasyMock
            .expect(resultMock.next())
            .andReturn(Boolean.TRUE)
            .times(2)
            .andReturn(Boolean.FALSE)
            .andReturn(Boolean.TRUE)
            .times(3)
            .andReturn(Boolean.FALSE);
      EasyMock
            .expect(resultMock.getString(1))
            .andReturn("Cell0x0")
            .andReturn("Cell1x0")
            .andReturn("Value 0x0")
            .andReturn("Value 1x0")
            .andReturn("Value 2x0");
      EasyMock
            .expect(resultMock.getString(2))
            .andReturn("Cell0x1")
            .andReturn("Cell1x1")
            .andReturn("Value 0x1")
            .andReturn("Value 1x1")
            .andReturn("Value 2x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall().times(2);

      @SuppressWarnings("resource")
      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.TRUE).andReturn(Boolean.FALSE);
      EasyMock.expect(stmtMock.getResultSet()).andReturn(resultMock);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);
      @SuppressWarnings("resource")
      MssSingleConnection c = new MssSingleConnection(null, this.server, this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      MssResult res = null;
      try {
         res = c.executeQuery(loggingId, "select * from table1");
      }
      catch (@SuppressWarnings("unused") MssException e) {
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);

      assertNotNull("Result not null", res);
      assertEquals("ResultCount = 2", 2, res.getNumberOfResults());
      assertEquals("ColumnCount(0) = 4", 4, res.getColumnCount(0));
      assertEquals("RowCount(0) = 2", 2, res.getRowCount(0));
      assertEquals("ColumnCount(1) = 2", 2, res.getColumnCount(1));
      assertEquals("RowCount(1) = 3", 3, res.getRowCount(1));

      checkResult(res, 0, "Cell");
      checkResult(res, 1, "Value ");
   }


   private void checkResult(MssResult res, int resultSetNumber, String prefix) {
      for (int r = 0; r < res.getRowCount(resultSetNumber); r++ ) {
         for (int c = 0; c < res.getColumnCount(resultSetNumber); c++ ) {
            assertEquals("Cellvalue " + r + "x" + c, prefix + r + "x" + c, res.getValue(resultSetNumber, c, r));
         }
      }
   }
}
