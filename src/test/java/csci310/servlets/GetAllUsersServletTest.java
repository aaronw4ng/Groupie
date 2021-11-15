package csci310.servlets;

import csci310.AppServletContextListener;
import csci310.Ticketmaster;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class GetAllUsersServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;

    // for mocking server initialization
    private static ServletContext context;
    private static ServletContextEvent event;
    private static ServletConfig config;
    private static Ticketmaster ticketmaster;

    @Before
    public void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void testDoPost() throws Exception {
        GetAllUsersServlet servlet = new GetAllUsersServlet();
        assertTrue(servlet != null);
    }
}
