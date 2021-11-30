package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class SetFinalDecisionServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            Boolean accept = Boolean.parseBoolean(request.getParameter("accept"));
            Database db = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            out.print(db.setFinalDecision(userId, proposalId, accept));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Set Final Decision Servlet Failed");
        }
    }
}
