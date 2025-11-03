package com.quizapp.Controller;

import com.quizapp.exception.InvalidInputException;
import com.quizapp.exception.QuizNotFoundException;
import com.quizapp.model.User;
import com.quizapp.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final Scanner sc = new Scanner(System.in);
    private static final AuthService authService = new AuthService();

    public static void main(String[] args) throws InvalidInputException, QuizNotFoundException {
        logger.info("Quiz Application started.");
        System.out.println("===== Welcome to Quiz Application =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> {
                    logger.info("Application exited by user.");
                    System.out.println("Exiting... Goodbye!");
                    System.exit(0);
                }
                default -> {
                    logger.warn("Invalid main menu choice: {}", choice);
                    System.out.println("Invalid choice!");
                }
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        logger.info("Attempting registration for user: {}", username);

        boolean success = authService.register(username, password, "");

        if (success) {
            System.out.println(" User registered successfully!");
            logger.info("User '{}' registered successfully.", username);
        } else {
            System.out.println(" Registration failed. Try again.");
            logger.warn("Registration failed for user '{}'.", username);
        }


    }

    private static void login() throws InvalidInputException, QuizNotFoundException {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        logger.info("Login attempt for username: {}", username);
        User user = authService.login(username, password);
        if (user != null) {
            if (user.getRole().equalsIgnoreCase("admin")) {
                logger.info("Admin '{}' logged in successfully.", username);
                new AdminController().adminMenu(user);
            } else {
                logger.info("User '{}' logged in successfully.", username);
                new UserController().userMenu(user);
            }
        } else {
            logger.warn("Login failed for username: {}", username);
        }
    }
}



















//package com.quizapp.Controller;
//
//
//import com.quizapp.exception.InvalidInputException;
//import com.quizapp.exception.QuizNotFoundException;
//import com.quizapp.model.User;
//import com.quizapp.service.AuthService;
//import java.util.Scanner;
//
//public class MainController {
//
//    private static final Scanner sc = new Scanner(System.in);
//    private static final AuthService authService = new AuthService();
//
//    public static void main(String[] args) throws InvalidInputException, QuizNotFoundException {
//        System.out.println("===== Welcome to Quiz Application =====");
//
//        while (true) {
//            System.out.println("\n1. Login");
//            System.out.println("2. Register");
//            System.out.println("3. Exit");
//            System.out.print("Enter choice: ");
//            int choice = sc.nextInt();
//            sc.nextLine(); // consume newline
//
//            switch (choice) {
//                case 1 -> login();
//                case 2 -> register();
//                case 3 -> {
//                    System.out.println("Exiting... Goodbye!");
//                    System.exit(0);
//                }
//                default -> System.out.println("Invalid choice!");
//            }
//        }
//    }
//
//    private static void register() {
//        System.out.print("Enter username: ");
//        String username = sc.nextLine();
//        System.out.print("Enter password: ");
//        String password = sc.nextLine();
////        System.out.print("Enter role (admin/user): ");
////        String role = sc.nextLine();
//
//        authService.register(username, password, "" );
//    }
//
//    private static void login() throws InvalidInputException, QuizNotFoundException {
//        System.out.print("Enter username: ");
//        String username = sc.nextLine();
//        System.out.print("Enter password: ");
//        String password = sc.nextLine();
//
//        User user = authService.login(username, password);
//        if (user != null) {
//            if (user.getRole().equalsIgnoreCase("admin")) {
//                new AdminController().adminMenu(user);
//            } else {
//                new UserController().userMenu(user);
//            }
//        }
//    }
//}
