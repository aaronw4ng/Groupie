package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;
import csci310.UserAvailability;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SetAvailabilityServletTest {
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
    public void testDoPost() throws Exception {
        // clear the database
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();
        
        // register users
        testDB.register("user1", "ps1");
        testDB.register("user2", "ps2");
        testDB.register("user3", "ps3");

        List<UserAvailability> availabilities = testDB.getAllUsers(3);
		assertEquals(true, availabilities.get(0).isAvailable);
		assertEquals(true, availabilities.get(1).isAvailable);

        // user1 sets unavailable for 1 hour
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.doReturn(pw).when(response).getWriter();
        Mockito.doReturn("1").when(request).getParameter("userId");
        Mockito.doReturn("false").when(request).getParameter("availability");
        Mockito.doReturn("1").when(request).getParameter("hours");
        SetAvailabilityServlet servlet = new SetAvailabilityServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        availabilities = testDB.getAllUsers(3);
        assertEquals(false, availabilities.get(0).isAvailable);
		assertEquals(true, availabilities.get(1).isAvailable);
        String result = sw.getBuffer().toString();
        assertTrue(result.contains("true"));
        
        // user1 sets available again
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        Mockito.doReturn(pw).when(response).getWriter();
        Mockito.doReturn("1").when(request).getParameter("userId");
        Mockito.doReturn("true").when(request).getParameter("availability");
        Mockito.doReturn("").when(request).getParameter("hours");
        servlet = new SetAvailabilityServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        availabilities = testDB.getAllUsers(3);
        assertEquals(true, availabilities.get(0).isAvailable);
        assertEquals(true, availabilities.get(1).isAvailable);
        result = sw.getBuffer().toString();
        assertTrue(result.contains("true"));

        // user2 sets unavailable indefinitely
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        Mockito.doReturn(pw).when(response).getWriter();
        Mockito.doReturn("2").when(request).getParameter("userId");
        Mockito.doReturn("false").when(request).getParameter("availability");
        Mockito.doReturn("-1").when(request).getParameter("hours");
        servlet = new SetAvailabilityServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        availabilities = testDB.getAllUsers(3);
        assertEquals(true, availabilities.get(0).isAvailable);
        assertEquals(false, availabilities.get(1).isAvailable);
        result = sw.getBuffer().toString();
        assertTrue(result.contains("true"));

        // try to set availability for non-existing user
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        Mockito.doReturn(pw).when(response).getWriter();
        Mockito.doReturn("999").when(request).getParameter("userId");
        Mockito.doReturn("true").when(request).getParameter("availability");
        Mockito.doReturn("").when(request).getParameter("hours");
        servlet = new SetAvailabilityServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        result = sw.getBuffer().toString();
        assertTrue(result.contains("false"));

        // close database for coverage
        testDB.close();
        try{
            servlet.doPost(request, response);
            fail("Should have thown an exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Failed"));
        }
    }
}
