/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import beans.BattleRequest;
import beans.User;
import cms.CMSUtilities;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class CancelBattleRequestServlet extends HttpServlet implements Constants {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            if (request.getSession().getAttribute(LOGGED_IN_USER) != null) {
                User current_user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
                if (request.getParameter(PARAM_USER) != null) {
                    User user = CMSUtilities.getUser(request.getParameter(PARAM_USER));
                    if (user != null) {
                        HttpSession session = SessionListener.findSession(user);
                        if (session != null) {
                            if (session.getAttribute(LOGGED_IN_USER) != null) {
                                User opponent = (User)session.getAttribute(LOGGED_IN_USER);
                                if (current_user.getSentRequests().containsKey(opponent.getUserName())) {
                                    current_user.getSentRequests().remove(opponent.getUserName());
                                }
                                if (opponent.getBattleRequests().containsKey(current_user.getUserName())) {
                                    opponent.getBattleRequests().remove(current_user.getUserName());
                                }
                            }
                        }
                    } 
                } 
            }
        } catch (Exception ex) { }       
        RequestDispatcher view = request.getRequestDispatcher(HOME_PAGE);
        view.forward(request, response);
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
