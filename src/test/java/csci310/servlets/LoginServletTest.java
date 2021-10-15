package csci310.servlets;

import csci310.Database;
import csci310.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class LoginServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    User user;

    @Before
    public void setUp() throws Exception {
        user = new User("User");
        user.setUsername("test");
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void testDoGet() throws Exception {
        Database testDB = new Database();
        testDB.dropAllTables();
        testDB.createRequiredTables();
        testDB.register("TestUser", "TestPassword");
        testDB.close();

        Mockito.when(request.getParameter("input-username")).thenReturn("TestUser");
        Mockito.when(request.getParameter("input-password")).thenReturn("TestPassword");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("true", result);
    }

    @Test
    public void testDoGetUserDoesNotExist() throws Exception {
        Mockito.when(request.getParameter("input-username")).thenReturn("TestUse2");
        Mockito.when(request.getParameter("input-password")).thenReturn("TestPassword2");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("false", result);
    }

    @Test
    public void testDoGetException() throws Exception {
        HttpServletResponse failingResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest failingRequest = Mockito.mock(HttpServletRequest.class);
        LoginServlet loginServlet = new LoginServlet();

        Mockito.when(failingResponse.getWriter()).thenThrow(IOException.class);

        try {
            loginServlet.doGet(failingRequest, failingResponse);
            fail("Expected a Servlet Exception to be thrown");
        } catch (ServletException servletException) {
            assertEquals("Login Servlet failed", servletException.getMessage());
        }
    }
}