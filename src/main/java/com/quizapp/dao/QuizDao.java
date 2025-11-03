package com.quizapp.dao;

import com.quizapp.exception.DatabaseException;
import com.quizapp.exception.QuizNotFoundException;
import com.quizapp.model.Quiz;
import com.quizapp.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class QuizDao {
    private static final Logger logger = LoggerFactory.getLogger(QuizDao.class);

    public void addQuiz(String title, String description) {
        String sql = "INSERT INTO quizzes(title, description) VALUES (?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.executeUpdate();
            logger.info("Quiz '{}' added successfully.", title);
        } catch (SQLException e) {
            logger.error("Error adding quiz '{}': {}", title, e.getMessage());
        }
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> list = new ArrayList<>();
        String sql = "SELECT * FROM quizzes";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Quiz(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description")
                ));
            }
            logger.info("Fetched {} quizzes from database.", list.size());
        } catch (SQLException e) {
            logger.error("Error fetching all quizzes: {}", e.getMessage());
        }
        return list;
    }

    public void deleteQuiz(int id) {
        String sql = "DELETE FROM quizzes WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.info("Quiz with ID {} deleted successfully.", id);
        } catch (SQLException e) {
            logger.error("Error deleting quiz with ID {}: {}", id, e.getMessage());
        }
    }

    public Quiz getQuizById(int id) throws QuizNotFoundException {
        String sql = "SELECT * FROM quizzes WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Quiz found with ID {}", id);
                return new Quiz(rs.getInt("id"), rs.getString("title"), rs.getString("description"));
            } else {
                logger.warn("Quiz with ID {} not found.", id);
                throw new QuizNotFoundException("Quiz with ID " + id + " not found.");
            }

        } catch (SQLException e) {
            logger.error("Database error fetching quiz with ID {}: {}", id, e.getMessage());
            throw new DatabaseException("Error fetching quiz with ID " + id, e);
        }
    }
}













//package com.quizapp.dao;
//
//
//import com.quizapp.exception.DatabaseException;
//import com.quizapp.exception.QuizNotFoundException;
//import com.quizapp.model.Quiz;
//import com.quizapp.util.DBUtil;
//import java.sql.*;
//import java.util.*;
//
//public class QuizDao {
//
//    public void addQuiz(String title, String description) {
//        String sql = "INSERT INTO quizzes(title, description) VALUES (?, ?)";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setString(1, title);
//            ps.setString(2, description);
//            ps.executeUpdate();
//            System.out.println("Quiz added successfully!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<Quiz> getAllQuizzes() {
//        List<Quiz> list = new ArrayList<>();
//        String sql = "SELECT * FROM quizzes";
//        try (Connection con = DBUtil.getConnection();
//             Statement st = con.createStatement();
//             ResultSet rs = st.executeQuery(sql)) {
//            while (rs.next()) {
//                list.add(new Quiz(
//                        rs.getInt("id"),
//                        rs.getString("title"),
//                        rs.getString("description")
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public void deleteQuiz(int id) {
//        String sql = "DELETE FROM quizzes WHERE id = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            ps.executeUpdate();
//            System.out.println("Quiz deleted successfully!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Quiz getQuizById(int id) throws QuizNotFoundException {
//        String sql = "SELECT * FROM quizzes WHERE id = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                return new Quiz(rs.getInt("id"), rs.getString("title"), rs.getString("description"));
//            } else {
//                throw new QuizNotFoundException("Quiz with ID " + id + " not found.");
//            }
//
//        } catch (SQLException e) {
//            throw new DatabaseException("Error fetching quiz with ID " + id, e);
//        }
//    }
//
////    public Quiz getQuizById(int id) {
////        String sql = "SELECT * FROM quizzes WHERE id = ?";
////        try (Connection con = DBUtil.getConnection();
////             PreparedStatement ps = con.prepareStatement(sql)) {
////            ps.setInt(1, id);
////            ResultSet rs = ps.executeQuery();
////            if (rs.next()) {
////                return new Quiz(rs.getInt("id"), rs.getString("title"), rs.getString("description"));
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
//}
