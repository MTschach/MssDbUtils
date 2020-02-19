package de.mss.dbutils;

import org.junit.Test;

import junit.framework.TestCase;

public class MssDbServerTest extends TestCase {


   @Test
   public void test() {
      MssDbServer s = new MssDbServer("mysql", "localhost", Integer.valueOf(1234), "db1", "user", "password", null);

      assertEquals("Driver", "com.mysql.jdbc.Driver", s.getDbDriver());
      assertEquals("ConnectionPrefix", "jdbc:mysql://", s.getConnectionPrefix());
      assertEquals("Host", "localhost", s.getHost());
      assertEquals("Port", Integer.valueOf(1234), s.getPort());
      assertEquals("DBName", "db1", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertNull("Options", s.getOptions());

      assertEquals("Connection", "localhost:1234", s.getConnectionUrl());
      assertEquals(
            "toString",
            "DBDriver : com.mysql.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : 1234\nDBName : db1\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServerEnum.MYSQL, "localhost", Integer.valueOf(1234), "db1", "user", "password", null);
      assertEquals("Driver", "com.mysql.jdbc.Driver", s.getDbDriver());
      assertEquals("ConnectionPrefix", "jdbc:mysql://", s.getConnectionPrefix());
      assertEquals("Host", "localhost", s.getHost());
      assertEquals("Port", Integer.valueOf(1234), s.getPort());
      assertEquals("DBName", "db1", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertNull("Options", s.getOptions());

      s.setDbDriver("odbc");
      s.setHost("127.0.0.1");
      s.setPort(Integer.valueOf(4321));
      s.setDbname("db2");
      s.setUsername("user2");
      s.setPasswd("secret");
      s.setOptions("option=1");
      s.setConnectionPrefix("prefix");

      assertEquals("Driver", "odbc", s.getDbDriver());
      assertEquals("Host", "127.0.0.1", s.getHost());
      assertEquals("Port", Integer.valueOf(4321), s.getPort());
      assertEquals("DBName", "db2", s.getDbname());
      assertEquals("User", "user2", s.getUsername());
      assertEquals("Password", "secret", s.getPasswd());
      assertEquals("Options", "option=1", s.getOptions());
      assertEquals("ConnectionPRefix", "prefix", s.getConnectionPrefix());


      s.setPort(null);
      assertEquals("Connection", "127.0.0.1", s.getConnectionUrl());
      s.setPort(Integer.valueOf(0));
      assertEquals("Connection", "127.0.0.1", s.getConnectionUrl());
   }

}
