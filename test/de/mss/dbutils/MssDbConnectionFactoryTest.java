package de.mss.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import de.mss.dbutils.enumeration.MssServer;
import de.mss.dbutils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class MssDbConnectionFactoryTest extends DbBaseTest {

   @Test
   public void testGetConnection() throws MssException {
      @SuppressWarnings("resource")
      Connection con = MssDbConnectionFactory.getConnection(this.server);

      assertNotNull(con);
      assertEquals(this.connectionMock, con);
   }


   //   @Test
   //   public void testGetNewConnection() throws MssException, SQLException {
   //      this.server.setName("testname4");
   //
   //      PowerMock.mockStatic(DriverManager.class);
   //
   //      EasyMock
   //            .expect(
   //                  DriverManager
   //                        .getConnection(
   //                              EasyMock.eq(this.server.getConnectionPrefix() + this.server.getConnectionUrl() + "/" + this.server.getDbname()),
   //                              EasyMock.eq(this.server.getUsername()),
   //                              EasyMock.eq(this.server.getPasswd())))
   //            .andReturn(this.connectionMock);
   //
   //      PowerMock.replay(DriverManager.class);
   //
   //      @SuppressWarnings("resource")
   //      Connection con = MssDbConnectionFactory.getConnection(this.server);
   //
   //      assertNotNull(con);
   //      assertEquals(this.connectionMock, con);
   //   }
   //
   //
   @SuppressWarnings("resource")
   @Test
   public void testSqlite() throws MssException {
      this.server = new MssDbServer(MssServer.SQLITE, "test.sqlite3");
      this.server.setName("testname5");

      Connection con = MssDbConnectionFactory.getConnection(this.server);

      assertNotNull(con);
   }


   @Test
   public void testInvalidDriverClass() {
      this.server = new MssDbServer(MssServer.USERDEFINED, "localhost", Integer.valueOf(3306), "testdb", "testuser", "testpassword", null);
      this.server.setDbDriver("com.mysql.invalid.Driver");
      this.server.setName("testname2");

      try {
         MssDbConnectionFactory.getConnection(this.server);
         fail();
      }
      catch (MssException e) {
         assertEquals("Error code", ErrorCodes.ERROR_INIT_DB_DRIVER.getErrorCode(), e.getError().getErrorCode());
      }
   }


   @Test
   public void testErrorOpenConnection() {
      this.server.setHost("1.2.3.4");
      this.server.setName("testname3");

      try {
         DriverManager.setLoginTimeout(1);
         MssDbConnectionFactory.getConnection(this.server);
         fail();
      }
      catch (MssException e) {
         assertEquals("Error code", ErrorCodes.ERROR_GETTING_CONNECTION.getErrorCode(), e.getError().getErrorCode());
      }
   }
}
