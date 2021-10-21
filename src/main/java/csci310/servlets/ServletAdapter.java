package csci310.servlets;

import csci310.Database;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ServletAdapter extends HttpServlet{
    public Database database = null;
    // initialize the database connection to be shared in application
    // @Override
    public void init() throws ServletException{
        database = (Database)getServletConfig().getServletContext().getAttribute("database");
        if (database == null){
            try{
                // create a new shared instance of database
                database = new Database("test.db");
                // set it on the application level
                getServletConfig().getServletContext().setAttribute("database", database);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    // close the application-wise database connection and set it to null
    public void close() throws Exception{
        // remove from application context
        getServletConfig().getServletContext().removeAttribute("database");
        database.close();
        database = null;
    }
}
