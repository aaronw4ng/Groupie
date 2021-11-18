package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;
import csci310.User;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletConfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class RegisterServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    
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
        listener = new AppServletContextListener();
        event = new ServletContextEvent(context);
        config = Mockito.mock(ServletConfig.class);
        listener.contextInitialized(event);
        Mockito.doReturn(listener.database).when(context).getAttribute("database");
        Mockito.doReturn(context).when(config).getServletContext();
    }

    @Before
    public void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        listener.contextDestroyed(event);
    }

    @Test
    public void testDoGet() throws Exception {
        // clear the database
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();

        Mockito.when(request.getParameter("username")).thenReturn("TestUser");
        Mockito.when(request.getParameter("password")).thenReturn("TestPassword");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        RegisterServlet registerServlet = new RegisterServlet();
        registerServlet.init(config);
        registerServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        // should be true since username has not yet been used and also only user in the database
        assertEquals("1", result);
    }

    @Test
    public void testDoGetUserAlreadyExists() throws Exception {
        // clear the database
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();
        User existingUser = new User("existingUser");
        existingUser.setPassword("existingPassword");
        // add that user that we will try to register again
        testDB.register(existingUser.getUsername(), existingUser.getPassword());

        Mockito.when(request.getParameter("username")).thenReturn("existingUser");
        Mockito.when(request.getParameter("password")).thenReturn("existingPassword");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        RegisterServlet registerServlet = new RegisterServlet();
        registerServlet.init(config);
        registerServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        // should be false because user already exists
        assertEquals("-1", result);
    }

    @Test
    public void testDoGetException() throws Exception {
        HttpServletResponse failingResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest failingRequest = Mockito.mock(HttpServletRequest.class);
        RegisterServlet registerServlet = new RegisterServlet();
        registerServlet.init(config);

        Mockito.when(failingResponse.getWriter()).thenThrow(IOException.class);

        try {
            registerServlet.doPost(failingRequest, failingResponse);
            fail("Expected a Servlet Exception to be thrown");
        } catch (ServletException servletException) {
            assertEquals("Register Servlet failed", servletException.getMessage());
        }
    }
}