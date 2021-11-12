package csci310.servlets;

import csci310.Ticketmaster;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class SearchEventsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            PrintWriter out = response.getWriter();
            String keyword = request.getParameter("keyword");
            String zipCode = request.getParameter("zipCode");
            String city = request.getParameter("city");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            System.out.println(keyword + " " + zipCode + " " + city + " " + startDate + " " + endDate);
            Ticketmaster ticketmaster = new Ticketmaster();
            String events = ticketmaster.searchEvents(keyword, zipCode, city, startDate, endDate);
            // if reached here, then did get some results
            out.print(events);
         //   System.out.println(events);
        }
        // No results found for search or something else went wrong, then let front end no results found
        catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("No results found!");
        }
    }
}
