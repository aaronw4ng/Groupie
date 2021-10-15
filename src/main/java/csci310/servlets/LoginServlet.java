package csci310.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import csci310.Database;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        System.out.println(request.getParameter("input-username"));
        System.out.println(request.getParameter("input-password"));
        try {
            Database database = new Database();
            database.createRequiredTables();
            PrintWriter out = response.getWriter();
            String us = request.getParameter("input-username");
            String ps = request.getParameter("input-password");
            // successful login
            if (database.login(us, ps)) {
                database.close();
                request.setAttribute("status", true);
                out.print(true);
            }
            // login failed
            else {
                database.close();
                request.setAttribute("status", false);
                out.print(false);
            }
        } catch (Exception e) {
            throw new ServletException("Login Servlet failed");
        }
    }
}
