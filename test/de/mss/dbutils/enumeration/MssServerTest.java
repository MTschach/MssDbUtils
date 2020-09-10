package de.mss.dbutils.enumeration;

import org.junit.Test;

import junit.framework.TestCase;

public class MssServerTest extends TestCase {
   @Test
   public void test() {
      check(MssServer.getByName("mysql"), MssServer.MYSQL);
      check(MssServer.getByName("sybasetds"), MssServer.SYBASETDS);
      check(MssServer.getByName("sqlite"), MssServer.SQLITE);
      check(MssServer.getByName(""), MssServer.USERDEFINED);
   }


   private void check(MssServer is, MssServer expected) {
      assertEquals("Name", expected.getName(), is.getName());
      assertEquals("DbDriver", expected.getDriverClass(), is.getDriverClass());
      assertEquals("ConnectionPrefix", expected.getConnectionPrefix(), is.getConnectionPrefix());
      assertEquals("is file based", expected.isFileBased(), is.isFileBased());
   }

}
