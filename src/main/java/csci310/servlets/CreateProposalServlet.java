package csci310.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import csci310.Database;
import csci310.Event;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CreateProposalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Database database = (Database) getServletContext().getAttribute("database");
        try {
            PrintWriter out = response.getWriter();
            String owner = request.getParameter("owner");
            String title = request.getParameter("title");
            String descript = request.getParameter("descript");
            String invited = request.getParameter("invited");
            Boolean isNew = Boolean.parseBoolean(request.getParameter("isNew"));
            int proposalId = Integer.parseInt(request.getParameter("proposalId"));
            JsonArray invitedJson = new Gson().fromJson(invited, JsonArray.class);
            List<String> invitedList = new ArrayList<>();
            for (int i = 0; i < invitedJson.size(); i++) {
                invitedList.add(invitedJson.get(i).getAsString());
                System.out.println("Add to list: " + invitedJson.get(i).getAsString());
            }
            String events = request.getParameter("events");
            Type eventListType = new TypeToken<ArrayList<Event>>(){}.getType();
            List<Event> eventsList = new Gson().fromJson(events, eventListType);

            int newProposalId = database.savesDraftProposal(owner, title, descript, invitedList, eventsList, isNew, proposalId);
            // successful proposal creation
            if (database.sendProposal(newProposalId)) {
                request.setAttribute("status", true);
                out.print(true);
            }
            // failed proposal creation
            else {
                request.setAttribute("status", false);
                out.print(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("Create Proposal Servlet failed");
        }
    }
}
