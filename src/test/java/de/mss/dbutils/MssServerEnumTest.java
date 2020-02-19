package de.mss.dbutils;

import org.junit.Test;

import junit.framework.TestCase;

public class MssServerEnumTest extends TestCase {


   @Test
   public void testGetByName() {
      MssServerEnum e = MssServerEnum.getByName(null);
      check(MssServerEnum.USERDEFINED, e);

      e = MssServerEnum.getByName("");
      check(MssServerEnum.USERDEFINED, e);

      e = MssServerEnum.getByName("meins");
      check(MssServerEnum.USERDEFINED, e);

      e = MssServerEnum.getByName("mysql");
      check(MssServerEnum.MYSQL, e);

      e = MssServerEnum.getByName("sybasetds");
      check(MssServerEnum.SYBASETDS, e);
   }


   private void check(MssServerEnum exp, MssServerEnum cur) {
      assertEquals("Name", exp.getName(), cur.getName());
      assertEquals("DBDriver", exp.getDriverClass(), cur.getDriverClass());
      assertEquals("ConnectionPrefix", exp.getConnectionPrefix(), cur.getConnectionPrefix());
   }
}
