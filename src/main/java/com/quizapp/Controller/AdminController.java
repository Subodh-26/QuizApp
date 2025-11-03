package com.quizapp.Controller;

import com.quizapp.exception.InvalidInputException;
import com.quizapp.exception.QuizNotFoundException;
import com.quizapp.model.Question;
import com.quizapp.model.User;
import com.quizapp.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final AdminService adminService = new AdminService();
    private final Scanner sc = new Scanner(System.in);

    public void adminMenu(User admin) throws InvalidInputException, QuizNotFoundException {
        logger.info("Admin '{}' logged in.", admin.getUsername());
        System.out.println("\n===== Welcome Admin, " + admin.getUsername() + " =====");

        while (true) {
            System.out.println("\n1. Add Quiz");
            System.out.println("2. View All Quizzes");
            System.out.println("3. Delete Quiz");
            System.out.println("4. Add Question to Quiz");
            System.out.println("5. View Questions of Quiz");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addQuiz();
                case 2 -> {
                    logger.info("Admin requested to view all quizzes.");
                    var quizzes = adminService.viewQuizzes();

                    if (quizzes == null || quizzes.isEmpty()) {
                        System.out.println("No quizzes found.");
                        logger.warn("No quizzes available to display.");
                    } else {
                        System.out.println("\n===== Available Quizzes =====");
                        for (var q : quizzes) {
                            System.out.println(q.getId() + ". " + q.getTitle() + " - " + q.getDescription());
                        }
                        logger.info("Displayed {} quizzes to admin.", quizzes.size());
                    }
                }

                case 3 -> deleteQuiz();
                case 4 -> addQuestion();
                case 5 -> viewQuestions();
                case 6 -> {
                    logger.info("Admin '{}' logged out.", admin.getUsername());
                    System.out.println("Logged out successfully!");
                    return;
                }
                default -> {
                    logger.warn("Invalid choice entered by admin: {}", choice);
                    System.out.println("Invalid choice!");
                }
            }
        }
    }

    private void addQuiz() throws InvalidInputException {
        System.out.print("Enter quiz title: ");
        String title = sc.nextLine();
        System.out.print("Enter description: ");
        String desc = sc.nextLine();

        logger.info("Admin adding new quiz with title '{}'.", title);

        try {
            adminService.addQuiz(title, desc);
            System.out.println("Quiz '" + title + "' added successfully!");
            logger.info("Quiz '{}' added successfully.", title);
        } catch (Exception e) {
            System.out.println(" Failed to add quiz: " + e.getMessage());
            logger.error("Error adding quiz '{}': {}", title, e.getMessage());
        }
    }
    private void deleteQuiz() {
        System.out.print("Enter quiz ID to delete: ");
        int id = sc.nextInt();

        logger.info("Admin requested to delete quiz ID: {}", id);

        try {
            adminService.deleteQuiz(id);
            System.out.println(" Quiz with ID " + id + " deleted successfully!");
            logger.info("Quiz with ID {} deleted successfully", id);

        } catch (QuizNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            logger.warn("Failed to delete quiz with ID {} - {}", id, e.getMessage());
        } catch (Exception e) {
            System.out.println(" Unexpected error while deleting quiz.");
            logger.error("Unexpected error while deleting quiz ID {}: {}", id, e.getMessage());
        }

        System.out.println();
    }


    private void addQuestion() throws InvalidInputException {
        System.out.print("Enter quiz ID: ");
        int quizId = sc.nextInt();
        sc.nextLine();

        logger.info("Admin adding question to quiz ID: {}", quizId);

        System.out.print("Enter question text: ");
        String qText = sc.nextLine();
        System.out.print("Enter option A: ");
        String a = sc.nextLine();
        System.out.print("Enter option B: ");
        String b = sc.nextLine();
        System.out.print("Enter option C: ");
        String c = sc.nextLine();
        System.out.print("Enter option D: ");
        String d = sc.nextLine();
        System.out.print("Enter correct option (A/B/C/D): ");
        char correct = Character.toUpperCase(sc.next().charAt(0));

        try {
            Question q = new Question(0, quizId, qText, a, b, c, d, correct);
            adminService.addQuestion(q);
            System.out.println("Question added successfully to quiz ID " + quizId + "!");
            logger.info("Question added successfully to quiz ID {}.", quizId);
        } catch (Exception e) {
            System.out.println("Failed to add question: " + e.getMessage());
            logger.error("Error adding question to quiz ID {}: {}", quizId, e.getMessage());
        }
    }


    private void viewQuestions() {
        System.out.print("Enter quiz ID: ");
        int id = sc.nextInt();

        logger.info("Admin requested to view questions for quiz ID: {}", id);

        try {
            var questions = adminService.viewQuestions(id);

            if (questions.isEmpty()) {
                System.out.println("No questions found for this quiz.");
                logger.warn("No questions found for quiz ID {}", id);
            } else {
                System.out.println("\n===== Questions for Quiz ID " + id + " =====");
                for (var q : questions) {
                    System.out.println("Q" + q.getId() + ": " + q.getQuestionText());
                    System.out.println("A) " + q.getOptionA());
                    System.out.println("B) " + q.getOptionB());
                    System.out.println("C) " + q.getOptionC());
                    System.out.println("D) " + q.getOptionD());
                    System.out.println("Correct Option: " + q.getCorrectOption());
                    System.out.println("----------------------------------");
                }
                logger.info("Displayed {} questions for quiz ID {}", questions.size(), id);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            logger.error("Failed to fetch questions for quiz ID {}: {}", id, e.getMessage());
        }
    }

}













//package com.quizapp.Controller;
//
//import com.quizapp.exception.InvalidInputException;
//import com.quizapp.exception.QuizNotFoundException;
//import com.quizapp.model.Question;
//import com.quizapp.model.User;
//import com.quizapp.service.AdminService;
//import java.util.Scanner;
//
//public class AdminController {
//
//    private final AdminService adminService = new AdminService();
//    private final Scanner sc = new Scanner(System.in);
//
//    public void adminMenu(User admin) throws InvalidInputException, QuizNotFoundException {
//        System.out.println("\n===== Welcome Admin, " + admin.getUsername() + " =====");
//
//        while (true) {
//            System.out.println("\n1. Add Quiz");
//            System.out.println("2. View All Quizzes");
//            System.out.println("3. Delete Quiz");
//            System.out.println("4. Add Question to Quiz");
//            System.out.println("5. View Questions of Quiz");
//            System.out.println("6. Logout");
//            System.out.print("Enter choice: ");
//            int choice = sc.nextInt();
//            sc.nextLine();
//
//            switch (choice) {
//                case 1 -> addQuiz();
//                case 2 -> adminService.viewQuizzes();
//                case 3 -> deleteQuiz();
//                case 4 -> addQuestion();
//                case 5 -> viewQuestions();
//                case 6 -> {
//                    System.out.println("Logged out successfully!");
//                    return;
//                }
//                default -> System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    private void addQuiz() throws InvalidInputException {
//        System.out.print("Enter quiz title: ");
//        String title = sc.nextLine();
//        System.out.print("Enter description: ");
//        String desc = sc.nextLine();
//        adminService.addQuiz(title, desc);
//    }
//
//    private void deleteQuiz() throws QuizNotFoundException {
//        System.out.print("Enter quiz ID to delete: ");
//        int id = sc.nextInt();
//        adminService.deleteQuiz(id);
//    }
//
//    private void addQuestion() throws InvalidInputException {
//        System.out.print("Enter quiz ID: ");
//        int quizId = sc.nextInt();
//        sc.nextLine();
//
//        System.out.print("Enter question text: ");
//        String qText = sc.nextLine();
//
//        System.out.print("Enter option A: ");
//        String a = sc.nextLine();
//        System.out.print("Enter option B: ");
//        String b = sc.nextLine();
//        System.out.print("Enter option C: ");
//        String c = sc.nextLine();
//        System.out.print("Enter option D: ");
//        String d = sc.nextLine();
//
//        System.out.print("Enter correct option (A/B/C/D): ");
//        char correct = Character.toUpperCase(sc.next().charAt(0));
//
//        Question q = new Question(0, quizId, qText, a, b, c, d, correct);
//        adminService.addQuestion(q);
//    }
//
//    private void viewQuestions() throws QuizNotFoundException {
//        System.out.print("Enter quiz ID: ");
//        int id = sc.nextInt();
//        adminService.viewQuestions(id);
//    }
//}
//
