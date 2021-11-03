package csci310.servlets;

import csci310.Database;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Database database = (Database) this.getServletConfig().getServletContext().getAttribute("database");
        try {
            PrintWriter out = response.getWriter();
            String usernameValue = request.getParameter("username");
            String passwordValue = request.getParameter("password");
            // if user already exists, then unsuccessful registration
            if (database.checkUserExists(usernameValue)) {
                out.print(false);
            }
            // if user does not yet exist, then register them
            else {
                database.register(usernameValue, passwordValue);
                out.print(true);
            }
        } catch (Exception e) {
            throw new ServletException("Register Servlet failed");
        }
    }
}
