package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SetAvailabilityServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int userId = Integer.parseInt(request.getParameter("userId"));
            boolean availability = Boolean.parseBoolean(request.getParameter("availability"));
            String until = "null";
            Database db = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            if (!availability){
                // only needs until(variable) if setting unavailable
                int hours = Integer.parseInt(request.getParameter("hours"));
                if (hours == -1){
                    until = "INDEFINITE";
                }
                else {
                    Date untilDate = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(hours));
                    until = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(untilDate);
                }
            }
            out.print(db.setUserAvailability(userId, availability, until));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Set Availability Servlet Failed");
        }
    }
}

