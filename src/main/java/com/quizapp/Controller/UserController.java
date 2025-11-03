package com.quizapp.Controller;

import com.quizapp.exception.DatabaseException;
import com.quizapp.model.User;
import com.quizapp.model.Quiz;
import com.quizapp.service.QuizService;
import com.quizapp.dao.QuizDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final QuizService quizService = new QuizService();
    private final QuizDao quizDao = new QuizDao();
    private final Scanner sc = new Scanner(System.in);

    public void userMenu(User user) {
        logger.info("User '{}' logged in.", user.getUsername());
        System.out.println("\n===== Welcome User, " + user.getUsername() + " =====");

        while (true) {
            System.out.println("\n1. View All Quizzes");
            System.out.println("2. Attempt Quiz");
            System.out.println("3. View Leaderboard");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> viewQuizzes();
                case 2 -> attemptQuiz(user);
                case 3 -> viewLeaderboard();
                case 4 -> {
                    logger.info("User '{}' logged out.", user.getUsername());
                    System.out.println("Logged out successfully!");
                    return;
                }
                default -> {
                    logger.warn("Invalid choice entered by user '{}': {}", user.getUsername(), choice);
                    System.out.println("Invalid choice!");
                }
            }
        }
    }

    private void viewQuizzes() {
        logger.info("User viewing all quizzes.");
        List<Quiz> quizzes = quizDao.getAllQuizzes();
        for (Quiz q : quizzes) {
            System.out.println(q.getId() + ". " + q.getTitle() + " - " + q.getDescription());
        }
    }

    private void attemptQuiz(User user) {
        System.out.print("Enter Quiz ID: ");
        int quizId = sc.nextInt();
        logger.info("User '{}' attempting quiz ID: {}", user.getUsername(), quizId);
        try {
            quizService.startQuiz(user.getId(), quizId, sc);
        } catch (DatabaseException e) {
            logger.error("Database error for quiz ID {}: {}", quizId, e.getMessage());
            System.out.println("Database error while starting quiz.");
        } catch (Exception e) {
            logger.error("Unexpected error during quiz attempt: ", e);
        }
    }

    private void viewLeaderboard() {
        System.out.print("Enter Quiz ID: ");
        int quizId = sc.nextInt();
        logger.info("User viewing leaderboard for quiz ID: {}", quizId);
        quizService.showLeaderboard(quizId);
    }
}













//package com.quizapp.Controller;
//
//import com.quizapp.exception.DatabaseException;
//import com.quizapp.exception.QuizNotFoundException;
//import com.quizapp.model.User;
//import com.quizapp.model.Quiz;
//import com.quizapp.service.QuizService;
//import com.quizapp.dao.QuizDao;
//import java.util.*;
//
//public class UserController {
//
//    private final QuizService quizService = new QuizService();
//    private final QuizDao quizDao = new QuizDao();
//    private final Scanner sc = new Scanner(System.in);
//
//    public void userMenu(User user) {
//        System.out.println("\n===== Welcome User, " + user.getUsername() + " =====");
//
//        while (true) {
//            System.out.println("\n1. View All Quizzes");
//            System.out.println("2. Attempt Quiz");
//            System.out.println("3. View Leaderboard");
//            System.out.println("4. Logout");
//            System.out.print("Enter choice: ");
//            int choice = sc.nextInt();
//
//            switch (choice) {
//                case 1 -> viewQuizzes();
//                case 2 -> attemptQuiz(user);
//                case 3 -> viewLeaderboard();
//                case 4 -> {
//                    System.out.println("Logged out successfully!");
//                    return;
//                }
//                default -> System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    private void viewQuizzes() {
//        List<Quiz> quizzes = quizDao.getAllQuizzes();
//        for (Quiz q : quizzes) {
//            System.out.println(q.getId() + ". " + q.getTitle() + " - " + q.getDescription());
//        }
//    }
//
//    private void attemptQuiz(User user) {
//        System.out.print("Enter Quiz ID: ");
//        int quizId = sc.nextInt();
//        try {
//            quizService.startQuiz(user.getId(), quizId, sc);
//        } catch (DatabaseException e) {
//            System.out.println("Database error while starting quiz.");
//        } catch (Exception e) {
//           e.printStackTrace();
//        }
//    }
//
//
////    private void attemptQuiz(User user) {
////        System.out.print("Enter Quiz ID: ");
////        int quizId = sc.nextInt();
////        quizService.startQuiz(user.getId(), quizId, sc);
////    }
//
//    private void viewLeaderboard() {
//        System.out.print("Enter Quiz ID: ");
//        int quizId = sc.nextInt();
//        quizService.showLeaderboard(quizId);
//    }
//}
