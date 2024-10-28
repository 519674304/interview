package com.wkk.business.config.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ManualTransactionManager {

    private static final ThreadLocal<Map<String, TransactionStatus>> transactionContext = new ThreadLocal<>();
    private static final Map<Connection, TransactionStatus> suspendedTransactions = new ConcurrentHashMap<>();

    public static Connection getDataSource() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "user", "password");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get connection", e);
        }
    }

    public static void beginTransaction() throws SQLException {
        Connection conn = getDataSource();
        conn.setAutoCommit(false);
        TransactionStatus status = new TransactionStatus(conn);
        Map<String, TransactionStatus> context = transactionContext.get();
        if (context == null) {
            context = new HashMap<>();
            transactionContext.set(context);
        }
        context.put("transaction", status);
    }

    public static void commit() throws SQLException {
        Map<String, TransactionStatus> context = transactionContext.get();
        if (context != null) {
            TransactionStatus status = context.get("transaction");
            if (status != null) {
                status.commit();
                context.remove("transaction");
            }
        }
    }

    public static void rollback() throws SQLException {
        Map<String, TransactionStatus> context = transactionContext.get();
        if (context != null) {
            TransactionStatus status = context.get("transaction");
            if (status != null) {
                status.rollback();
                context.remove("transaction");
            }
        }
    }

    public static void suspend() throws SQLException {
        Map<String, TransactionStatus> context = transactionContext.get();
        if (context != null) {
            TransactionStatus status = context.get("transaction");
            if (status != null) {
                Connection conn = status.getConnection();
                conn.setAutoCommit(true);
                suspendedTransactions.put(conn, status);
                context.remove("transaction");
            }
        }
    }

    public static void resume() throws SQLException {
        for (Map.Entry<Connection, TransactionStatus> entry : suspendedTransactions.entrySet()) {
            Connection conn = entry.getKey();
            TransactionStatus status = entry.getValue();
            conn.setAutoCommit(false);
            Map<String, TransactionStatus> context = transactionContext.get();
            if (context == null) {
                context = new HashMap<>();
                transactionContext.set(context);
            }
            context.put("transaction", status);
            suspendedTransactions.remove(conn);
            break; // 只恢复一个事务
        }
    }

    public static class TransactionStatus {
        private final Connection connection;

        public TransactionStatus(Connection connection) {
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }

        public void commit() throws SQLException {
            connection.commit();
            connection.close();
        }

        public void rollback() throws SQLException {
            connection.rollback();
            connection.close();
        }
    }

    public static void main(String[] args) {
        try {
            beginTransaction();
            // 执行一些数据库操作
            suspend();
            // 执行其他操作
            resume();
            // 继续执行数据库操作
            commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
