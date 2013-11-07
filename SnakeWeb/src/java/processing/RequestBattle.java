/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import beans.BattleRequest;
import beans.User;
import cms.CMSUtilities;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class RequestBattle extends HttpServlet implements Constants {
    private static final String ERR_ALREADY_REQUESTED = "Already requested a battle";
   
    private static final String ERR_CANNOT_BATTLE_YOURSELF = "Cannot Battle Yourself";
    private static final String ERR_MUST_BE_ONLINE = "Must be online and logged in";
    private static final String ERR_PENDING_REQUEST = "Already have a pending request";
    private static final String ERR_USER_DOESNT_EXIST = "User doesnt exist";
    private static final String ERR_USER_IN_BATTLE = "User currently in battle";
    private static final String ERR_USER_NOT_ONLINE = "User not online";
    private static final String SUCCESS_MSG = "Requested Battle with ";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType(RESPONSE_CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        try {
            if (request.getSession().getAttribute(LOGGED_IN_USER) != null) {
                User current_user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
                if (request.getParameter(PARAM_USER) != null) {
                    User user = CMSUtilities.getUser(request.getParameter(PARAM_USER));
                    if (user != null) {
                        if (current_user.getUserName().equals(user.getUserName())) {
                            throw new Exception(ERR_CANNOT_BATTLE_YOURSELF);
                        }
                        HttpSession session = SessionListener.findSession(user);
                        if (session != null) {
                            User opponent = (User)session.getAttribute(LOGGED_IN_USER);
                            if (SessionListener.isUserOnline(opponent.getUserName())) {
                                if (opponent.getStatus() == User.USER_INBATTLE) {
                                    throw new Exception(ERR_USER_IN_BATTLE);
                                }
                                BattleRequest battle = new BattleRequest(current_user, opponent);
                                if (opponent.getBattleRequests().containsKey(current_user.getUserName())) {
                                    throw new Exception(ERR_ALREADY_REQUESTED);
                                }
                                if (current_user.getBattleRequests().containsKey(opponent.getUserName())) {
                                    throw new Exception(ERR_PENDING_REQUEST);
                                }
                                opponent.getBattleRequests().put(current_user.getUserName(), battle);
                                current_user.getSentRequests().put(opponent.getUserName(), battle);
                                out.println(SPAN_SUCCESS + SUCCESS_MSG + opponent.getUserName() + END_SPAN);
                            } else {
                                throw new Exception(ERR_USER_NOT_ONLINE);
                            }
                        } else {
                            throw new Exception(ERR_USER_NOT_ONLINE);
                        }
                    } else {
                        throw new Exception(ERR_USER_DOESNT_EXIST);
                    }
                }
            } else {
                throw new Exception(ERR_MUST_BE_ONLINE);
            }
        } catch (Exception ex) {
            out.println(SPAN_ERROR + ex.getMessage() + END_SPAN);
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
