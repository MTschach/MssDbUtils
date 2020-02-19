package de.mss.dbutils;


public class MssDbServer {

   private String  host     = null;
   private Integer port     = null;
   private String  username = null;
   private String  passwd   = null;
   private String  options  = null;
   private String  dbDriver = null;
   private String  dbname   = null;
   private String  connectionPrefix = null;


   public MssDbServer(String s, String h, Integer p, String dbn, String user, String pwd, String o) {
      MssServerEnum server = MssServerEnum.getByName(s);
      this.dbDriver = server.getDriverClass();
      this.host = h;
      this.port = p;
      this.dbname = dbn;
      this.username = user;
      this.passwd = pwd;
      this.options = o;
      this.connectionPrefix = server.getConnectionPrefix();
   }


   public MssDbServer(MssServerEnum server, String h, Integer p, String dbn, String user, String pwd, String o) {
      this.dbDriver = server.getDriverClass();
      this.host = h;
      this.port = p;
      this.dbname = dbn;
      this.username = user;
      this.passwd = pwd;
      this.options = o;
      this.connectionPrefix = server.getConnectionPrefix();
   }


   public void setDbDriver(String d) {
      this.dbDriver = d;
   }


   public void setConnectionPrefix(String p) {
      this.connectionPrefix = p;
   }


   public void setHost(String h) {
      this.host = h;
   }


   public void setPort(Integer p) {
      this.port = p;
   }


   public void setDbname(String n) {
      this.dbname = n;
   }


   public void setUsername(String u) {
      this.username = u;
   }


   public void setPasswd(String p) {
      this.passwd = p;
   }


   public void setOptions(String o) {
      this.options = o;
   }


   public String getDbDriver() {
      return this.dbDriver;
   }


   public String getConnectionPrefix() {
      return this.connectionPrefix;
   }


   public String getHost() {
      return this.host;
   }


   public Integer getPort() {
      return this.port;
   }


   public String getDbname() {
      return this.dbname;
   }


   public String getUsername() {
      return this.username;
   }


   public String getPasswd() {
      return this.passwd;
   }


   public String getOptions() {
      return this.options;
   }


   public String getConnectionUrl() {
      return this.host + (this.port != null && this.port.intValue() > 0 ? ":" + this.port.toString() : "");
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("DBDriver : " + this.dbDriver + "\n");
      sb.append("ConnectionPrefix : " + this.connectionPrefix + "\n");
      sb.append("Host : " + this.host + "\n");
      sb.append("Port : " + this.port + "\n");
      sb.append("DBName : " + this.dbname + "\n");
      sb.append("Username : " + this.username + "\n");
      sb.append("Password : ****\n");
      sb.append("Options : " + this.options + "\n");

      return sb.toString();
   }

}
