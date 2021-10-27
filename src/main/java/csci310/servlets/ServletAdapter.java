package csci310.servlets;

import csci310.Database;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.http.*;

public class ServletAdapter extends HttpServlet{
    public Database database = null;
    public static String db_name = "project27.db";
    public static String db_config = "db_config.ini";
    public static String db_pass = "ThisProjectIsSoMuchFun";
    // initialize the database connection to be shared in application
    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        database = (Database)getServletConfig().getServletContext().getAttribute("database");
        if (database == null){
            try{
                // create a new shared instance of database
                database = new Database(db_name, db_config, db_pass);
                // set it on the application level
                getServletConfig().getServletContext().setAttribute("database", database);
            }
            catch (Exception e){
                System.out.println("failed to establish database connection");
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
