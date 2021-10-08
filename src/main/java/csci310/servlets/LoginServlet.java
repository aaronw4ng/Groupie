package csci310.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import csci310.Database;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.print(request.getParameter("input-username"));
        System.out.println(request.getParameter("input-password"));
        try {
            Database database = new Database();
            database.createRequiredTables();
            PrintWriter out = response.getWriter();
            // successful login
            if (database.login(request.getParameter("input-username"), request.getParameter("input-password"))) {
                database.close();
//                request.setAttribute("status", true);
                out.print(true);
            }
            // login failed
            else {
                database.close();
                request.setAttribute("status", false);
                out.print(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
