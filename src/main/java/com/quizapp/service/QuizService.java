package com.quizapp.service;

import com.quizapp.dao.QuestionDao;
import com.quizapp.dao.ResultDao;
import com.quizapp.exception.QuizNotFoundException;
import com.quizapp.model.Question;
import com.quizapp.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);
    private final QuestionDao questionDao = new QuestionDao();
    private final ResultDao resultDao = new ResultDao();

    public void startQuiz(int userId, int quizId, Scanner sc) throws QuizNotFoundException {
        logger.info("Starting quiz for user ID {} and quiz ID {}", userId, quizId);
        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);

        if (questions.isEmpty()) {
            logger.warn("No questions found for quiz ID {}", quizId);
            throw new QuizNotFoundException("No questions found for quiz ID " + quizId);
        }

        int score = 0;
        for (Question q : questions) {
            System.out.println(q.getQuestionText());
            System.out.println("A) " + q.getOptionA());
            System.out.println("B) " + q.getOptionB());
            System.out.println("C) " + q.getOptionC());
            System.out.println("D) " + q.getOptionD());
            System.out.print("Enter your answer (A/B/C/D): ");
            char ans = Character.toUpperCase(sc.next().charAt(0));

            if (ans == q.getCorrectOption()) {
                score++;
            }
            System.out.println();
        }

        logger.info("User ID {} completed quiz ID {} with score {}", userId, quizId, score);
        System.out.println("Quiz Completed! Your score: " + score + "/" + questions.size());
        resultDao.saveResult(new Result("", 0, userId, quizId, score));
    }

    public void showLeaderboard(int quizId) {
        logger.info("Fetching leaderboard for quiz ID {}", quizId);
        List<Result> leaderboard = resultDao.getLeaderboard(quizId);

        System.out.println("===== Leaderboard =====");
        int rank = 1;
        for (Result r : leaderboard) {
            System.out.println(rank++ + ". UserID: " + r.getUserId() + " | Name: " + r.getUsername() + " | Score: " + r.getScore());
        }

        logger.info("Displayed leaderboard for quiz ID {}", quizId);
    }
}





























//package com.quizapp.service;
//
//import com.quizapp.dao.QuestionDao;
//import com.quizapp.dao.ResultDao;
//import com.quizapp.exception.QuizNotFoundException;
//import com.quizapp.model.Question;
//import com.quizapp.model.Result;
//import java.util.*;
//
//public class QuizService {
//
//    private QuestionDao questionDao = new QuestionDao();
//    private ResultDao resultDao = new ResultDao();
//
//    // Start quiz for a user
//    public void startQuiz(int userId, int quizId, Scanner sc) throws QuizNotFoundException {
//        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);
//        if (questions.isEmpty()) {
//            throw new QuizNotFoundException("No questions found for quiz ID " + quizId);
//        }
//
//        int score = 0;
//        for (Question q : questions) {
//            System.out.println(q.getQuestionText());
//            System.out.println("A) " + q.getOptionA());
//            System.out.println("B) " + q.getOptionB());
//            System.out.println("C) " + q.getOptionC());
//            System.out.println("D) " + q.getOptionD());
//            System.out.print("Enter your answer (A/B/C/D): ");
//            char ans = Character.toUpperCase(sc.next().charAt(0));
//
//            if (ans == q.getCorrectOption()) {
//                score++;
//            }
//            System.out.println();
//        }
//
//        System.out.println("Quiz Completed! Your score: " + score + "/" + questions.size());
//        resultDao.saveResult(new Result("", 0, userId, quizId, score));
//    }
//
//
//
//
////    public void startQuiz(int userId, int quizId, Scanner sc) {
////        List<Question> questions = questionDao.getQuestionsByQuizId(quizId);
////        if (questions.isEmpty()) {
////            System.out.println("No questions available in this quiz!");
////            return;
////        }
//
////        int score = 0;
////        for (Question q : questions) {
////            System.out.println(q.getQuestionText());
////            System.out.println("A) " + q.getOptionA());
////            System.out.println("B) " + q.getOptionB());
////            System.out.println("C) " + q.getOptionC());
////            System.out.println("D) " + q.getOptionD());
////            System.out.print("Enter your answer (A/B/C/D): ");
////            char ans = Character.toUpperCase(sc.next().charAt(0));
////
////            if (ans == q.getCorrectOption()) {
////                score++;
////            }
////            System.out.println();
////        }
////
////        System.out.println("Quiz Completed! Your score: " + score + "/" + questions.size());
////        resultDao.saveResult(new Result("",0, userId, quizId, score));
////    }
//
//    // Show leaderboard
//    public void showLeaderboard(int quizId) {
//        List<Result> leaderboard = resultDao.getLeaderboard(quizId);
//        System.out.println("===== Leaderboard =====");
//        int rank = 1;
//        for (Result r : leaderboard) {
//            System.out.println(rank++ + ". UserID: " + r.getUserId() +". | Name:  "+ r.getUsername() + " | Score: " + r.getScore());
//        }
//    }
//}
