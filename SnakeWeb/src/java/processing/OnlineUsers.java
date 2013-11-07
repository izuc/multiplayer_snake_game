/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OnlineUsers extends HttpServlet implements Constants {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            request.getSession().setAttribute(LAST_ACCESSED, new Date(System.currentTimeMillis()));
            request.setAttribute(USERS_LIST, processing.SessionListener.getSessions());
            RequestDispatcher view = request.getRequestDispatcher("template/online_users.jsp");
            view.forward(request, response);  
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            processRequest(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            processRequest(request, response);
    }
}
