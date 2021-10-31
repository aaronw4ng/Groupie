package csci310;

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener{
    public Database database = null;
    public static String db_name = "project27.db";
    public static String db_config = "db_config.ini";
    public static String db_pass = "ThisProjectIsSoMuchFun";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try{
            // create a new shared instance of database
            database = new Database(db_name, db_config, db_pass);
            // set it on the application level
            sce.getServletContext().setAttribute("database", database);
        }
        catch (Exception e){
            System.out.println("failed to establish database connection");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce){
        sce.getServletContext().removeAttribute("database");
        try{
            database.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        database = null;
    }
}
