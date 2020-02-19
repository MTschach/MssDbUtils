package de.mss.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import de.mss.utils.exception.MssException;

public class MssDbConnectionFactory {

   private static Map<MssDbServer, Connection> connectionMap = new HashMap<>();


   public static void setConnection(MssDbServer server, Connection con) {
      MssDbConnectionFactory.connectionMap.put(server, con);
   }


   public static Connection getConnection(MssDbServer server) throws MssException {
      if (MssDbConnectionFactory.connectionMap.containsKey(server))
         return MssDbConnectionFactory.connectionMap.get(server);

      try {
         Class.forName(server.getDbDriver()).newInstance();
      }
      catch (Exception e) {
         throw new MssException(e, "Driver '" + server.getDbDriver() + "' not found");
      }

      try {
         MssDbConnectionFactory.connectionMap
               .put(
                     server,
                     DriverManager
               .getConnection(
                     server.getConnectionPrefix() + server.getConnectionUrl() + "/" + server.getDbname(),
                     server.getUsername(),
                                 server.getPasswd()));
         return MssDbConnectionFactory.connectionMap.get(server);
      }
      catch (SQLException e) {
         throw new MssException(e, "Could not get a connection from the DriverManager");
      }
   }


   public static void dropConnection(MssDbServer server) {
      if (MssDbConnectionFactory.connectionMap.containsKey(server))
         MssDbConnectionFactory.connectionMap.remove(server);
   }
}
