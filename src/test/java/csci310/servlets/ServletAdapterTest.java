package csci310.servlets;

import org.junit.Test;
import static org.junit.Assert.*;

public class ServletAdapterTest {

    @Test
    public void testInit() throws Exception{
        ServletAdapter testAdapter = new ServletAdapter();
        testAdapter.init();
        assertNotNull(testAdapter.database);
        testAdapter.close();
    }

    @Test
    public void testClose() throws Exception{
        ServletAdapter testAdapter = new ServletAdapter();
        testAdapter.close();
        assertNull(testAdapter.database);
    }
}
