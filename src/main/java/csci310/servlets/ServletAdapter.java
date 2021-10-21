package csci310.servlets;

import csci310.Database;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ServletAdapter extends HttpServlet{
    public Database database = null;
    // initialize the database connection to be shared in application
    public void init() throws ServletException{
    }
    // close the application-wise database connection and set it to null
    public void close() throws Exception{
    }
}
