package de.mss.dbutils;

import org.junit.Test;

import de.mss.dbutils.enumeration.MssServer;
import junit.framework.TestCase;

public class MssDbServerTest extends TestCase {


   @Test
   public void test() {
      MssDbServer s = new MssDbServer("mysql", "localhost", Integer.valueOf(1234), "db1", "user", "password", null);
      s.setName("name1");

      assertEquals("Name", "name1", s.getName());
      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
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
            "Name : name1\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : 1234\nPath : null\nDBName : db1\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServer.MYSQL, "localhost", Integer.valueOf(1234), "db1", "user", "password", null);
      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
      assertEquals("ConnectionPrefix", "jdbc:mysql://", s.getConnectionPrefix());
      assertEquals("Host", "localhost", s.getHost());
      assertEquals("Port", Integer.valueOf(1234), s.getPort());
      assertEquals("DBName", "db1", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertNull("Options", s.getOptions());
      
      assertEquals("Server", MssServer.MYSQL, s.getServer());
   }


   @Test
   public void testUrl() {
      MssDbServer s = new MssDbServer("mysql", "jdbc://localhost:1234/db1", "user", "password", null);
      s.setName("name1");

      assertEquals("Name", "name1", s.getName());
      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
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
            "Name : name1\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : 1234\nPath : null\nDBName : db1\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServer.MYSQL, "jdbc://localhost:1234/db1", "user", "password", null);
      s.setName("name1");

      assertEquals("Name", "name1", s.getName());
      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
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
            "Name : name1\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : 1234\nPath : null\nDBName : db1\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServer.MYSQL, "localhost:1234/db1", "user", "password", null);
      s.setName("name1");

      assertEquals("Name", "name1", s.getName());
      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
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
            "Name : name1\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : 1234\nPath : null\nDBName : db1\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServer.MYSQL, "localhost/db1", "user", "password", null);
      s.setName("name1");

      assertEquals("Name", "name1", s.getName());
      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
      assertEquals("ConnectionPrefix", "jdbc:mysql://", s.getConnectionPrefix());
      assertEquals("Host", "localhost", s.getHost());
      assertNull("Port", s.getPort());
      assertEquals("DBName", "db1", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertNull("Options", s.getOptions());

      assertEquals("Connection", "localhost", s.getConnectionUrl());
      assertEquals(
            "toString",
            "Name : name1\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : null\nPath : null\nDBName : db1\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServer.MYSQL, "localhost", "user", "password", null);

      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
      assertEquals("ConnectionPrefix", "jdbc:mysql://", s.getConnectionPrefix());
      assertEquals("Host", "localhost", s.getHost());
      assertNull("Port", s.getPort());
      assertNull("DBName", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertNull("Options", s.getOptions());

      assertEquals("Connection", "localhost", s.getConnectionUrl());
      assertEquals(
            "toString",
            "Name : null\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : localhost\nPort : null\nPath : null\nDBName : null\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer(MssServer.MYSQL, null, "user", "password", null);

      assertEquals("Driver", "com.mysql.cj.jdbc.Driver", s.getDbDriver());
      assertEquals("ConnectionPrefix", "jdbc:mysql://", s.getConnectionPrefix());
      assertNull("Host", s.getHost());
      assertNull("Port", s.getPort());
      assertNull("DBName", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertNull("Options", s.getOptions());

      assertEquals("Connection", "null", s.getConnectionUrl());
      assertEquals(
            "toString",
            "Name : null\nServer : MYSQL\nDBDriver : com.mysql.cj.jdbc.Driver\nConnectionPrefix : jdbc:mysql://\nHost : null\nPort : null\nPath : null\nDBName : null\nUsername : user\nPassword : ****\nOptions : null\n",
            s.toString());
   }


   @Test
   public void testUserdefined() {
      MssDbServer s = new MssDbServer(MssServer.USERDEFINED, "/opt/data/base.sqlite3");
      s.setName("name3");

      assertNull("Driver", s.getDbDriver());
      assertNull("ConnectionPrefix", s.getConnectionPrefix());
      assertNull("Host", s.getHost());
      assertNull("Port", s.getPort());
      assertNull("DBName", s.getDbname());
      assertNull("User", s.getUsername());
      assertNull("Password", s.getPasswd());
      assertNull("Options", s.getOptions());
      assertEquals("Path", "/opt/data/base.sqlite3", s.getPath());

      assertEquals("Connection", "null", s.getConnectionUrl());
      assertEquals(
            "toString",
            "Name : name3\nServer : USERDEFINED\nDBDriver : null\nConnectionPrefix : null\nHost : null\nPort : null\nPath : /opt/data/base.sqlite3\nDBName : null\nUsername : null\nPassword : ****\nOptions : null\n",
            s.toString());

      s = new MssDbServer("", "/opt/data/base.sqlite3");
      s.setName("name4");

      assertNull("Driver", s.getDbDriver());
      assertNull("ConnectionPrefix", s.getConnectionPrefix());
      assertNull("Host", s.getHost());
      assertNull("Port", s.getPort());
      assertNull("DBName", s.getDbname());
      assertNull("User", s.getUsername());
      assertNull("Password", s.getPasswd());
      assertNull("Options", s.getOptions());
      assertEquals("Path", "/opt/data/base.sqlite3", s.getPath());

      assertEquals("Connection", "null", s.getConnectionUrl());
      assertEquals(
            "toString",
            "Name : name4\nServer : USERDEFINED\nDBDriver : null\nConnectionPrefix : null\nHost : null\nPort : null\nPath : /opt/data/base.sqlite3\nDBName : null\nUsername : null\nPassword : ****\nOptions : null\n",
            s.toString());
   }


   @Test
   public void testUserdefinedOdbc() {
      MssDbServer s = new MssDbServer("", "localhost", Integer.valueOf(0), "db1", "user", "password", "autocommit=true");
      s.setName("name5");
      s.setConnectionPrefix("odbc://");
      s.setDbDriver("java.odbc.OdbcDriver.class");

      assertEquals("Driver", "java.odbc.OdbcDriver.class", s.getDbDriver());
      assertEquals("ConnectionPrefix", "odbc://", s.getConnectionPrefix());
      assertEquals("Host", "localhost", s.getHost());
      assertEquals("Port", Integer.valueOf(0), s.getPort());
      assertEquals("DBName", "db1", s.getDbname());
      assertEquals("User", "user", s.getUsername());
      assertEquals("Password", "password", s.getPasswd());
      assertEquals("Options", "autocommit=true", s.getOptions());
      assertNull("Path", s.getPath());

      assertEquals("Connection", "localhost", s.getConnectionUrl());
      assertEquals(
            "toString",
            "Name : name5\nServer : USERDEFINED\nDBDriver : java.odbc.OdbcDriver.class\nConnectionPrefix : odbc://\nHost : localhost\nPort : 0\nPath : null\nDBName : db1\nUsername : user\nPassword : ****\nOptions : autocommit=true\n",
            s.toString());
   }
}
