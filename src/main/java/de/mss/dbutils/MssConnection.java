package de.mss.dbutils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mss.utils.Tools;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class MssConnection {

   protected List<MssDbServer>         serverlist     = null;
   protected List<MssSingleConnection> connectionList = null;

   private static Logger               rootLogger     = LogManager.getRootLogger();

   private Logger                      logger         = rootLogger;


   public Logger getLogger() {
      if (this.logger == null)
         this.logger = rootLogger;

      return this.logger;
   }


   public void setLogger(Logger l) {
      this.logger = l;
   }


   public MssConnection(Logger l, MssDbServer server) throws MssException {
      this.serverlist = new ArrayList<>();
      this.serverlist.add(server);
      setLogger(l);
      init();
   }


   public MssConnection(Logger l, MssDbServer[] servers) throws MssException {
      this.serverlist = new ArrayList<>();
      for (MssDbServer s : servers)
         this.serverlist.add(s);
      setLogger(l);
      init();
   }


   public MssConnection(Logger l, List<MssDbServer> list) throws MssException {
      this.serverlist = list;
      setLogger(l);
      init();
   }


   private void init() throws MssException {
      this.connectionList = new ArrayList<>();
      
      for (MssDbServer s : this.serverlist) {
         this.connectionList.add(new MssSingleConnection(getLogger(), s));
      }
   }


   public void close() throws MssException {
      if (!isConnected())
         return;

      for (MssSingleConnection c : this.connectionList) {
         try {
            if (!c.isClosed())
               c.close();
         }
         catch (SQLException e) {
            throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_CLOSING_CONNECTION, e, "error while closing connection " + c);
         }
      }
   }


   public void connect() throws MssException {
      if (isConnected())
         return;

      for (MssSingleConnection c : this.connectionList) {
         c.connect();
      }
   }


   public MssResult executeQuery(String loggingId, String query) throws MssException {
      connect();
      return getConnectionFromPool(loggingId).executeQuery(loggingId, query);
   }


   public MssResult executeQuery(String loggingId, PreparedStatement prepStmt) throws MssException {
      connect();
      return getConnectionFromPool(loggingId).executeQuery(loggingId, prepStmt);
   }


   public int executeUpdate(String loggingId, String query) throws MssException {
      connect();
      
      int ret = -1;
      
      for (MssSingleConnection c : this.connectionList) {
         int r = c.executeUpdate(loggingId, query);
         if (ret == -1)
            ret = r;

         if (r != ret)
            throw new MssException(
                  ErrorCodes.ERROR_DB_POSSIBLE_DATA_INCONSISTENCE,
                  "Servers did return different results. possible data inconsistence. CHECK OUT");
      }
      
      return ret;
   }



   public int executeUpdate(String loggingId, PreparedStatement prepStmt) throws MssException {
      connect();

      int ret = -1;

      for (MssSingleConnection c : this.connectionList) {
         int r = c.executeUpdate(loggingId, prepStmt);
         if (ret == -1)
            ret = r;

         if (r != ret)
            throw new MssException(
                  ErrorCodes.ERROR_DB_POSSIBLE_DATA_INCONSISTENCE,
                  "Servers did return different results. possible data inconsistence. CHECK OUT");
      }

      return ret;
   }


   public boolean isConnected() {
      for (MssSingleConnection c : this.connectionList) {
         if (!c.isConnected())
            return false;
      }
      return true;
   }


   public MssSingleConnection getConnectionFromPool(String loggingId) throws MssException {
      return getConnectionFromPool(loggingId, 2);
   }


   private MssSingleConnection getConnectionFromPool(String loggingId, int retry) throws MssException {
      if (retry < 0)
         throw new MssException(ErrorCodes.ERROR_DB_NO_AVAILABLE_CONNECTION, "No Connection found");

      MssSingleConnection ret = null;
      long usedCount = Long.MAX_VALUE;

      for (MssSingleConnection c : this.connectionList) {
         if (!c.isBusy() && c.getUsedCount() < usedCount) {
            ret = c;
            usedCount = c.getUsedCount();
         }
      }

      if (ret == null) {
         getLogger().debug(Tools.formatLoggingId(loggingId) + "no available connection found, retry");
         try {
            sleep(25);
            return getConnectionFromPool(loggingId, retry - 1);
         }
         catch (InterruptedException e) {
            getLogger().error(e);
            throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_WAITING, e, "Error while waiting for a connection to get ready");
         }
      }

      return ret;
   }


   protected void sleep(long millies) throws InterruptedException {
      Thread.sleep(millies);
   }


   public PreparedStatement prepareStatement(String loggingId, String sql) throws SQLException, MssException {
      return getConnectionFromPool(loggingId).prepareStatement(sql);
   }


   public PreparedStatement prepareStatement(String loggingId, String sql, int autoGeneratedKeys) throws SQLException, MssException {
      return getConnectionFromPool(loggingId).prepareStatement(sql, autoGeneratedKeys);
   }


   public PreparedStatement prepareStatement(String loggingId, String sql, int resultSetType, int resultSetConcurrency)
         throws SQLException,
         MssException {
      return getConnectionFromPool(loggingId).prepareStatement(sql, resultSetType, resultSetConcurrency);
   }


   public PreparedStatement prepareStatement(String loggingId, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
         throws SQLException,
         MssException {
      return getConnectionFromPool(loggingId).prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
   }


   public PreparedStatement prepareStatement(String loggingId, String sql, int[] columnIndexes) throws SQLException, MssException {
      return getConnectionFromPool(loggingId).prepareStatement(sql, columnIndexes);
   }


   public PreparedStatement prepareStatement(String loggingId, String sql, String[] columnNames) throws SQLException, MssException {
      return getConnectionFromPool(loggingId).prepareStatement(sql, columnNames);
   }


   public CallableStatement prepareCall(String loggingId, String sql) throws SQLException, MssException {
      return getConnectionFromPool(loggingId).prepareCall(sql);
   }


   public CallableStatement prepareCall(String loggingId, String sql, int resultSetType, int resultSetConcurrency)
         throws SQLException,
         MssException {
      return getConnectionFromPool(loggingId).prepareCall(sql, resultSetType, resultSetConcurrency);
   }


   public CallableStatement prepareCall(String loggingId, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
         throws SQLException,
         MssException {
      return getConnectionFromPool(loggingId).prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
   }


}
