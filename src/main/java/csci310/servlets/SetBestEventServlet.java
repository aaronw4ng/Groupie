package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class SetBestEventServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            Database db = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            out.print(db.setBestEvent(proposalId, eventId));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Set Best Event Servlet Failed");
        }
    }
}
