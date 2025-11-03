package com.quizapp.util;


import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DBUtilTest {

    private Connection connection;

    @Test
    void testDatabaseConnectionNotNull() {
        Connection conn = DBUtil.getConnection();
        assertNotNull(conn, "Database connection should be established");
        DBUtil.closeConnection(connection);
    }

    @Test
    void testCloseConnection() {
        Connection conn = DBUtil.getConnection();
        assertDoesNotThrow(() -> DBUtil.closeConnection(connection)); //
    }

}
