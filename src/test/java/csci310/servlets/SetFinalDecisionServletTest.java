package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Database;
import csci310.Event;
import csci310.Venue;
import csci310.Proposal;

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

public class SetFinalDecisionServletTest {
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
		String title = "Test Indicate Responses";
		String descript = "This is a test description for indicating responses to events";
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

		// check that the responses are not filled out
		List<Proposal> proposals = testDB.getAllNonDraftProposals(2, false);
		assertNotNull(proposals);
		assertEquals(1, proposals.size());
		assertEquals(proposals.get(0).events.get(0).responses.size(), 3);
		assertEquals(proposals.get(0).events.get(0).responses.get(0).isFilledOut, false);
		assertEquals(proposals.get(0).events.get(0).responses.get(1).isFilledOut, false);
		assertEquals(proposals.get(0).events.get(0).responses.get(2).isFilledOut, false);

		// now the users should be able to indicate responses
		testDB.indicateResponse(1, 1, 2, "yes", 4);
		proposals = testDB.getAllNonDraftProposals(2, false);
		assertNotNull(proposals);
		assertEquals(1, proposals.size());
		assertEquals(proposals.get(0).events.get(0).responses.size(), 3);
		assertEquals(proposals.get(0).events.get(0).responses.get(0).isFilledOut, true);
		assertEquals(proposals.get(0).events.get(0).responses.get(0).availability, "yes");
		assertEquals(proposals.get(0).events.get(0).responses.get(0).excitement, 4);
		assertEquals(proposals.get(0).events.get(0).responses.get(0).userId, 2);

		// test that when all responses are filled out, begin next phase
		assertTrue(testDB.indicateResponse(1, 1, 3, "yes", 4));
		assertTrue(testDB.indicateResponse(1, 1, 1, "yes", 4));
		assertTrue(testDB.indicateResponse(1, 2, 1, "yes", 2));
		assertTrue(testDB.indicateResponse(1, 2, 2, "yes", 4));
		assertTrue(testDB.indicateResponse(1, 2, 3, "yes", 4));
		proposals = testDB.getAllNonDraftProposals(2, false);

		// expect the have both events pass for availability filtering, but only event 1
		// passes for excitement filtering as the final best event
		assertEquals(1, proposals.get(0).bestEventId);
		assertEquals("Birthday", proposals.get(0).bestEvent.eventName);
		assertEquals(proposals.get(0).isFinalized, true);
		assertEquals(proposals.get(0).isDraft, false);

		// test when users accept or reject proposals
		// assertTrue(testDB.setFinalDecision(2, 1, true));
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Mockito.doReturn(pw).when(response).getWriter();
        Mockito.doReturn("2").when(request).getParameter("userId");
        Mockito.doReturn("1").when(request).getParameter("proposalId");
        Mockito.doReturn("true").when(request).getParameter("accept");
        SetFinalDecisionServlet servlet = new SetFinalDecisionServlet();
        servlet.init(config);
        servlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        assertEquals("true", result);

		proposals = testDB.getAllNonDraftProposals(2, false);
		assertEquals(proposals.get(0).accepted.size(), 1);
		assertEquals(proposals.get(0).declined.size(), 0);
		assertEquals(proposals.get(0).notResponded.size(), 2);

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
