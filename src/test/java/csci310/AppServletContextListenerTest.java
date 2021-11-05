package csci310;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.mockito.Mockito;

public class AppServletContextListenerTest {
    
    @Test
    public void testContextInitialized() {
        ServletContext context = Mockito.mock(ServletContext.class);
        AppServletContextListener listener = new AppServletContextListener();
        ServletContextEvent event = new ServletContextEvent(context);
        listener.contextInitialized(event);
        assertTrue(listener.database != null);
        // mock shared database connection returned by context
        Mockito.doReturn(listener.database).when(context).getAttribute("database");
        assertTrue(context.getAttribute("database") != null);
        listener.contextDestroyed(event);
    }
    
    @Test
    public void testContextInitializedWrongPass(){
        // test wrong password for the database
        String temp = AppServletContextListener.db_pass;
        AppServletContextListener.db_pass = "wrongPassword";
        ServletContext context = Mockito.mock(ServletContext.class);
        AppServletContextListener listener = new AppServletContextListener();
        ServletContextEvent event = new ServletContextEvent(context);
        listener.contextInitialized(event);
        assertTrue(listener.database == null);
        // mock shared database connection returned by context
        Mockito.doReturn(listener.database).when(context).getAttribute("database");
        assertTrue(context.getAttribute("database") == null);
        listener.contextDestroyed(event);
        // reset password
        AppServletContextListener.db_pass = temp;
    }

    @Test
    public void testContextDestroyed() {
        ServletContext context = Mockito.mock(ServletContext.class);
        AppServletContextListener listener = new AppServletContextListener();
        ServletContextEvent event = new ServletContextEvent(context);
        listener.contextInitialized(event);
        listener.contextDestroyed(event);
        assertTrue(listener.database == null);
        // mock shared database connection returned by context
        Mockito.doReturn(listener.database).when(context).getAttribute("database");
        assertTrue(context.getAttribute("database") == null);
    }
}
