package csci310.servlets;

import csci310.Database;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            Database database = new Database("test.db");
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
            database.close();
        } catch (Exception e) {
            throw new ServletException("Register Servlet failed");
        }
    }
}
