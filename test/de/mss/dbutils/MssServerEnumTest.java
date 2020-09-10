package de.mss.dbutils;

import org.junit.Test;

import de.mss.dbutils.enumeration.MssServer;
import junit.framework.TestCase;

public class MssServerEnumTest extends TestCase {


   @Test
   public void testGetByName() {
      MssServer e = MssServer.getByName(null);
      check(MssServer.USERDEFINED, e);

      e = MssServer.getByName("");
      check(MssServer.USERDEFINED, e);

      e = MssServer.getByName("meins");
      check(MssServer.USERDEFINED, e);

      e = MssServer.getByName("mysql");
      check(MssServer.MYSQL, e);

      e = MssServer.getByName("sybasetds");
      check(MssServer.SYBASETDS, e);
   }


   private void check(MssServer exp, MssServer cur) {
      assertEquals("Name", exp.getName(), cur.getName());
      assertEquals("DBDriver", exp.getDriverClass(), cur.getDriverClass());
      assertEquals("ConnectionPrefix", exp.getConnectionPrefix(), cur.getConnectionPrefix());
   }
}
