package processing;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.*;
import cms.CMSUtilities;
import java.io.File;

public class LoginUserServlet extends HttpServlet implements Constants {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (request.getParameter(ACTION).equals(LOGOUT_PARAM)) {
           SessionListener.removeSession(request.getSession());
        }
        response.sendRedirect(HOME_PAGE);
    } 


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            User user = CMSUtilities.getUser(request.getParameter(LOGIN_USER_NAME), request.getParameter(LOGIN_PASSWORD));
            if (user != null) {
                request.getSession().setAttribute(LOGGED_IN_USER, user);
                String path = this.getServletContext().getRealPath(USER_UPLOAD_PATH) + File.separator;
                cms.CMSUtilities.checkDisplayPicture(user, path);
                SessionListener.addSession(user, request.getSession());
            }
            response.sendRedirect(HOME_PAGE);
    }
}