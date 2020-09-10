package de.mss.dbutils;

import java.sql.Connection;

import org.easymock.EasyMock;

import de.mss.dbutils.enumeration.MssServer;
import junit.framework.TestCase;

public abstract class DbBaseTest extends TestCase {

   protected MssDbServer   server            = null;
   protected MssConnection mssConnectionMock = null;
   protected Connection    connectionMock    = null;



   @Override
   public void setUp() throws Exception {
      super.setUp();
      this.server = new MssDbServer(MssServer.MYSQL, "localhost", Integer.valueOf(3306), "testdb", "testuser", "testpassword", null);
      this.server.setName("testserver");
      this.connectionMock = EasyMock.createMock(Connection.class);
      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);

      this.mssConnectionMock = new MssConnection(null, this.server);
   }


   @Override
   public void tearDown() throws Exception {
      super.tearDown();
      MssDbConnectionFactory.dropConnection(this.server);
   }
}
