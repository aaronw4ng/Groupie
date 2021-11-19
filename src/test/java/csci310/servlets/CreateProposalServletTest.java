package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;
import csci310.Event;
import csci310.Venue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CreateProposalServletTest {
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

        // register the user and invitees
        testDB.register("TestUser", "TestPassword");
        testDB.register("Invitee1", "ps1");
        testDB.register("Invitee2", "ps2");

        Mockito.when(request.getParameter("owner")).thenReturn("TestUser");
        Mockito.when(request.getParameter("title")).thenReturn("TestTitle");
        Mockito.when(request.getParameter("descript")).thenReturn("This is a test description!");
        Mockito.when(request.getParameter("invited")).thenReturn("[\"Invitee1\", \"Invitee2\"]");
        Mockito.when(request.getParameter("events")).thenReturn("[{" +
                "\"eventName\": \"BTS PERMISSION TO DANCE ON STAGE - LA\"," +
                "\"url\": \"https://www.ticketmaster.com/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021/event/0A005B36DF5C3326\"," +
                "\"startDateTime\": \"2021-11-28T03:30:00Z\"," +
                "\"venues\": [{" +
                "\"name\": \"SoFi Stadium\"," +
                "\"address\": \"1001 S. Stadium Dr\"," +
                "\"city\": \"Inglewood\"," +
                "\"state\": \"CA\"," +
                "\"country\": \"US\"" +
                "}]" +
                "}]");
        Mockito.when(request.getParameter("isDraft")).thenReturn("false");
        Mockito.when(request.getParameter("isNew")).thenReturn("true");
        Mockito.when(request.getParameter("proposalId")).thenReturn("1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        CreateProposalServlet createProposalServlet = new CreateProposalServlet();
        createProposalServlet.init(config);
        createProposalServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("true", result);
    }

    // Updating an older version of proposal
    @Test
    public void testDoPostProposalAlreadyExists() throws Exception {
        // clear the database
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();

        // register the user and invitees
        testDB.register("TestUser", "TestPassword");
        testDB.register("Invitee1", "ps1");
        testDB.register("Invitee2", "ps2");

        // Adding the old proposal draft
        String title = "My Old Test Proposal for Create Proposal Servlet";
        String descript = "This is an old test description for delete proposal servlet!";
        List<String> invitees = new ArrayList<>();
        invitees.add("Invitee1");
        invitees.add("Invitee2");
        List<Event> events = new ArrayList<>();
        List<Venue> venues = new ArrayList<>();
        venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
        events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
        int newProposalId = testDB.savesDraftProposal("TestUser", title, descript, invitees, events, true, -1);
        assertEquals(1, newProposalId);

        // Updated version of the proposal
        Mockito.when(request.getParameter("owner")).thenReturn("TestUser");
        Mockito.when(request.getParameter("title")).thenReturn("Updated Test Title");
        Mockito.when(request.getParameter("descript")).thenReturn("This is an updated test description!");
        Mockito.when(request.getParameter("invited")).thenReturn("[\"Invitee1\", \"Invitee2\"]");
        Mockito.when(request.getParameter("events")).thenReturn("[{" +
                "\"eventName\": \"BTS PERMISSION TO DANCE ON STAGE - LA\"," +
                "\"url\": \"https://www.ticketmaster.com/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021/event/0A005B36DF5C3326\"," +
                "\"startDateTime\": \"2021-11-28T03:30:00Z\"," +
                "\"venues\": [{" +
                "\"name\": \"SoFi Stadium\"," +
                "\"address\": \"1001 S. Stadium Dr\"," +
                "\"city\": \"Inglewood\"," +
                "\"state\": \"CA\"," +
                "\"country\": \"US\"" +
                "}]" +
                "}]");
        Mockito.when(request.getParameter("isDraft")).thenReturn("false");
        Mockito.when(request.getParameter("isNew")).thenReturn("false");
        Mockito.when(request.getParameter("proposalId")).thenReturn("1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        CreateProposalServlet createProposalServlet = new CreateProposalServlet();
        createProposalServlet.init(config);
        createProposalServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("true", result);
    }

    @Test
    public void testDoPostOwnerDoesNotExist() throws Exception {
        // clear the database
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();

        // register the invitees only
        testDB.register("Invitee1", "ps1");
        testDB.register("Invitee2", "ps2");

        Mockito.when(request.getParameter("owner")).thenReturn("TestUser");
        Mockito.when(request.getParameter("title")).thenReturn("TestTitle");
        Mockito.when(request.getParameter("descript")).thenReturn("This is a test description!");
        Mockito.when(request.getParameter("invited")).thenReturn("[\"Invitee1\", \"Invitee2\"]");
        Mockito.when(request.getParameter("events")).thenReturn("[{" +
                "\"eventName\": \"BTS PERMISSION TO DANCE ON STAGE - LA\"," +
                "\"url\": \"https://www.ticketmaster.com/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021/event/0A005B36DF5C3326\"," +
                "\"startDateTime\": \"2021-11-28T03:30:00Z\"," +
                "\"venues\": [{" +
                "\"name\": \"SoFi Stadium\"," +
                "\"address\": \"1001 S. Stadium Dr\"," +
                "\"city\": \"Inglewood\"," +
                "\"state\": \"CA\"," +
                "\"country\": \"US\"" +
                "}]" +
                "}]");
        Mockito.when(request.getParameter("isDraft")).thenReturn("false");
        Mockito.when(request.getParameter("isNew")).thenReturn("true");
        Mockito.when(request.getParameter("proposalId")).thenReturn("1");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);

        CreateProposalServlet createProposalServlet = new CreateProposalServlet();
        createProposalServlet.init(config);
        createProposalServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("false", result);
    }

    @Test
    public void testDoPostException() throws Exception {
        HttpServletResponse failingResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest failingRequest = Mockito.mock(HttpServletRequest.class);
        CreateProposalServlet createProposalServlet = new CreateProposalServlet();
        createProposalServlet.init(config);

        Mockito.when(failingResponse.getWriter()).thenThrow(IOException.class);

        try {
            createProposalServlet.doPost(failingRequest, failingResponse);
            fail("Expected a Servlet Exception to be thrown");
        } catch (ServletException servletException) {
            assertEquals("Create Proposal Servlet failed", servletException.getMessage());
        }
    }
}