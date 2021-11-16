package csci310.servlets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import csci310.AppServletContextListener;
import csci310.Ticketmaster;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;

import static org.junit.Assert.*;

public class SearchEventsServletTest {
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

        // mocked ticketmaster initialization
        context = Mockito.mock(ServletContext.class);
        config = Mockito.mock(ServletConfig.class);
        event = new ServletContextEvent(context);
        ticketmaster = Mockito.spy(new Ticketmaster());
        Mockito.doReturn(ticketmaster).when(context).getAttribute("ticketmaster");
        Mockito.doReturn(context).when(config).getServletContext();

        // load ticketmaster saved mock data
        // import mock data from directory "misc"
        File f = new File("./misc");
        String[] fileList = f.list();
        for (String pathname:fileList){
            if (pathname.contains(".json")){
                mockFileImport(pathname.substring(0, pathname.length()-5));
                // System.out.println(pathname.substring(0, pathname.length()-5));
            }
        }
    }

    // helper function for testing
    public String getFileString(String filename){
        String fileString = "";
        try {
            Path filePath = Path.of("./misc/" + filename);
            // System.out.println(filePath);
            fileString = Files.readString(filePath);
        } catch (Exception e) {
            System.out.println("File not found");
        }
        return fileString;
    }

    // helper function for testing
    public void writeFile(String filename, String content){
        try {
            Path filePath = Path.of("./misc/" + filename);
            // System.out.println(filePath);
            Files.writeString(filePath, content);
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    public void mockFileImport(String filename)throws Exception{
        Mockito.doReturn(getFileString(filename+".json")).when(ticketmaster).getSearchResult(getFileString(filename+".url"));
        // System.out.println("-----------" + filename + "-----------");
    }

    public void saveMockData(String filename, String content, String url)throws Exception{
        writeFile(filename+".json", content);
        writeFile(filename+".url", url);
    }

    @Test
    public void testDoPost() throws Exception {
        Mockito.when(request.getParameter("keyword")).thenReturn("BTS");
        Mockito.when(request.getParameter("zipCode")).thenReturn("90301");
        Mockito.when(request.getParameter("city")).thenReturn("");
        Mockito.when(request.getParameter("startDate")).thenReturn("");
        Mockito.when(request.getParameter("endDate")).thenReturn("");
        Mockito.when(request.getParameter("genre")).thenReturn("");

        // save mock data
        String host = ticketmaster.buildHostString("BTS", "90301", "", "", "", "");
        saveMockData("searchEventsTestDoPost", ticketmaster.getSearchResult(host), host);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);
        SearchEventsServlet searchEventsServlet = new SearchEventsServlet();
        searchEventsServlet.init(config);
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

        // save mock data
        String host = ticketmaster.buildHostString("asdfg", "", "", "", "", "");
        saveMockData("searchEventsTestDoPostNoEventsFound", ticketmaster.getSearchResult(host), host);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        Mockito.when(response.getWriter()).thenReturn(pw);
        SearchEventsServlet searchEventsServlet = new SearchEventsServlet();
        searchEventsServlet.init(config);
        searchEventsServlet.doPost(request, response);
        String result = sw.getBuffer().toString();
        System.out.println(result);
        assertEquals("No results found!", result);
    }


}