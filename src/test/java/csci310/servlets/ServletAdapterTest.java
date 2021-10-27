package csci310.servlets;

import org.junit.Test;
import static org.junit.Assert.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import org.junit.Before;
import org.mockito.Mockito;

public class ServletAdapterTest {
    private ServletConfig config;
    private ServletContext context;

    @Before
    public void setUp() throws Exception {
        ServletAdapter.db_name = "test.db";
        config = Mockito.mock(ServletConfig.class);
        context = Mockito.mock(ServletContext.class);
        Mockito.doReturn(context).when(config).getServletContext();
    }
    
    @Test
    public void testInit() throws Exception{
        ServletAdapter testAdapter = new ServletAdapter();
        testAdapter.init(config);
        assertNotNull(testAdapter.database);
        // mock shared database connection returned by context
        Mockito.doReturn(testAdapter.database).when(context).getAttribute("database");
        ServletAdapter testAdapter2 = new ServletAdapter();
        testAdapter2.init(config);
        assertNotNull(testAdapter2.database);
    }

    @Test
    public void testInitWrongPass() throws Exception{
        // test wrong db_pass for coverage
        String temp = ServletAdapter.db_pass;
        ServletAdapter.db_pass = "wrongPassword";
        ServletAdapter testAdapter = new ServletAdapter();
        testAdapter.init(config);
        assertNull(testAdapter.database);
        // revert back to original password
        ServletAdapter.db_pass = temp;
    }

    @Test
    public void testClose() throws Exception{
        ServletAdapter testAdapter = new ServletAdapter();
        testAdapter.init(config);
        testAdapter.close();
        assertNull(testAdapter.database);
    }
}
