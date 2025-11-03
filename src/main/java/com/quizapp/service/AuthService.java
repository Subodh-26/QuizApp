package com.quizapp.service;

import com.quizapp.dao.UserDao;
import com.quizapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserDao userDao = new UserDao();

    public boolean register(String username, String password, String role) {
        logger.info("Registering new user: {}", username);
        try {
            userDao.registerUser(username, password, "USER");
            logger.info("User '{}' registered successfully", username);
            return true;
        } catch (Exception e) {
            logger.error("Error registering user '{}': {}", username, e.getMessage());
            return false;
        }
    }

    public User login(String username, String password) {
        logger.info("User '{}' attempting login", username);
        User user = userDao.login(username, password);

        if (user != null) {
            logger.info("Login successful for user '{}'", username);
            System.out.println("Login successful! Welcome " + user.getUsername());
        } else {
            logger.warn("Invalid login attempt for username '{}'", username);
            System.out.println("Invalid credentials! Try again.");
        }

        return user;
    }
}





















//package com.quizapp.service;
//
//
//import com.quizapp.dao.UserDao;
//import com.quizapp.model.User;
//
//public class AuthService {
//
//    private UserDao userDao = new UserDao();
//
//    // Register user
//    public void register(String username, String password, String role) {
//        userDao.registerUser(username, password, "USER");
//    }
//
//    // Login user
//    public User login(String username, String password) {
//        User user = userDao.login(username, password);
//        if (user != null) {
//            System.out.println("Login successful! Welcome " + user.getUsername());
//        } else {
//            System.out.println("Invalid credentials! Try again.");
//        }
//        return user;
//    }
//}
