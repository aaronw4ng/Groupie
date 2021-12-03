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

public class GetAllNonDraftProposalsServlet extends HttpServlet{
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            Database database = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            int userId = Integer.parseInt(request.getParameter("userId"));
            Boolean isOwner = Boolean.parseBoolean(request.getParameter("isOwner"));
            // System.out.println("0");
            List<Proposal> proposals = database.getAllNonDraftProposals(userId, isOwner);
            // System.out.println("1");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // System.out.println("2");
            out.print(gson.toJson(proposals));
            // System.out.println("3");
            System.out.println("-------START------");
            System.out.println(gson.toJson(proposals));
            System.out.println("-------END-------");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Get All Non-Draft Proposals Servlet failed");
        }
    }
}
