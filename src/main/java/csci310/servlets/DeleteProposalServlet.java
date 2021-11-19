package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteProposalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database database = (Database) getServletContext().getAttribute("database");
        try {
            PrintWriter out = response.getWriter();
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));

            // successful proposal deletion
            if (database.deleteProposal(proposalId)) {
                request.setAttribute("status", true);
                out.print(true);
            }
            // failed proposal deletion
            else {
                request.setAttribute("status", false);
                out.print(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("Delete Proposal Servlet failed");
        }
    }
}
