package com.quizapp.service;

import com.quizapp.dao.UserDao;
import com.quizapp.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    private AuthService authService;
    private UserDao userDaoMock;

    @BeforeEach
    void setup() {
        userDaoMock = mock(UserDao.class);

        // Inject the mock UserDao using reflection since your class creates it directly
        authService = new AuthService();
        try {
            var field = AuthService.class.getDeclaredField("userDao");
            field.setAccessible(true);
            field.set(authService, userDaoMock);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject mock UserDao", e);
        }
    }

    @Test
    void testRegisterUser_Success() {
        doNothing().when(userDaoMock).registerUser("subodh", "1234", "USER");

        assertDoesNotThrow(() -> authService.register("subodh", "1234", "USER"));
        verify(userDaoMock, times(1)).registerUser("subodh", "1234", "USER");
    }

    @Test
    void testRegisterUser_ExceptionHandled() {
        doThrow(new RuntimeException("DB Error")).when(userDaoMock).registerUser("test", "123", "USER");

        assertDoesNotThrow(() -> authService.register("test", "123", "USER"),
                "Exception should be caught and logged inside register()");
        verify(userDaoMock, times(1)).registerUser("test", "123", "USER");
    }

    @Test
    void testLogin_Success() {
        User mockUser = new User();
        mockUser.setUsername("subodh");
        mockUser.setPassword("1234");

        when(userDaoMock.login("subodh", "1234")).thenReturn(mockUser);

        User result = authService.login("subodh", "1234");

        assertNotNull(result);
        assertEquals("subodh", result.getUsername());
        verify(userDaoMock, times(1)).login("subodh", "1234");
    }

    @Test
    void testLogin_Failure() {
        when(userDaoMock.login("wrong", "0000")).thenReturn(null);

        User result = authService.login("wrong", "0000");

        assertNull(result);
        verify(userDaoMock, times(1)).login("wrong", "0000");
    }
}
