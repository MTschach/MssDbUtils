package de.mss.dbutils;

import de.mss.dbutils.enumeration.MssServer;

public class MssDbServer {

   private String    name             = null;
   private String    dbDriver         = null;
   private String    connectionPrefix = null;
   private String    host             = null;
   private Integer   port             = null;
   private String    username         = null;
   private String    passwd           = null;
   private String    options          = null;
   private String    dbname           = null;
   private String    path             = null;
   private MssServer server           = MssServer.USERDEFINED;


   public MssDbServer(String s, String h, Integer p, String dbn, String user, String pwd, String o) {
      setServer(s);
      setHost(h);
      setPort(p);
      setDbname(dbn);
      setUsername(user);
      setPasswd(pwd);
      setOptions(o);
   }


   public MssDbServer(MssServer s, String h, Integer p, String dbn, String user, String pwd, String o) {
      setServer(s);
      setHost(h);
      setPort(p);
      setDbname(dbn);
      setUsername(user);
      setPasswd(pwd);
      setOptions(o);
   }


   public MssDbServer(String s, String u, String user, String pwd, String o) {
      setServer(s);
      parseUrl(u);
      setUsername(user);
      setPasswd(pwd);
      setOptions(o);
   }


   public MssDbServer(String s, String p) {
      setServer(s);
      setPath(p);
   }


   public MssDbServer(MssServer s, String p) {
      setServer(s);
      setPath(p);
   }


   public MssDbServer(MssServer s, String u, String user, String pwd, String o) {
      setServer(s);
      parseUrl(u);
      setUsername(user);
      setPasswd(pwd);
      setOptions(o);
   }


   private void parseUrl(String url) {
      if (url == null)
         return;

      String[] u = url.split("://");
      String urlPart = u[0];
      if (u.length > 1)
         urlPart = u[1];

      u = urlPart.split("/");
      if (u.length >= 2)
         setDbname(u[1]);

      u = u[0].split(":");
      this.host = u[0];
      if (u.length > 1)
         setPort(Integer.valueOf(u[1]));
   }


   public void setName(String v) {
      this.name = v;
   }


   public void setDbDriver(String d) {
      this.dbDriver = d;
   }


   public void setConnectionPrefix(String c) {
      this.connectionPrefix = c;
   }


   public void setHost(String v) {
      this.host = v;
   }


   public void setPort(Integer v) {
      this.port = v;
   }


   public void setDbname(String n) {
      this.dbname = n;
   }


   public void setUsername(String v) {
      this.username = v;
   }


   public void setPasswd(String v) {
      this.passwd = v;
   }


   public void setOptions(String o) {
      this.options = o;
   }


   public void setPath(String p) {
      this.path = p;
   }


   public void setServer(String s) {
      this.server = MssServer.getByName(s);
   }


   public void setServer(MssServer s) {
      this.server = s;
   }


   public String getName() {
      return this.name;
   }


   public String getDbDriver() {
      return this.server == MssServer.USERDEFINED ? this.dbDriver : this.server.getDriverClass();
   }


   public String getConnectionPrefix() {
      return this.server == MssServer.USERDEFINED ? this.connectionPrefix : this.server.getConnectionPrefix();
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


   public MssServer getServer() {
      return this.server;
   }


   public String getConnectionUrl() {
      return this.host + (this.port != null && this.port.intValue() > 0 ? ":" + this.port.toString() : "");
   }


   public String getPath() {
      return this.path;
   }


   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("Name : " + this.name + "\n");
      sb.append("Server : " + this.server + "\n");
      sb.append("DBDriver : " + getDbDriver() + "\n");
      sb.append("ConnectionPrefix : " + getConnectionPrefix() + "\n");
      sb.append("Host : " + this.host + "\n");
      sb.append("Port : " + this.port + "\n");
      sb.append("Path : " + this.path + "\n");
      sb.append("DBName : " + this.dbname + "\n");
      sb.append("Username : " + this.username + "\n");
      sb.append("Password : ****\n");
      sb.append("Options : " + this.options + "\n");

      return sb.toString();
   }

}
