package csci310.servlets;

import csci310.Database;
import csci310.Proposal;

import javax.servlet.*;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class GetAllDraftProposalsServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            Database database = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<Proposal> proposals = database.getAllDraftProposals(userId);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            out.print(gson.toJson(proposals));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Get All Draft Proposals Servlet failed");
        }
    }
}
