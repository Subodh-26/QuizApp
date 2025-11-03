package com.quizapp.dao;

import com.quizapp.model.Question;
import com.quizapp.util.DBUtil;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QuestionDaoTest {

    private static QuestionDao questionDao;
    private static Connection connection;

    @BeforeAll
    static void setup() {
        questionDao = new QuestionDao();
        connection = DBUtil.getConnection();
        assertNotNull(connection);
    }

    @Test
    @Order(1)
    void testAddQuestion() {
        Question question = new Question(0, 1, "What is JUnit?", "A", "B", "C", "D", 'A');
        questionDao.addQuestion(question);
    }

    @Test
    @Order(2)
    void testGetQuestionsByQuizId() {
        List<Question> questions = questionDao.getQuestionsByQuizId(1);
        assertNotNull(questions);
    }

    @AfterAll
    static void tearDown() {
        DBUtil.closeConnection(connection);
    }
}
