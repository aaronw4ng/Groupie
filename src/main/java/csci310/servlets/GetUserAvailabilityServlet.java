package csci310.servlets;

import csci310.Database;
import csci310.UserAvailability;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;

public class GetUserAvailabilityServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            Database database = (Database) getServletContext().getAttribute("database");
            int userId = Integer.parseInt(request.getParameter("userId"));
            UserAvailability userAvailability = database.getUserAvailability(userId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            PrintWriter out = response.getWriter();
            out.print(gson.toJson(userAvailability));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("Get UserAvailability Servlet Failed");
        }
    }
}