package csci310.servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import csci310.Database;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));
        try {
            Database database = new Database("test.db");
            database.createRequiredTables();
            PrintWriter out = response.getWriter();
            String us = request.getParameter("username");
            String ps = request.getParameter("password");
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
