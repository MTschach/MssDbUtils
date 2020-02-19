package de.mss.dbutils;

import java.sql.Connection;

import org.junit.Test;

import de.mss.utils.exception.MssException;

public class MssDbConnectionFactoryTest extends DbBaseTest {

   @Override
   public void setUp() throws MssException {
      super.setUp();
   }

   @Test
   public void testGetConnection() throws MssException {
      @SuppressWarnings("resource")
      Connection con = MssDbConnectionFactory.getConnection(this.server);

      assertNotNull(con);
      assertEquals(this.connectionMock, con);
   }
}
