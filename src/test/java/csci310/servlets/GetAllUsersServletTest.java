package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;

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
import java.util.concurrent.TimeUnit;

public class GetAllUsersServletTest {
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

        Date currentDate = new Date(System.currentTimeMillis());
		String currentDateString = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(currentDate);
		// set user1 to unavailable until now
		testDB.setUserAvailability(1, false, currentDateString);

		Date nextDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
		String nextDateString = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(nextDate);
		// set user2 to unavailable until next day
		testDB.setUserAvailability(2, false, nextDateString);

        Mockito.when(request.getParameter("userId")).thenReturn("3");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);
        GetAllUsersServlet servlet = new GetAllUsersServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        System.out.println(result);

        // test that the response is correct
        assertTrue(result.contains("user1"));
        assertTrue(result.contains("user2"));
        assertFalse(result.contains("user3"));
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertFalse(result.contains("3"));
        assertTrue(result.contains("true"));
        assertTrue(result.contains("false"));
    }
}
