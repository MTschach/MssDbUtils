package de.mss.dbutils;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mss.utils.StopWatch;
import de.mss.utils.Tools;
import de.mss.utils.exception.ErrorCodes;
import de.mss.utils.exception.MssException;

public class MssSingleConnection implements Connection {

   protected MssDbServer   server       = null;
   protected Connection dbConnection = null;
   protected boolean    busy         = false;
   protected long       usedCount    = 0;


   private String    loggingId = null;
   private Throwable throwable = null;


   private static Logger rootLogger = LogManager.getRootLogger();

   private Logger        logger     = rootLogger;


   protected Logger getLogger() {
      if (this.logger == null)
         this.logger = rootLogger;

      return this.logger;
   }


   public void setLogger(Logger l) {
      this.logger = l;
   }


   public MssSingleConnection(Logger l, MssDbServer s) throws MssException {
      setLogger(l);
      this.server = s;
      connect();
   }


   public MssSingleConnection(Logger l, MssDbServer s, Connection c) {
      setLogger(l);
      this.server = s;
      this.dbConnection = c;
   }


   @Override
   public void close() throws SQLException {
      try {
         if (isClosed())
            return;

         if (!this.dbConnection.isClosed())
            this.dbConnection.close();
      }
      catch (SQLException e) {
         throw e;
      }
      finally {
         this.dbConnection = null;
         this.usedCount = 0;
      }
   }


   public void connect() throws MssException {
      if (this.dbConnection == null)
         this.dbConnection = MssDbConnectionFactory.getConnection(this.server);
   }


   public boolean isConnected() {
      return (this.dbConnection != null);
   }


   public Connection getDbConnection() {
      return this.dbConnection;
   }


   @Override
   public boolean isClosed() throws SQLException {
      return this.dbConnection == null || this.dbConnection.isClosed();
   }


   public MssResult executeQuery(String logId, String query) throws MssException {
      connect();
      try {
         return executeQuery(logId, this.dbConnection.prepareStatement(query));
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_DB_EXECUTE_QUERY_FAILURE, e);
      }
   }


   public MssResult executeQuery(String logId, PreparedStatement stmt) throws MssException {
      if (stmt == null)
         throw new MssException(ErrorCodes.ERROR_INVALID_PARAM, "The Statement is null");

      MssResult result = new MssResult();

      int resultSetNumber = 1;
      this.busy = true;
      this.usedCount++ ;
      getLogger().debug(Tools.formatLoggingId(logId) + "Executing Query " + stmt);
      StopWatch s = new StopWatch();
      try (ResultSet res = stmt.executeQuery()) {
         getLogger().debug(Tools.formatLoggingId(logId) + "Duration for executing query [" + s.getDuration() + "ms]");
         s.reset();
         if (res != null)
            result.addResult(getResultFromDb(resultSetNumber, res));

         result = handleMoreResults(result, resultSetNumber + 1, stmt);

         getLogger().debug(Tools.formatLoggingId(logId) + "Result " + result + " [" + s.getDuration() + "ms]");
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_DB_EXECUTE_QUERY_FAILURE, e, "Failure while executing query");
      }
      finally {
         this.busy = false;
      }
      return result;
   }


   public int executeUpdate(String logId, String query) throws MssException {
      try {
         return executeUpdate(logId, this.dbConnection.prepareStatement(query));
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_DB_EXECUTE_UPDATE_FAILURE, e);
      }
   }


   public int executeUpdate(String logId, PreparedStatement stmt) throws MssException {
      if (stmt == null)
         throw new MssException(ErrorCodes.ERROR_INVALID_PARAM, "The Statement is null");

      getLogger().debug(Tools.formatLoggingId(logId) + "Executing Update " + stmt);
      int rows = 0;
      this.usedCount++ ;
      StopWatch s = new StopWatch();
      try {
         this.busy = true;
         rows = stmt.executeUpdate();
         getLogger().debug(Tools.formatLoggingId(logId) + "Duration for executing update [" + s.getDuration() + "ms]");
         getLogger().debug(Tools.formatLoggingId(logId) + rows + " affected");
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_DB_EXECUTE_UPDATE_FAILURE, e, "Failure while executing update");
      }
      finally {
         this.busy = false;
      }
      return rows;
   }


   private MssResult handleMoreResults(MssResult result, int resultSetNumber, PreparedStatement stmt) throws MssException {
      try {
         if (!stmt.getMoreResults())
            return result;
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_DB_RESUTSET_FAILURE, e);
      }

      try (ResultSet res = stmt.getResultSet()) {
         result.addResult(getResultFromDb(resultSetNumber, res));

         return handleMoreResults(result, resultSetNumber + 1, stmt);
      }
      catch (SQLException e) {
         throw new MssException(ErrorCodes.ERROR_DB_RESUTSET_FAILURE, e);
      }
   }


   private MssSingleResult getResultFromDb(int resultSetNumber, ResultSet res) throws SQLException {
      if (res == null)
         return null;

      ResultSetMetaData meta = res.getMetaData();

      MssSingleResult result = new MssSingleResult(resultSetNumber, meta.getColumnCount());
      for (int i = 0; i < meta.getColumnCount(); i++ )
         result.setColumnName(meta.getColumnName(i + 1), i);

      while (res.next()) {
         int row = result.addRow() - 1;
         for (int i = 0; i < result.getColumnCount(); i++ ) {
            result.setValue(i, row, res.getString(i + 1));
         }
      }

      return result;
   }


   public boolean isBusy() {
      return this.busy;
   }


   public long getUsedCount() {
      return this.usedCount;
   }


   @Override
   public <T> T unwrap(Class<T> iface) throws SQLException {
      return this.dbConnection.unwrap(iface);
   }


   @Override
   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return this.dbConnection.isWrapperFor(iface);
   }


   @Override
   public Statement createStatement() throws SQLException {
      return new MssStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.createStatement(),
            this.throwable);
   }


   @Override
   public PreparedStatement prepareStatement(String sql) throws SQLException {
      return new MssPreparedStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareStatement(sql),
            sql,
            this.throwable);
   }


   @Override
   public CallableStatement prepareCall(String sql) throws SQLException {
      return new MssCallableStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareCall(sql),
            sql,
            this.throwable);
   }


   @Override
   public String nativeSQL(String sql) throws SQLException {
      return this.dbConnection.nativeSQL(sql);
   }


   @Override
   public void setAutoCommit(boolean autoCommit) throws SQLException {
      this.dbConnection.setAutoCommit(autoCommit);
   }


   @Override
   public boolean getAutoCommit() throws SQLException {
      return this.dbConnection.getAutoCommit();
   }


   @Override
   public void commit() throws SQLException {
      this.dbConnection.commit();
   }


   @Override
   public void rollback() throws SQLException {
      this.dbConnection.rollback();
   }


   @Override
   public DatabaseMetaData getMetaData() throws SQLException {
      return this.dbConnection.getMetaData();
   }


   @Override
   public void setReadOnly(boolean readOnly) throws SQLException {
      this.dbConnection.setReadOnly(readOnly);
   }


   @Override
   public boolean isReadOnly() throws SQLException {
      return this.dbConnection.isReadOnly();
   }


   @Override
   public void setCatalog(String catalog) throws SQLException {
      this.dbConnection.setCatalog(catalog);
   }


   @Override
   public String getCatalog() throws SQLException {
      return this.dbConnection.getCatalog();
   }


   @Override
   public void setTransactionIsolation(int level) throws SQLException {
      this.dbConnection.setTransactionIsolation(level);
   }


   @Override
   public int getTransactionIsolation() throws SQLException {
      return this.dbConnection.getTransactionIsolation();
   }


   @Override
   public SQLWarning getWarnings() throws SQLException {
      return this.dbConnection.getWarnings();
   }


   @Override
   public void clearWarnings() throws SQLException {
      this.dbConnection.clearWarnings();
   }


   @Override
   public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
      return new MssStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.createStatement(resultSetType, resultSetConcurrency),
            this.throwable);
   }


   @Override
   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      return new MssPreparedStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareStatement(sql, resultSetType, resultSetConcurrency),
            sql,
            this.throwable);
   }


   @Override
   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
      return new MssCallableStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareCall(sql, resultSetType, resultSetConcurrency),
            sql,
            this.throwable);
   }


   @Override
   public Map<String, Class<?>> getTypeMap() throws SQLException {
      return this.dbConnection.getTypeMap();
   }


   @Override
   public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
      this.dbConnection.setTypeMap(map);
   }


   @Override
   public void setHoldability(int holdability) throws SQLException {
      this.dbConnection.setHoldability(holdability);
   }


   @Override
   public int getHoldability() throws SQLException {
      return this.dbConnection.getHoldability();
   }


   @Override
   public Savepoint setSavepoint() throws SQLException {
      return this.dbConnection.setSavepoint();
   }


   @Override
   public Savepoint setSavepoint(String name) throws SQLException {
      return this.dbConnection.setSavepoint(name);
   }


   @Override
   public void rollback(Savepoint savepoint) throws SQLException {
      this.dbConnection.rollback(savepoint);
   }


   @Override
   public void releaseSavepoint(Savepoint savepoint) throws SQLException {
      this.dbConnection.releaseSavepoint(savepoint);
   }


   @Override
   public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return new MssStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.createStatement(resultSetType, resultSetConcurrency),
            this.throwable);
   }


   @Override
   public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return new MssPreparedStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
            sql,
            this.throwable);
   }


   @Override
   public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
      return new MssCallableStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
            sql,
            this.throwable);
   }


   @Override
   public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
      return new MssPreparedStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareStatement(sql, autoGeneratedKeys),
            sql,
            this.throwable);
   }


   @Override
   public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
      return new MssPreparedStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareStatement(sql, columnIndexes),
            sql,
            this.throwable);
   }


   @Override
   public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
      return new MssPreparedStatement(
            this.loggingId,
            getLogger(),
            this.dbConnection.prepareStatement(sql, columnNames),
            sql,
            this.throwable);
   }


   @Override
   public Clob createClob() throws SQLException {
      return this.dbConnection.createClob();
   }


   @Override
   public Blob createBlob() throws SQLException {
      return this.dbConnection.createBlob();
   }


   @Override
   public NClob createNClob() throws SQLException {
      return this.dbConnection.createNClob();
   }


   @Override
   public SQLXML createSQLXML() throws SQLException {
      return this.dbConnection.createSQLXML();
   }


   @Override
   public boolean isValid(int timeout) throws SQLException {
      return this.dbConnection.isValid(timeout);
   }


   @Override
   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      this.dbConnection.setClientInfo(name, value);
   }


   @Override
   public void setClientInfo(Properties properties) throws SQLClientInfoException {
      this.dbConnection.setClientInfo(properties);
   }


   @Override
   public String getClientInfo(String name) throws SQLException {
      return this.dbConnection.getClientInfo(name);
   }


   @Override
   public Properties getClientInfo() throws SQLException {
      return this.dbConnection.getClientInfo();
   }


   @Override
   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      return this.dbConnection.createArrayOf(typeName, elements);
   }


   @Override
   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      return this.dbConnection.createStruct(typeName, attributes);
   }


   @Override
   public void setSchema(String schema) throws SQLException {
      this.dbConnection.setSchema(schema);
   }


   @Override
   public String getSchema() throws SQLException {
      return this.dbConnection.getSchema();
   }


   @Override
   public void abort(Executor executor) throws SQLException {
      this.dbConnection.abort(executor);
   }


   @Override
   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      this.dbConnection.setNetworkTimeout(executor, milliseconds);
   }


   @Override
   public int getNetworkTimeout() throws SQLException {
      return this.dbConnection.getNetworkTimeout();
   }
}
