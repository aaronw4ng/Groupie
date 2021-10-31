package csci310;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.Before;
import org.mockito.Mockito;

public class AppServletContextListenerTest {
    
    @Test
    public void testContextInitialized() {
        ServletContext context = Mockito.mock(ServletContext.class);
        AppServletContextListener listener = new AppServletContextListener();
        ServletContextEvent event = new ServletContextEvent(context);
        listener.contextInitialized(event);
        assertTrue(context.getAttribute("database") != null);
        listener.contextDestroyed(event);
    }

    @Test
    public void testContextDestroyed() {
        ServletContext context = Mockito.mock(ServletContext.class);
        AppServletContextListener listener = new AppServletContextListener();
        ServletContextEvent event = new ServletContextEvent(context);
        listener.contextInitialized(event);
        listener.contextDestroyed(event);
        assertTrue(context.getAttribute("database") == null);
    }
}
