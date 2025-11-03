package com.quizapp.dao;

import com.quizapp.model.Quiz;
import com.quizapp.util.DBUtil;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuizDaoTest {

    private static QuizDao quizDao;
    private static Connection connection;


    @BeforeAll
    static void setup() {
        quizDao = new QuizDao();
        Connection conn = DBUtil.getConnection();
        assertNotNull(conn, "Database connection should not be null");
    }

    @Test
    @Order(1)
    void testAddQuiz() {
        quizDao.addQuiz("JUnit Quiz", "Testing basics");
    }

    @Test
    @Order(2)
    void testGetAllQuizzes() {
        List<Quiz> quizzes = quizDao.getAllQuizzes();
        assertNotNull(quizzes);
    }

    @AfterAll
    static void tearDown() {
        DBUtil.closeConnection(connection);
    }

}
