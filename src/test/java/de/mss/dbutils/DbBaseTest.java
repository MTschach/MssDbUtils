package de.mss.dbutils;

import java.sql.Connection;

import org.easymock.EasyMock;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public abstract class DbBaseTest extends TestCase {

   protected MssDbServer   server            = null;
   protected MssConnection mssConnectionMock = null;
   protected Connection    connectionMock    = null;



   @Override
   public void setUp() throws MssException {
      this.server = new MssDbServer(MssServerEnum.MYSQL, "localhost", Integer.valueOf(3306), "testdb", "testuser", "testpassword", null);
      this.connectionMock = EasyMock.createMock(Connection.class);
      MssDbConnectionFactory.setConnection(this.server, this.connectionMock);

      this.mssConnectionMock = new MssConnection(null, this.server);
   }
}
