package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class IndicateResponseServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            String availability = request.getParameter("availability");
            int excitement = Integer.parseInt(request.getParameter("excitement"));
            Database db = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            out.print(db.indicateResponse(proposalId, eventId, userId, availability, excitement));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Indicate Response Servlet Failed");
        }
    }
}
