package csci310.servlets;

import csci310.Database;
import csci310.UserAvailibility;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class GetAllUsersServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            Database database = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            int userId = Integer.parseInt(request.getParameter("userId"));
            List<UserAvailibility> userList = database.getAllUsers(userId);
            out.print(userList);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new ServletException("Get All Users Servlet failed");
        }
    }
}
