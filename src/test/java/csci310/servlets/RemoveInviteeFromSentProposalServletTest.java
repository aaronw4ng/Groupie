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

public class RemoveInviteeFromSentProposalServletTest {
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

        testDB.register("TestUser", "TestPassword");
        testDB.register("Invitee1", "ps1");
        testDB.register("Invitee2", "ps2");

        // Adding the proposal draft
        String title = "My Old Test Proposal for Remove Invitee from Sent Proposal Servlet";
        String descript = "This is a test description for remove invitee from sent proposal servlet!";
        List<String> invitees = new ArrayList<>();
        invitees.add("Invitee1"); // user Id is 2
        invitees.add("Invitee2"); // user Id is 3
        List<Event> events = new ArrayList<>();
        List<Venue> venues = new ArrayList<>();
        venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
        events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
        int newProposalId = testDB.savesDraftProposal("TestUser", title, descript, invitees, events, true, -1);
        assertEquals(1, newProposalId);

        // send the proposal
        boolean status = testDB.sendProposal(1);
        assertEquals(true, status);


        // Remove invitee 1 from sent proposal
        Mockito.when(request.getParameter("proposalId")).thenReturn("1");
        Mockito.when(request.getParameter("userId")).thenReturn("2");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        RemoveInviteeFromSentProposalServlet removeInviteeFromSentProposalServlet = new RemoveInviteeFromSentProposalServlet();
        removeInviteeFromSentProposalServlet.init(config);
        removeInviteeFromSentProposalServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("true", result);
    }

    @Test
    public void testDoPostUserWasNotInvited() throws Exception {
        // clear the database
        Database testDB = listener.database;
        testDB.dropAllTables();
        testDB.createRequiredTables();

        // register the user and invitees
        testDB.register("TestUser", "TestPassword");
        testDB.register("Invitee1", "ps1");
        testDB.register("Invitee2", "ps2");

        testDB.register("TestUser", "TestPassword");
        testDB.register("Invitee1", "ps1");
        testDB.register("Invitee2", "ps2");

        // Adding the proposal draft
        String title = "My Old Test Proposal for Remove Invitee from Sent Proposal Servlet";
        String descript = "This is a test description for remove invitee from sent proposal servlet!";
        List<String> invitees = new ArrayList<>();
        invitees.add("Invitee1"); // user Id is 2
        invitees.add("Invitee2"); // user Id is 3
        List<Event> events = new ArrayList<>();
        List<Venue> venues = new ArrayList<>();
        venues.add(new Venue("VenueName", "VenueAddress", "VenueCity", "VenueState", "VenueCountry"));
        events.add(new Event("TestEvent", "TestURL", "TestStartDate", venues));
        int newProposalId = testDB.savesDraftProposal("TestUser", title, descript, invitees, events, true, -1);
        assertEquals(1, newProposalId);

        // send the proposal
        boolean status = testDB.sendProposal(1);
        assertEquals(true, status);


        // Remove user that wasn't invited at all from sent proposal
        Mockito.when(request.getParameter("proposalId")).thenReturn("1");
        Mockito.when(request.getParameter("userId")).thenReturn("5");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.when(response.getWriter()).thenReturn(pw);
        RemoveInviteeFromSentProposalServlet removeInviteeFromSentProposalServlet = new RemoveInviteeFromSentProposalServlet();
        removeInviteeFromSentProposalServlet.init(config);
        removeInviteeFromSentProposalServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("false", result);
    }

    @Test
    public void testDoPostException() throws Exception {
        HttpServletResponse failingResponse = Mockito.mock(HttpServletResponse.class);
        HttpServletRequest failingRequest = Mockito.mock(HttpServletRequest.class);
        RemoveInviteeFromSentProposalServlet removeInviteeFromSentProposalServlet = new RemoveInviteeFromSentProposalServlet();
        removeInviteeFromSentProposalServlet.init(config);

        Mockito.when(failingResponse.getWriter()).thenThrow(IOException.class);

        try {
            removeInviteeFromSentProposalServlet.doPost(failingRequest, failingResponse);
            fail("Expected a Servlet Exception to be thrown");
        } catch (ServletException servletException) {
            assertEquals("Remove Invitee from Sent Proposal Servlet failed", servletException.getMessage());
        }
    }
}