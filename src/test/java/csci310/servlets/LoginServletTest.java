package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;
import csci310.User;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletConfig;

import org.mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class LoginServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    User user;

    // for mocking server initialization
    private static ServletContext context;
    private static AppServletContextListener listener;
    private static ServletContextEvent event;
    private static ServletConfig config;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // database initialization
        AppServletContextListener.db_name = "test.db";
        context = Mockito.mock(ServletContext.class);
        config = Mockito.mock(ServletConfig.class);
        listener = new AppServletContextListener();
        event = new ServletContextEvent(context);
        listener.contextInitialized(event);
        Mockito.doReturn(listener.database).when(context).getAttribute("database");
        Mockito.doReturn(context).when(config).getServletContext();
    }

    @Before
    public void setUp() throws Exception {
        user = new User("User");
        user.setUsername("test");
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        listener.contextDestroyed(event);
    } 
    
    @Test
    public void testDoPost() throws Exception {
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();
        testDB.register("TestUser", "TestPassword");

        Mockito.when(request.getParameter("username")).thenReturn("TestUser");
        Mockito.when(request.getParameter("password")).thenReturn("TestPassword");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.init(config);
        loginServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("1", result);
    }

    @Test
    public void testDoPostUserDoesNotExist() throws Exception {
        Mockito.when(request.getParameter("username")).thenReturn("TestUse2");
        Mockito.when(request.getParameter("password")).thenReturn("TestPassword2");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.init(config);
        loginServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("-1", result);
    }

    @Test
    public void testDoPostException() throws Exception {
        HttpServletResponse failingResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest failingRequest = Mockito.mock(HttpServletRequest.class);
        LoginServlet loginServlet = new LoginServlet();
        loginServlet.init(config);

        Mockito.when(failingResponse.getWriter()).thenThrow(IOException.class);

        try {
            loginServlet.doPost(failingRequest, failingResponse);
            fail("Expected a Servlet Exception to be thrown");
        } catch (ServletException servletException) {
            assertEquals("Login Servlet failed", servletException.getMessage());
        }
    }
}