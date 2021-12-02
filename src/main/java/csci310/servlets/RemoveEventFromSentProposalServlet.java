package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoveEventFromSentProposalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database database = (Database) getServletContext().getAttribute("database");
        try {
            PrintWriter out = response.getWriter();
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            // successful removed event from sent proposal
            if (database.removeEventFromSentProposal(proposalId, eventId)) {
                out.print(true);
            }
            // failed to remove event from sent proposal
            else {
                out.print(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("Remove Event From Sent Proposal Servlet failed");
        }
    }
}
