package com.quizapp.dao;

import com.quizapp.model.Result;
import com.quizapp.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class ResultDao {
    private static final Logger logger = LoggerFactory.getLogger(ResultDao.class);

    public void saveResult(Result r) {
        String sql = "INSERT INTO results(user_id, quiz_id, score) VALUES (?, ?, ?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getQuizId());
            ps.setInt(3, r.getScore());
            ps.executeUpdate();
            logger.info("Result saved for user {} in quiz {}.", r.getUserId(), r.getQuizId());
        } catch (SQLException e) {
            logger.error("Error saving result for user {}: {}", r.getUserId(), e.getMessage());
        }
    }

    public List<Result> getLeaderboard(int quizId) {
        List<Result> list = new ArrayList<>();
        String sql = """
            SELECT u.username, u.id, r.user_id, r.quiz_id, r.score
            FROM users u
            JOIN results r ON u.id = r.user_id
            WHERE r.quiz_id = ?
            ORDER BY r.score DESC
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Result(
                        rs.getString("username"),
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("quiz_id"),
                        rs.getInt("score")
                ));
            }
            logger.info("Fetched leaderboard for quiz ID {} with {} entries.", quizId, list.size());
        } catch (SQLException e) {
            logger.error("Error fetching leaderboard for quiz {}: {}", quizId, e.getMessage());
        }
        return list;
    }
}















//package com.quizapp.dao;
//
//import com.quizapp.model.Result;
//import com.quizapp.util.DBUtil;
//import java.sql.*;
//import java.util.*;
//
//public class ResultDao {
//
//    public void saveResult(Result r) {
//        String sql = "INSERT INTO results(user_id, quiz_id, score) VALUES (?, ?, ?)";
//        try (Connection con = DBUtil.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, r.getUserId());
//            ps.setInt(2, r.getQuizId());
//            ps.setInt(3, r.getScore());
//            ps.executeUpdate();
//            System.out.println("Result saved!");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
////    public List<Result> getLeaderboard(int quizId) {
////        List<Result> list = new ArrayList<>();
//////        String sql = "SELECT * FROM results WHERE quiz_id = ? ORDER BY score DESC LIMIT 10";
////       String sql = " select u.username,u.id, r.score,r.quiz_id from users u join results r on u.id = r.user_id order by r.score desc" ;
////        try (Connection con = DBUtil.getConnection();
////             PreparedStatement ps = con.prepareStatement(sql)) {
////            ps.setInt(1, quizId);
////            ResultSet rs = ps.executeQuery();
////            while (rs.next()) {
////                list.add(new Result(
////                       // rs.getInt("id"),
////                        rs.getString("username"),
////                        rs.getInt("user_id"),
////                        rs.getInt("quiz_id"),
////                        rs.getInt("score")
////                ));
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return list;
////    }
//public List<Result> getLeaderboard(int quizId) {
//    List<Result> list = new ArrayList<>();
//    String sql = """
//        SELECT u.username, u.id, r.user_id, r.quiz_id, r.score
//        FROM users u
//        JOIN results r ON u.id = r.user_id
//        WHERE r.quiz_id = ?
//        ORDER BY r.score DESC
//        """;
//
//    try (Connection con = DBUtil.getConnection();
//         PreparedStatement ps = con.prepareStatement(sql)) {
//
//        ps.setInt(1, quizId);  //  quizId placeholder now exists
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            list.add(new Result(
//                    rs.getString("username"),
//                    rs.getInt("id"),
//                    rs.getInt("user_id"),
//                    rs.getInt("quiz_id"),
//                    rs.getInt("score")
//            ));
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return list;
//}
//
//}
