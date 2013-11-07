/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import beans.BattleRequest;
import beans.User;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class GetBattleRequestsServlet extends HttpServlet implements Constants {
    private static final String ATRB_BATTLE_SIZE = "battle_amount";
    private static final String BATTLES = "battles";
    private static final String TYPE = "type";
    private static final String TYPE_BATTLE = "battle";
    private static final String TYPE_SENT = "sent";
    private static final String TEMPLATE_FOLDER = "template/";
    private static final String REQUESTS_JSP = "_requests.jsp";
    
   
    private void checkBattleRequests(Collection battles) {
            synchronized (battles) {
                for (Iterator it = battles.iterator(); it.hasNext();) {
                    BattleRequest battle = (BattleRequest)it.next();
                    if ((!SessionListener.isUserOnline(battle.getPlayer().getUserName())) ||
                        (!SessionListener.isUserOnline(battle.getOpponent().getUserName()))) {
                       it.remove();
                    }
                }
            }
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            if (request.getSession().getAttribute(LOGGED_IN_USER) != null) {
                User user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
                if (request.getParameter(TYPE) != null) {
                    String type = request.getParameter(TYPE);
                    Collection battles = (type.equals(TYPE_BATTLE))? user.getBattleRequests().values() :
                                         (type.equals(TYPE_SENT))? user.getSentRequests().values() : null;
                    if (battles != null) {
                        this.checkBattleRequests(battles);
                        request.setAttribute(ATRB_BATTLE_SIZE, battles.size());
                        request.setAttribute(BATTLES, battles);
                    }
                    RequestDispatcher view = request.getRequestDispatcher(TEMPLATE_FOLDER + type + REQUESTS_JSP);
                    view.forward(request, response);
                }
                  
            }
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
