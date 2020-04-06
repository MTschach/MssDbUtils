package de.mss.dbutils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.easymock.EasyMock;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;

public class MssConnectionTest extends DbBaseTest {

   private static final String STR_CONNECTION   = "Connection";
   private static final String STR_AFTER_CLOSE      = "after close";
   private static final String STR_BEFORE_RECONNECT = "before re-connect";

   @Test
   public void testConstructor() throws MssException {
      List<MssDbServer> list = prepareServerList(1);
      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list.get(0));
      assertNotNull(STR_CONNECTION, con);

      con = new MssConnection(null, list.toArray(new MssDbServer[list.size()]));
      assertNotNull(STR_CONNECTION, con);

      con = new MssConnection(null, list);
      assertNotNull(STR_CONNECTION, con);
   }


   @Test
   public void testConnectAndClose() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);

      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).times(3);
      this.connectionMock.close();
      EasyMock.expectLastCall();

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);
      
      PowerMock.replay(this.connectionMock);

      assertTrue("before close", con.isConnected());

      try {
         con.close();
      }
      catch (MssException e) {
         Tools.doNullLog(e);
         fail();
      }

      assertFalse(STR_AFTER_CLOSE, con.isConnected());

      PowerMock.verify(this.connectionMock);
   }


   @Test
   public void testConnectAlready() throws MssException {
      List<MssDbServer> list = prepareServerList(1);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      assertTrue(STR_BEFORE_RECONNECT, con.isConnected());

      try {
         con.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertTrue("after re-connect", con.isConnected());
   }


   @Test
   public void testClosedAlready() throws MssException, SQLException {
      List<MssDbServer> list = prepareServerList(1);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      assertTrue(STR_BEFORE_RECONNECT, con.isConnected());

      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE);

      EasyMock.replay(this.connectionMock);

      try {
         con.close();
         con.close();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertFalse("after re-connect", con.isConnected());

      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testReConnect() throws MssException, SQLException {
      List<MssDbServer> list = prepareServerList(1);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      assertTrue(STR_BEFORE_RECONNECT, con.isConnected());

      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).andReturn(Boolean.TRUE);

      EasyMock.replay(this.connectionMock);

      try {
         con.close();
         con.connect();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertTrue("after re-connect", con.isConnected());

      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testConnectAndCloseAlreadyClosed() throws MssException, SQLException {
      List<MssDbServer> list = prepareServerList(1);

      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).times(3);

      this.connectionMock.close();
      EasyMock.expectLastCall();

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      EasyMock.replay(this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      try {
         con.close();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertFalse(STR_AFTER_CLOSE, con.isConnected());
      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testCloseMultiple() throws MssException, SQLException {
      List<MssDbServer> list = prepareServerList(2);

      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).times(3).andReturn(Boolean.TRUE);
      this.connectionMock.close();
      EasyMock.expectLastCall();

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssDbConnectionFactory.setConnection(list.get(1), this.connectionMock);

      EasyMock.replay(this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      try {
         con.close();
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      assertFalse(STR_AFTER_CLOSE, con.isConnected());

      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testCloseMultipleWithError() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(2);

      EasyMock.expect(this.connectionMock.isClosed()).andReturn(Boolean.FALSE).andThrow(new SQLException("Test"));

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssDbConnectionFactory.setConnection(list.get(1), this.connectionMock);

      EasyMock.replay(this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      try {
         con.close();
         fail();
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals(5005, e.getError().getErrorCode());
      }

      assertFalse(STR_AFTER_CLOSE, con.isConnected());

      EasyMock.verify(this.connectionMock);
   }


   @Test
   public void testExecuteQueryOk() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.FALSE);

      //      @SuppressWarnings("resource")
      //      Connection connectionMock = EasyMock.createMock(Connection.class);
      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      MssResult res = null;

      try {
         res = con.executeQuery(loggingId, "select * from table1");
         assertNotNull("Result not null", res);
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);
   }


   @Test
   public void testExecutePreparedStatementQueryOk() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(Integer.valueOf(metaMock.getColumnCount())).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(Boolean.valueOf(resultMock.next())).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(Boolean.valueOf(stmtMock.getMoreResults())).andReturn(Boolean.FALSE);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      MssResult res = null;

      try {
         res = con.executeQuery(loggingId, stmtMock);
         assertNotNull("Result not null", res);
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock, resultMock, metaMock);
   }


   @Test
   public void testExecuteUpdateOk() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column2 = 3 where column4 = 1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         int rows = con.executeUpdate(loggingId, "update table1 set column2 = 3 where column4 = 1");
         assertEquals("Rows afected", 1, rows);
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testExecutePreparedStatementUpdateOk() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         int rows = con.executeUpdate(loggingId, stmtMock);
         assertEquals("Rows afected", 1, rows);
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testExecuteMultipleUpdateOk() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(2);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1).times(2);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column2 = 3 where column4 = 1")))
            .andReturn(stmtMock)
            .times(2);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssDbConnectionFactory.setConnection(list.get(1), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         int rows = con.executeUpdate(loggingId, "update table1 set column2 = 3 where column4 = 1");
         assertEquals("Rows afected", 1, rows);
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testExecuteMultipleUpdateInconsistence() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(2);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1).andReturn(2);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.eq("update table1 set column2 = 3 where column4 = 1")))
            .andReturn(stmtMock)
            .times(2);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssDbConnectionFactory.setConnection(list.get(1), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         con.executeUpdate(loggingId, "update table1 set column2 = 3 where column4 = 1");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1004, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPreparedStatementExecuteMultipleUpdateInconsistence() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(2);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeUpdate()).andReturn(1).andReturn(2);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      MssDbConnectionFactory.setConnection(list.get(1), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         con.executeUpdate(loggingId, stmtMock);
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1004, e.getError().getErrorCode());
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testGetConnectionBusy() throws MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      DBConnectionForTest con = new DBConnectionForTest(null, list);
      for (MssSingleConnection c : con.getConnectionList()) {
         ((DBSingleConnectionForTest)c).setBusy(4);
      }
      try {
         con.executeQuery(loggingId, "select * from table1");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1003, e.getError().getErrorCode());
      }
   }


   @Test
   public void testGetConnectionBusy2Times() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      ResultSetMetaData metaMock = EasyMock.createMock(ResultSetMetaData.class);
      EasyMock.expect(metaMock.getColumnCount()).andReturn(Integer.valueOf(4)).times(6);
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(1))).andReturn("Column1");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(2))).andReturn("Col2");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(3))).andReturn("Column3");
      EasyMock.expect(metaMock.getColumnName(EasyMock.eq(4))).andReturn("Col4");

      ResultSet resultMock = EasyMock.createMock(ResultSet.class);
      EasyMock.expect(resultMock.getMetaData()).andReturn(metaMock);
      EasyMock.expect(resultMock.next()).andReturn(Boolean.TRUE).times(2).andReturn(Boolean.FALSE);
      EasyMock.expect(resultMock.getString(1)).andReturn("Cell0x0").andReturn("Cell1x0");
      EasyMock.expect(resultMock.getString(2)).andReturn("Cell0x1").andReturn("Cell1x1");
      EasyMock.expect(resultMock.getString(3)).andReturn("Cell0x2").andReturn("Cell1x2");
      EasyMock.expect(resultMock.getString(4)).andReturn("Cell0x3").andReturn("Cell1x3");

      resultMock.close();
      EasyMock.expectLastCall();

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.executeQuery()).andReturn(resultMock);
      EasyMock.expect(stmtMock.getMoreResults()).andReturn(Boolean.FALSE);

      EasyMock.expect(this.connectionMock.prepareStatement(EasyMock.eq("select * from table1"))).andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      EasyMock.replay(this.connectionMock, stmtMock, resultMock, metaMock);
      MssResult res = null;

      DBConnectionForTest con = new DBConnectionForTest(null, list);
      for (MssSingleConnection c : con.getConnectionList()) {
         ((DBSingleConnectionForTest)c).setBusy(2);
      }
      try {
         res = con.executeQuery(loggingId, "select * from table1");
         assertNotNull("Result not null", res);
      }
      catch (MssException e) {
         fail();
      }
   }


   @Test
   public void testGetConnectionBusyWithError() throws MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      DBConnectionForTest con = new DBConnectionForTest(null, list);
      con.throwSleepException = true;
      for (MssSingleConnection c : con.getConnectionList()) {
         ((DBSingleConnectionForTest)c).setBusy(4);
      }
      try {
         con.executeQuery(loggingId, "select * from table1");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 5007, e.getError().getErrorCode());
      }
   }


   @Test
   public void testGetConnectionUsedCount() throws MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);
      DBConnectionForTest con = new DBConnectionForTest(null, list);
      for (MssSingleConnection c : con.getConnectionList()) {
         ((DBSingleConnectionForTest)c).setUsedCount(Long.MAX_VALUE);
      }
      try {
         con.executeQuery(loggingId, "select * from table1");
         fail();
      }
      catch (MssException e) {
         assertEquals("ErrorCode", 1003, e.getError().getErrorCode());
      }
   }


   @Test
   public void testPrepareStatement1() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.anyString(), EasyMock.anyObject(int[].class)))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssPreparedStatement pstmt = (MssPreparedStatement)con
               .prepareStatement(loggingId, "update table1 set column2 = 3 where column4 = 1", new int[] {1});
         assertEquals("PreparedStatement", stmtMock, pstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareStatement2() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.anyString(), EasyMock.anyObject(String[].class)))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssPreparedStatement pstmt = (MssPreparedStatement)con
               .prepareStatement(loggingId, "update table1 set column2 = 3 where column4 = 1", new String[] {"ID"});
         assertEquals("PreparedStatement", stmtMock, pstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareStatement3() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.anyString()))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssPreparedStatement pstmt = (MssPreparedStatement)con
               .prepareStatement(loggingId, "update table1 set column2 = 3 where column4 = 1");
         assertEquals("PreparedStatement", stmtMock, pstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareStatement4() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.anyString(), EasyMock.anyInt()))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssPreparedStatement pstmt = (MssPreparedStatement)con
               .prepareStatement(loggingId, "update table1 set column2 = 3 where column4 = 1", 1);
         assertEquals("PreparedStatement", stmtMock, pstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareStatement5() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      PreparedStatement stmtMock = EasyMock.createMock(PreparedStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareStatement(EasyMock.anyString(), EasyMock.anyInt(), EasyMock.anyInt(), EasyMock.anyInt()))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssPreparedStatement pstmt = (MssPreparedStatement)con
               .prepareStatement(loggingId, "update table1 set column2 = 3 where column4 = 1", 1, 2, 3);
         assertEquals("PreparedStatement", stmtMock, pstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareCall1() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      CallableStatement stmtMock = EasyMock.createMock(CallableStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareCall(EasyMock.eq("update table1 set column2 = 3 where column4 = 1")))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssCallableStatement cstmt = (MssCallableStatement)con.prepareCall(loggingId, "update table1 set column2 = 3 where column4 = 1");
         assertEquals("PreparedStatement", stmtMock, cstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareCall2() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      CallableStatement stmtMock = EasyMock.createMock(CallableStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(this.connectionMock.prepareCall(EasyMock.eq("update table1 set column2 = 3 where column4 = 1"), EasyMock.eq(11), EasyMock.eq(14)))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssCallableStatement cstmt = (MssCallableStatement)con.prepareCall(loggingId, "update table1 set column2 = 3 where column4 = 1", 11, 14);
         assertEquals("PreparedStatement", stmtMock, cstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   @Test
   public void testPrepareCall3() throws SQLException, MssException {
      List<MssDbServer> list = prepareServerList(1);
      String loggingId = Tools.getId(new Throwable());

      CallableStatement stmtMock = EasyMock.createMock(CallableStatement.class);
      EasyMock.expect(stmtMock.getConnection()).andReturn(this.connectionMock);

      EasyMock
            .expect(
                  this.connectionMock
                        .prepareCall(
                              EasyMock.eq("update table1 set column2 = 3 where column4 = 1"),
                              EasyMock.eq(11),
                              EasyMock.eq(14),
                              EasyMock.eq(234)))
            .andReturn(stmtMock);

      MssDbConnectionFactory.setConnection(list.get(0), this.connectionMock);

      MssConnection con = new MssConnection(null, list);

      EasyMock.replay(this.connectionMock, stmtMock);

      try {
         MssCallableStatement cstmt = (MssCallableStatement)con
               .prepareCall(loggingId, "update table1 set column2 = 3 where column4 = 1", 11, 14, 234);
         assertEquals("PreparedStatement", stmtMock, cstmt.getStatement());
      }
      catch (MssException e) {
         e.printStackTrace();
         fail();
      }

      EasyMock.verify(this.connectionMock, stmtMock);
   }


   private List<MssDbServer> prepareServerList(int count) {
      ArrayList<MssDbServer> list = new ArrayList<>();

      for (int i = 1; i <= count; i++ ) {
         list.add(new MssDbServer("mysql", "127.0.0." + i, Integer.valueOf(1233 + i), "db", "user", "password", null));
      }

      return list;
   }


   protected class DBSingleConnectionForTest extends MssSingleConnection {

      private int timesBusy = 0;


      public DBSingleConnectionForTest(Logger l, MssDbServer s) throws MssException {
         super(l, s);
      }


      public DBSingleConnectionForTest(Logger l, MssDbServer s, Connection c) {
         super(l, s, c);
      }


      public void setUsedCount(long l) {
         this.usedCount = l;
      }


      public void setBusy(boolean b) {
         this.busy = b;
      }


      public void setBusy(int times) {
         this.timesBusy = times;
         this.busy = this.timesBusy > 0;
      }


      @Override
      public boolean isBusy() {
         this.timesBusy-- ;
         this.busy = this.timesBusy > 0;

         return super.isBusy();
      }
   }


   protected class DBConnectionForTest extends MssConnection {

      public boolean throwSleepException = false;


      public DBConnectionForTest(Logger loggerName, MssDbServer server) throws MssException {
         super(loggerName, server);
         init();
      }


      public DBConnectionForTest(Logger loggerName, MssDbServer[] servers) throws MssException {
         super(loggerName, servers);
         init();
      }


      public DBConnectionForTest(Logger loggerName, List<MssDbServer> list) throws MssException {
         super(loggerName, list);
         init();
      }


      private void init() throws MssException {
         this.connectionList = new ArrayList<>();
         for (MssDbServer s : this.serverlist) {
            this.connectionList.add(new DBSingleConnectionForTest(null, s));
         }
      }


      public List<MssSingleConnection> getConnectionList() {
         return this.connectionList;
      }


      @Override
      protected void sleep(long millies) throws InterruptedException {
         if (this.throwSleepException)
            throw new InterruptedException("Test");

         super.sleep(millies);
      }
   }
}
