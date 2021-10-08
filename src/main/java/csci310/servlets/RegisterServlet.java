package csci310.servlets;

import csci310.Database;
import csci310.Hello;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Database database = new Database();
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
            e.printStackTrace();
        }
    }
}
