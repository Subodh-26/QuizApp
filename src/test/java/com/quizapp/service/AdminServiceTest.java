package com.quizapp.service;

import com.quizapp.dao.QuizDao;
import com.quizapp.dao.QuestionDao;
import com.quizapp.exception.InvalidInputException;
import com.quizapp.exception.QuizNotFoundException;
import com.quizapp.model.Question;
import com.quizapp.model.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminServiceTest {

    private AdminService adminService;
    private QuizDao quizDaoMock;
    private QuestionDao questionDaoMock;

    @BeforeEach
    void setup() {
        quizDaoMock = Mockito.mock(QuizDao.class);
        questionDaoMock = Mockito.mock(QuestionDao.class);
        adminService = new AdminService() {
            {
                // override DAOs for testing
                try {
                    var quizDaoField = AdminService.class.getDeclaredField("quizDao");
                    quizDaoField.setAccessible(true);
                    quizDaoField.set(this, quizDaoMock);

                    var questionDaoField = AdminService.class.getDeclaredField("questionDao");
                    questionDaoField.setAccessible(true);
                    questionDaoField.set(this, questionDaoMock);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Test
    void testAddQuizThrowsExceptionForEmptyTitle() {
        assertThrows(InvalidInputException.class, () -> adminService.addQuiz("", "desc"));
    }

    @Test
    void testViewQuizzesReturnsList() {
        when(quizDaoMock.getAllQuizzes()).thenReturn(List.of(new Quiz(1, "Java Quiz", "Basics")));
        List<Quiz> quizzes = adminService.viewQuizzes();
        assertEquals(1, quizzes.size());
    }

    @Test
    void testDeleteQuizThrowsWhenNotFound() throws QuizNotFoundException {
        when(quizDaoMock.getQuizById(99)).thenReturn(null);
        assertThrows(QuizNotFoundException.class, () -> adminService.deleteQuiz(99));
    }

    @Test
    void testAddQuestionThrowsForEmptyText() {
        Question q = new Question(0, 1, "", "A", "B", "C", "D", 'A');
        assertThrows(InvalidInputException.class, () -> adminService.addQuestion(q));
    }

    @Test
    void testViewQuestionsThrowsWhenEmpty() {
        when(questionDaoMock.getQuestionsByQuizId(1)).thenReturn(Collections.emptyList());
        assertThrows(QuizNotFoundException.class, () -> adminService.viewQuestions(1));
    }
}
