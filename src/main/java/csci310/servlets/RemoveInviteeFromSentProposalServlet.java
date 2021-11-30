package csci310.servlets;

import csci310.Database;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class RemoveInviteeFromSentProposalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database database = (Database) getServletContext().getAttribute("database");
        try {
            PrintWriter out = response.getWriter();
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));
            int userId = Integer.parseInt(request.getParameter("userId"));

            // successful removed invitee from sent proposal
            if (database.removeInviteeFromSentProposal(proposalId, userId)) {
                out.print(true);
            }
            // failed to remove invitee from sent proposal
            else {
                out.print(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("Remove Invitee from Sent Proposal Servlet failed");
        }
    }
}
