package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;
import csci310.Event;
import csci310.Venue;

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
import java.util.ArrayList;
import java.util.List;

public class GetAllNonDraftProposalsServletTest {
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

        // Create a proposal first
		// add user to database first
		testDB.register("Test User", "Test Password"); // userId = 1
		String title = "Remove Invitee from Sent Proposal";
		String descript = "This is a test description for removing an invitee after sending proposal test!";
		List<String> invitees = new ArrayList<>();
		invitees.add("Invitee 1");
		invitees.add("Invitee 2");
		// add invitees as users
		testDB.register("Invitee 1", "PS1"); // userId = 2
		testDB.register("Invitee 2", "PS2"); // userId = 3
		List<Venue> venues1 = new ArrayList<>();
		venues1.add(new Venue("birthdayVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Venue> venues2 = new ArrayList<>();
		venues2.add(new Venue("BTSConcertVenue", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
		List<Event> events = new ArrayList<>();
		events.add(new Event("Birthday", "TestURL", "TestStartDate", venues1));
		events.add(new Event("BTS Concert", "TestURL", "TestStartDate", venues2));
		int newProposalId = testDB.savesDraftProposal("Test User", title, descript, invitees, events, true, -1);
		assertEquals(1, newProposalId);

        // Send the proposal
		Boolean sentStatus = testDB.sendProposal(newProposalId);
		assertEquals(true, sentStatus);

        // check response
        Mockito.when(request.getParameter("userId")).thenReturn("1");
        Mockito.when(request.getParameter("isOwner")).thenReturn("true");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        GetAllNonDraftProposalsServlet servlet = new GetAllNonDraftProposalsServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        System.out.println(result);
        assertTrue(result.contains("birthdayVenue"));
        assertTrue(result.contains("BTSConcertVenue"));
        assertTrue(result.contains("invitee 1"));
        assertTrue(result.contains("invitee 2"));

        // check empty response
        Mockito.when(request.getParameter("userId")).thenReturn("2");
        Mockito.when(request.getParameter("isOwner")).thenReturn("true");
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        servlet = new GetAllNonDraftProposalsServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        result = sw.getBuffer().toString();
        System.out.println(result);
        assertTrue(result.equals("[]"));

        // check empty response
        Mockito.when(request.getParameter("userId")).thenReturn("1");
        Mockito.when(request.getParameter("isOwner")).thenReturn("false");
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        servlet = new GetAllNonDraftProposalsServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        result = sw.getBuffer().toString();
        System.out.println(result);
        assertTrue(result.equals("[]"));

        // check response
        Mockito.when(request.getParameter("userId")).thenReturn("2");
        Mockito.when(request.getParameter("isOwner")).thenReturn("false");
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        servlet = new GetAllNonDraftProposalsServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        result = sw.getBuffer().toString();
        System.out.println(result);
        assertTrue(result.contains("birthdayVenue"));
        assertTrue(result.contains("BTSConcertVenue"));
        assertTrue(result.contains("invitee 1"));
        assertTrue(result.contains("invitee 2"));

        // close database for full coverage
        testDB.close();
        try{
            servlet.doPost(request, response);
            fail("Should have thrown an exception");
        }
        catch(Exception e){
            assertTrue(e.getMessage().contains("failed"));
        }
    }
}
