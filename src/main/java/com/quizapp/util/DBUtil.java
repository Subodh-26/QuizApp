package com.quizapp.util;

import com.quizapp.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);

    public static Connection getConnection() {
        try {
            Properties prop = new Properties();
            InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");

            if (input == null) {
                logger.error("Database configuration file not found.");
                throw new DatabaseException("Unable to find db.properties", null);
            }

            prop.load(input);
            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            String driver = prop.getProperty("db.driver");

            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            logger.info("Database connected successfully!");
            return connection;

        } catch (IOException e) {
            logger.error("Error reading db.properties: {}", e.getMessage());
            throw new DatabaseException("Error reading database configuration file", e);
        } catch (SQLException e) {
            logger.error("SQL Error while connecting: {}", e.getMessage());
            throw new DatabaseException("Database connection failed", e);
        } catch (ClassNotFoundException e) {
            logger.error("JDBC Driver not found: {}", e.getMessage());
            throw new DatabaseException("JDBC driver not found", e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed.");
            }
        } catch (SQLException e) {
            logger.error("Error closing connection: {}", e.getMessage());
        }
    }
}
















//package com.quizapp.util;
//
//import com.quizapp.exception.DatabaseException;
//
//import java.sql.*;
//import java.util.Properties;
//import java.io.InputStream;
//import java.io.IOException;
//
//public class DBUtil {
//
//    public static Connection getConnection() {
//        try {
//            Properties prop = new Properties();
//            InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
//
//            if (input == null) {
//                throw new DatabaseException("Unable to find db.properties", null);
//            }
//
//            prop.load(input);
//
//            String url = prop.getProperty("db.url");
//            String username = prop.getProperty("db.username");
//            String password = prop.getProperty("db.password");
//            String driver = prop.getProperty("db.driver");
//
//            Class.forName(driver);
//            Connection connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Database connected successfully!");
//            return connection;
//
//        } catch (IOException e) {
//            throw new DatabaseException("Error reading database configuration file", e);
//        } catch (SQLException e) {
//            throw new DatabaseException("Database connection failed", e);
//        } catch (ClassNotFoundException e) {
//            throw new DatabaseException("JDBC driver not found", e);
//        }
//    }
//
//    public static void closeConnection(Connection connection) {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                System.out.println("Database connection closed.");
//            }
//        } catch (SQLException e) {
//            throw new DatabaseException("Error closing database connection", e);
//        }
//    }
//}











//package com.quizapp.util;
//
//import java.sql.*;
//import java.util.Properties;
//import java.io.InputStream;
//import java.io.IOException;
//
//public class DBUtil {
//    private static Connection connection;
//
//    public static Connection getConnection() {
//        try {
//            if (connection == null || connection.isClosed()) {
//                Properties prop = new Properties();
//                InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
//
//                if (input == null) {
//                    System.out.println("Sorry, unable to find db.properties");
//                    return null;
//                }
//
//                prop.load(input);
//
//                String url = prop.getProperty("db.url");
//                String username = prop.getProperty("db.username");
//                String password = prop.getProperty("db.password");
//                String driver = prop.getProperty("db.driver");
//
//                Class.forName(driver);
//                connection = DriverManager.getConnection(url, username, password);
//                System.out.println("Database connected successfully!");
//            }
//        } catch (IOException | SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                System.out.println("Database connection closed.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
