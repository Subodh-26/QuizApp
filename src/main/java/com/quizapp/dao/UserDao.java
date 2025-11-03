package com.quizapp.dao;

import com.quizapp.model.User;
import com.quizapp.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void registerUser(String username, String password, String role) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();
            logger.info("User '{}' registered successfully.", username);
        } catch (SQLException e) {
            logger.error("Error registering user '{}': {}", username, e.getMessage());
        }
    }

//    public User findByUsername(String username) {
//        String sql = "SELECT * FROM users WHERE username = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setString(1, username);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                logger.info("User '{}' found in database.", username);
//                return new User(
//                        rs.getInt("id"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getString("role")
//                );
//            } else {
//                logger.warn("User '{}' not found.", username);
//            }
//        } catch (SQLException e) {
//            logger.error("Error finding user '{}': {}", username, e.getMessage());
//        }
//        return null;
//    }

    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                logger.info("Login successful for '{}'", username);
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            } else {
                logger.warn("Invalid credentials for '{}'", username);
            }
        } catch (SQLException e) {
            logger.error("Error during login for '{}': {}", username, e.getMessage());
        }
        return null;
    }
}















//package com.quizapp.dao;
//
//import com.quizapp.model.User;
//import com.quizapp.util.DBUtil;
//import java.sql.*;
//import java.util.*;
//
//public class UserDao {
//
//    // Register new user
//    public void registerUser(String username, String password, String role) {
//        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ps.setString(3, role);
//            ps.executeUpdate();
//            System.out.println("User registered successfully!");
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//
//    // Find user by username
//    public User findByUsername(String username) {
//        String sql = "SELECT * FROM users WHERE username = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setString(1, username);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new User(
//                        rs.getInt("id"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getString("role")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    // Validate login
//    public User login(String username, String password) {
//        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return new User(
//                        rs.getInt("id"),
//                        rs.getString("username"),
//                        rs.getString("password"),
//                        rs.getString("role")
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
