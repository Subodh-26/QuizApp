package com.quizapp.service;

import com.quizapp.dao.QuizDao;
import com.quizapp.dao.QuestionDao;
import com.quizapp.exception.InvalidInputException;
import com.quizapp.exception.QuizNotFoundException;
import com.quizapp.model.Quiz;
import com.quizapp.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final QuizDao quizDao = new QuizDao();
    private final QuestionDao questionDao = new QuestionDao();

    public void addQuiz(String title, String description) throws InvalidInputException {
        logger.info("Attempting to add quiz with title: {}", title);

        if (title == null || title.isEmpty()) {
            logger.error("Quiz title is empty");
            throw new InvalidInputException("Quiz title cannot be empty.");
        }

        quizDao.addQuiz(title, description);
        logger.info("Quiz '{}' added successfully", title);
    }

    public List<Quiz> viewQuizzes() {
        logger.info("Fetching all quizzes");
        List<Quiz> quizzes = quizDao.getAllQuizzes();
        logger.info("Fetched {} quizzes", quizzes.size());
        return quizzes;
    }

    public void deleteQuiz(int quizId) throws QuizNotFoundException {
        logger.info("Attempting to delete quiz with ID: {}", quizId);

        Quiz quiz = quizDao.getQuizById(quizId);
        if (quiz == null) {
            logger.warn("Quiz with ID {} not found", quizId);
            throw new QuizNotFoundException("Cannot delete: Quiz with ID " + quizId + " not found.");
        }

        quizDao.deleteQuiz(quizId);
        logger.info("Quiz with ID {} deleted successfully", quizId);
    }

    public void addQuestion(Question question) throws InvalidInputException {
        logger.info("Adding question to quiz ID {}", question.getQuizId());

        if (question.getQuestionText() == null || question.getQuestionText().isEmpty()) {
            logger.error("Question text is empty");
            throw new InvalidInputException("Question text cannot be empty.");
        }

        questionDao.addQuestion(question);
        logger.info("Question added successfully to quiz ID {}", question.getQuizId());
    }

    public List<Question> viewQuestions(int quizId) throws QuizNotFoundException {
        logger.info("Fetching questions for quiz ID {}", quizId);
        List<Question> list = questionDao.getQuestionsByQuizId(quizId);

        if (list.isEmpty()) {
            logger.warn("No questions found for quiz ID {}", quizId);
            throw new QuizNotFoundException("No questions found for Quiz ID " + quizId);
        }

        logger.info("Fetched {} questions for quiz ID {}", list.size(), quizId);
        return list;
    }
}




















//package com.quizapp.service;
//
//import com.quizapp.dao.QuizDao;
//import com.quizapp.dao.QuestionDao;
//import com.quizapp.exception.InvalidInputException;
//import com.quizapp.exception.QuizNotFoundException;
//import com.quizapp.model.Quiz;
//import com.quizapp.model.Question;
//import java.util.*;
//
//public class AdminService {
//
//    private QuizDao quizDao = new QuizDao();
//    private QuestionDao questionDao = new QuestionDao();
//
//    public void addQuiz(String title, String description) throws InvalidInputException {
//        if (title == null || title.isEmpty()) {
//            throw new InvalidInputException("Quiz title cannot be empty.");
//        }
//        quizDao.addQuiz(title, description);
//    }
//
//    public List<Quiz> viewQuizzes() {
//        return quizDao.getAllQuizzes();
//    }
//
//    public void deleteQuiz(int quizId) throws QuizNotFoundException {
//        Quiz quiz = quizDao.getQuizById(quizId);
//        if (quiz == null) {
//            throw new QuizNotFoundException("Cannot delete: Quiz with ID " + quizId + " not found.");
//        }
//        quizDao.deleteQuiz(quizId);
//    }
//
//    public void addQuestion(Question question) throws InvalidInputException {
//        if (question.getQuestionText() == null || question.getQuestionText().isEmpty()) {
//            throw new InvalidInputException("Question text cannot be empty.");
//        }
//        questionDao.addQuestion(question);
//    }
//
//    public List<Question> viewQuestions(int quizId) throws QuizNotFoundException {
//        List<Question> list = questionDao.getQuestionsByQuizId(quizId);
//        if (list.isEmpty()) {
//            throw new QuizNotFoundException("No questions found for Quiz ID " + quizId);
//        }
//        return list;
//    }
//}
//
////public class AdminService {
////
////    private QuizDao quizDao = new QuizDao();
////    private QuestionDao questionDao = new QuestionDao();
////
////    // Add quiz
////    public void addQuiz(String title, String description) {
////        quizDao.addQuiz(title, description);
////    }
////
////    // View all quizzes
////    public void viewQuizzes() {
////        List<Quiz> quizzes = quizDao.getAllQuizzes();
////        System.out.println("Available Quizzes:");
////        for (Quiz q : quizzes) {
////            System.out.println(q.getId() + ". " + q.getTitle() + " - " + q.getDescription());
////        }
////    }
////
////    // Delete quiz
////    public void deleteQuiz(int quizId) {
////        quizDao.deleteQuiz(quizId);
////    }
////
////    // Add question to quiz
////    public void addQuestion(Question question) {
////        questionDao.addQuestion(question);
////    }
////
////    // View all questions in a quiz
////    public void viewQuestions(int quizId) {
////        List<Question> list = questionDao.getQuestionsByQuizId(quizId);
////        for (Question q : list) {
////            System.out.println("Q" + q.getId() + ": " + q.getQuestionText());
////            System.out.println("A) " + q.getOptionA());
////            System.out.println("B) " + q.getOptionB());
////            System.out.println("C) " + q.getOptionC());
////            System.out.println("D) " + q.getOptionD());
////            System.out.println("---------------------------");
////        }
////    }
////}
////
