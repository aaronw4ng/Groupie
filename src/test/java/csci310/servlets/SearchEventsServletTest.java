package csci310.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

public class SearchEventsServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void testDoPost() throws Exception {
        Mockito.when(request.getParameter("keyword")).thenReturn("BTS");
        Mockito.when(request.getParameter("zipCode")).thenReturn("90301");
        Mockito.when(request.getParameter("city")).thenReturn("");
        Mockito.when(request.getParameter("startDate")).thenReturn("");
        Mockito.when(request.getParameter("endDate")).thenReturn("");
        Mockito.when(request.getParameter("genre")).thenReturn("");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);
        SearchEventsServlet searchEventsServlet = new SearchEventsServlet();
        searchEventsServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        System.out.println(result);
        assertTrue(result.contains("BTS"));
    }

    @Test
    public void testDoPostNoEventsFound() throws Exception {
        Mockito.when(request.getParameter("keyword")).thenReturn("asdfg");
        Mockito.when(request.getParameter("zipCode")).thenReturn("");
        Mockito.when(request.getParameter("city")).thenReturn("");
        Mockito.when(request.getParameter("startDate")).thenReturn("");
        Mockito.when(request.getParameter("endDate")).thenReturn("");
        Mockito.when(request.getParameter("genre")).thenReturn("");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);
        SearchEventsServlet searchEventsServlet = new SearchEventsServlet();
        searchEventsServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        System.out.println(result);
        assertEquals("No results found!", result);
    }


}