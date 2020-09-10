package de.mss.dbutils.enumeration;


public enum MssServer {
   //@formatter:off
   //           name         DriverClass                            connectionPrefix
   USERDEFINED (""            , ""                                      , ""                    , false),
   MYSQL       ("mysql"       , "com.mysql.cj.jdbc.Driver"              , "jdbc:mysql://"       , false),
   SYBASETDS   ("sybasetds"   , "com.sybase.jdbc2.jdbc.SybDriver"       , "jdbc:sybase:Tds://"  , false),
   SQLITE      ("sqlite"      , "org.sqlite.JDBC"                       , "jdbc:sqlite:"      , true)
   ;
   //@formatter:on

   private String name             = null;
   private String driverClass      = null;
   private String connectionPrefix = null;
   private boolean isFileBased      = false;


   MssServer(String n, String d, String p, boolean i) {
      this.name = n;
      this.driverClass = d;
      this.connectionPrefix = p;
      this.isFileBased = i;
   }


   public String getName() {
      return this.name;
   }


   public String getDriverClass() {
      return this.driverClass;
   }


   public String getConnectionPrefix() {
      return this.connectionPrefix;
   }


   public boolean isFileBased() {
      return this.isFileBased;
   }


   public static MssServer getByName(String n) {
      for (MssServer s : MssServer.values()) {
         if (s.getName().equals(n))
            return s;
      }
      return USERDEFINED;
   }
}
