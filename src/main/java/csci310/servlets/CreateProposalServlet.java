package csci310.servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import csci310.Database;
import csci310.Event;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
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
            JsonArray invitedJson = new Gson().fromJson(invited, JsonArray.class);
            List<String> invitedList = new ArrayList<>();
            for (int i = 0; i < invitedJson.size(); i++) {
                invitedList.add(invitedJson.get(i).getAsString());
                System.out.println("Add to list: " + invitedJson.get(i).getAsString());
            }
            String events = request.getParameter("events");
            Type eventListType = new TypeToken<ArrayList<Event>>(){}.getType();
            List<Event> eventsList = new Gson().fromJson(events, eventListType);
            Boolean isDraft = Boolean.valueOf(request.getParameter("isDraft"));
            System.out.println(invitedList.size());
            System.out.println(eventsList.size());

            // TODO: change -> using a temp list to get only the event url
            // Need to also change createAProposal and add Events function to database
            List<String> tempList = new ArrayList<>();
            for (Event e: eventsList) {
                tempList.add(e.getUrl());
            }

            // successful proposal creation
            if (database.createAProposal(owner, title, descript, invitedList, tempList, isDraft)) {
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
