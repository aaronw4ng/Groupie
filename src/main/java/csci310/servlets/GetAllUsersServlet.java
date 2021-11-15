package csci310.servlets;

import csci310.Database;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class GetAllUsersServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Database database = (Database) getServletContext().getAttribute("database");
        PrintWriter out = response.getWriter();
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userList = database.getAllUsers(userId);
        out.print(userList);
    }
}
