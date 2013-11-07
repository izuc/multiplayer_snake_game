/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import beans.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BattleLoadCheckServlet extends HttpServlet implements Constants {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (request.getSession().getAttribute(LOGGED_IN_USER) != null) {
            User current_user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
            if (request.getSession().getAttribute(ATRB_LOAD_GAME) != null) {
                User sender = (User)request.getSession().getAttribute(ATRB_LOAD_GAME);
                if (sender.getBattleRequests().containsKey(current_user.getUserName())) {
                    out.println("player="+current_user.getUserName()+"&opponent="+sender.getUserName());
                    sender.getBattleRequests().remove(current_user.getUserName());
                    current_user.getSentRequests().remove(sender.getUserName());
                    request.getSession().setAttribute(ATRB_LOAD_GAME, null);
                }
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

}
