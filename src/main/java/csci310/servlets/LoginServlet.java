package csci310.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import csci310.Database;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Database database = new Database();
            PrintWriter out = response.getWriter();
            // successful login
            if (database.login(request.getParameter("username"), request.getParameter("password"))) {
                database.close();
                out.print(true);
            }
            // login failed
            else {
                database.close();
                out.print(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
