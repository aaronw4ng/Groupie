package csci310.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;

import csci310.Database;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Database database = (Database) getServletContext().getAttribute("database");
		System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));
        try {
            PrintWriter out = response.getWriter();
            String us = request.getParameter("username");
            String ps = request.getParameter("password");
            // successful login
            if (database.login(us, ps)) {
                request.setAttribute("status", true);
                out.print(true);
            }
            // login failed
            else {
                request.setAttribute("status", false);
                out.print(false);
            }
        } catch (Exception e) {
            throw new ServletException("Login Servlet failed");
        }
	}
	
}
