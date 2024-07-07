package org.another;

import java.io.FileWriter;
import java.sql.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Zadania{
    public static void main(String[] args) {
        SingletonEngine singletonEngine = new SingletonEngine();
        try {
            singletonEngine.transfer("100");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

class SingletonEngine {
    private Connection connectionTo;
    private Connection connectionFrom;
    private static SingletonEngine instance = null;
    public synchronized static SingletonEngine getInstance() {
        if (instance == null) {
            instance = new SingletonEngine();
            instance.getConnectionTo();
            instance.getConnectionFrom();
        }
        return instance;
    }
    private Connection getConnectionFrom() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionFrom = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_from", "root", "werpipl15");
            connectionFrom.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage() + "SQLState: " + e.getSQLState());
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        }
        return connectionFrom;
    }

    private Connection getConnectionTo() {
        final String connectionToAdress = "jdbc:mysql://localhost:3306/test_to";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionTo = DriverManager.getConnection(connectionToAdress, "root", "werpipl15");
            connectionTo.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage() + "SQLState: " + e.getSQLState());
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found");
        }
        return connectionTo;
    }

    public void transfer(String summa) throws SQLException {
        Statement stFrom = null;
        Statement stTo = null;
        try {
            int sum = Integer.parseInt(summa);
            if (sum <= 0) {
                throw new NumberFormatException("less or equals zero");
            }
            stFrom = connectionFrom.createStatement();
            stTo = connectionTo.createStatement();
            //транзакция из 4-х запросов
            ResultSet rsFrom = stFrom.executeQuery("SELECT * FROM test_from");
            ResultSet rsTo = stTo.executeQuery("SELECT * FROM test_to");
            int accountFrom = 0;
            while (rsFrom.next()) {
                accountFrom = rsFrom.getInt(1);
            }
            int resultFrom = 0;
            if (accountFrom >= sum) {
                resultFrom = accountFrom - sum;
            } else {
                throw new SQLException("Invalid balance");
            }
            int accountTo = 0;
            while (rsTo.next()) {
                accountTo = rsTo.getInt(1);
            }
            int resultTo = accountTo + sum;
            stFrom.executeUpdate("UPDATE test_from SET balanse=" + resultFrom);
            stTo.executeUpdate("UPDATE test_to SET balanse=" + resultTo);
            //завершение транзакции
            connectionFrom.commit();
            connectionTo.commit();
            System.out.println("remainig on : " + resultFrom + " rub");
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState()
            + "Error Message: " + e.getMessage());
            //откат транзакции при ошибке
            connectionFrom.rollback();
            connectionTo.rollback();;
        } catch (NumberFormatException e) {
            System.err.println("Invalid summa: " + summa);
        } finally {
            if (stFrom != null) {
                try {
                    stFrom.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stTo != null) {
                try {
                    stTo.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


