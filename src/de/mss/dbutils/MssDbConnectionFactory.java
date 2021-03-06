package de.mss.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import de.mss.dbutils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class MssDbConnectionFactory {

   private static Map<String, Connection> connectionMap = new HashMap<>();


   public static void setConnection(MssDbServer server, Connection con) {
      MssDbConnectionFactory.connectionMap.put(server.getName(), con);
   }


   public static Connection getConnection(MssDbServer server) throws MssException {
      if (MssDbConnectionFactory.connectionMap.containsKey(server.getName()))
         return MssDbConnectionFactory.connectionMap.get(server.getName());

      try {
         Class.forName(server.getDbDriver()).getDeclaredConstructor().newInstance();
      }
      catch (Exception e) {
         throw new MssException(ErrorCodes.ERROR_INIT_DB_DRIVER, e, "Driver '" + server.getDbDriver() + "' not found");
      }

      try {
         if (server.getServer().isFileBased()) {
            MssDbConnectionFactory.connectionMap.put(server.getName(), DriverManager.getConnection(server.getConnectionPrefix() + server.getPath()));
         } else {
         MssDbConnectionFactory.connectionMap
               .put(
                        server.getName(),
                     DriverManager
               .getConnection(
                     server.getConnectionPrefix() + server.getConnectionUrl() + "/" + server.getDbname(),
                     server.getUsername(),
                                 server.getPasswd()));
         }
         return MssDbConnectionFactory.connectionMap.get(server.getName());
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_GETTING_CONNECTION, e, "Could not get a connection from the DriverManager");
      }
   }


   public static void dropConnection(MssDbServer server) {
      if (MssDbConnectionFactory.connectionMap.containsKey(server.getName()))
         MssDbConnectionFactory.connectionMap.remove(server.getName());
   }
}
