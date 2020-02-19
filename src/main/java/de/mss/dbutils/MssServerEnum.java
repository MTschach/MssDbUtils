package de.mss.dbutils;


public enum MssServerEnum {
   //@formatter:off
   //           name         DriverClass                            connectionPrefix
   USERDEFINED ("",          "",                                    ""),
   MYSQL       ("mysql",     "com.mysql.jdbc.Driver",               "jdbc:mysql://"),
   SYBASETDS   ("sybasetds", "com.sybase.jdbc2.jdbc.SybDriver",     "jdbc:sybase:Tds:")
   ;
   //@formatter:on

   private String name             = null;
   private String driverClass      = null;
   private String connectionPrefix = null;


   MssServerEnum(String n, String d, String p) {
      this.name = n;
      this.driverClass = d;
      this.connectionPrefix = p;
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


   public static MssServerEnum getByName(String n) {
      for (MssServerEnum s : MssServerEnum.values()) {
         if (s.getName().equals(n))
            return s;
      }
      return USERDEFINED;
   }
}
