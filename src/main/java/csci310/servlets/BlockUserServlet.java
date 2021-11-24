package csci310.servlets;

import csci310.Database;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

public class BlockUserServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try{
            int userId = Integer.parseInt(request.getParameter("userId"));
            int blockedUserId = Integer.parseInt(request.getParameter("blockedUserId"));
            Database db = (Database) getServletContext().getAttribute("database");
            PrintWriter out = response.getWriter();
            if (db.setBlockUser(true, userId, blockedUserId)){
                out.print(true);
            }
            else {
                out.print(false);
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ServletException("Block User Servlet Failed");
        }
    }
}
