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
public class BattleServlet extends HttpServlet implements Constants {
    private static final String BATTLE = "battle";
    private static final String PARAM_PLAYER = "player";
    private static final String PARAM_OPPONENT = "opponent";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            if (request.getSession().getAttribute(LOGGED_IN_USER) != null) {
                User current_user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
                if ((request.getParameter(PARAM_PLAYER) != null) && (request.getParameter(PARAM_OPPONENT) != null)) {
                    User player = CMSUtilities.getUser(request.getParameter(PARAM_PLAYER));
                    User opponent = CMSUtilities.getUser(request.getParameter(PARAM_OPPONENT));
                    if ((player != null) && (opponent != null)) {
                        if (SessionListener.isUserOnline(player.getUserName()) && (SessionListener.isUserOnline(opponent.getUserName()))) {
                            int type = (player.getUserName().equals(current_user.getUserName())) ? 1 : 2;
                            request.getSession().setAttribute("player_input", player.getUserName());
                            request.getSession().setAttribute("opponent_input", opponent.getUserName());
                            request.getSession().setAttribute("type_input", type);
                            if (opponent.getUserName().equals(current_user.getUserName())) {
                                HttpSession session = SessionListener.findSession(player);
                                session.setAttribute(ATRB_LOAD_GAME, current_user);
                            }
                        }
                    }
                }
                request.setAttribute(PAGE, BATTLE);
                RequestDispatcher view = request.getRequestDispatcher(HOME_PAGE);
                view.forward(request, response);
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
