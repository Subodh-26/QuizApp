package com.quizapp.dao;

import com.quizapp.model.Question;
import com.quizapp.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class QuestionDao {
    private static final Logger logger = LoggerFactory.getLogger(QuestionDao.class);

    public void addQuestion(Question q) {
        String sql = "INSERT INTO questions(quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, q.getQuizId());
            ps.setString(2, q.getQuestionText());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, String.valueOf(q.getCorrectOption()));
            ps.executeUpdate();
            logger.info("Question added for quiz ID {}", q.getQuizId());
        } catch (SQLException e) {
            logger.error("Error adding question for quiz {}: {}", q.getQuizId(), e.getMessage());
        }
    }

    public List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> list = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Question(
                        rs.getInt("id"),
                        rs.getInt("quiz_id"),
                        rs.getString("question_text"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option").charAt(0)
                ));
            }
            logger.info("Fetched {} questions for quiz ID {}", list.size(), quizId);
        } catch (SQLException e) {
            logger.error("Error fetching questions for quiz ID {}: {}", quizId, e.getMessage());
        }
        return list;
    }

    public void deleteQuestion(int id) {
        String sql = "DELETE FROM questions WHERE id = ?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            logger.info("Question with ID {} deleted successfully.", id);
        } catch (SQLException e) {
            logger.error("Error deleting question with ID {}: {}", id, e.getMessage());
        }
    }
}

















//package com.quizapp.dao;
//
//import com.quizapp.model.Question;
//import com.quizapp.util.DBUtil;
//import java.sql.*;
//import java.util.*;
//
//public class QuestionDao {
//
//    public void addQuestion(Question q) {
//        String sql = "INSERT INTO questions(quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, q.getQuizId());
//            ps.setString(2, q.getQuestionText());
//            ps.setString(3, q.getOptionA());
//            ps.setString(4, q.getOptionB());
//            ps.setString(5, q.getOptionC());
//            ps.setString(6, q.getOptionD());
//            ps.setString(7, String.valueOf(q.getCorrectOption()));
//            ps.executeUpdate();
//            System.out.println("Question added successfully!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public List<Question> getQuestionsByQuizId(int quizId) {
//        List<Question> list = new ArrayList<>();
//        String sql = "SELECT * FROM questions WHERE quiz_id = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, quizId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                list.add(new Question(
//                        rs.getInt("id"),
//                        rs.getInt("quiz_id"),
//                        rs.getString("question_text"),
//                        rs.getString("option_a"),
//                        rs.getString("option_b"),
//                        rs.getString("option_c"),
//                        rs.getString("option_d"),
//                        rs.getString("correct_option").charAt(0)
//                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public void deleteQuestion(int id) {
//        String sql = "DELETE FROM questions WHERE id = ?";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, id);
//            ps.executeUpdate();
//            System.out.println("Question deleted successfully!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
