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

public class GetUserAvailabilityServletTest {
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

        UserAvailability status = testDB.getUserAvailability(1);
		assertNotNull(status);
		assertEquals(1, status.userId);
		assertEquals(true, status.isAvailable);
		assertEquals(false, status.didIBlock);
		assertEquals("null", status.until);

		// check if updates are successful
		Date nextDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
		String nextDateString = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(nextDate);
		assertEquals(true, testDB.setUserAvailability(1, false, nextDateString));
		assertEquals(true, testDB.setUserAvailability(2, true, ""));
		assertEquals(false, testDB.setUserAvailability(4, false, ""));

		// check availabilities changed
		List<UserAvailability> availabilities = testDB.getAllUsers(3);
		assertEquals(2, availabilities.size());
		assertEquals(1, availabilities.get(0).userId);
		assertEquals(false, availabilities.get(0).isAvailable);
		assertEquals(2, availabilities.get(1).userId);
		assertEquals(true, availabilities.get(1).isAvailable);

		// check that the user status has changed
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        Mockito.when(request.getParameter("userId")).thenReturn("1");
        GetUserAvailabilityServlet servlet = new GetUserAvailabilityServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        String result = sw.toString();
        System.out.println(result);

		// status = testDB.getUserAvailability(1);
		// assertNotNull(status);
		// assertEquals(1, status.userId);
		// assertEquals(false, status.isAvailable);
		// assertEquals(true, status.didIBlock);
		// assertEquals(nextDateString, status.until);

        // close database for coverage
        testDB.close();
        try{
            servlet.doPost(request, response);
            fail("Should have thown an exception");
        }
        catch(Exception e){
            assertTrue(e.getMessage().contains("Failed"));
        }
    }
}