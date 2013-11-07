/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import beans.User;
import cms.CMSUtilities;
import java.io.File;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ProfileServlet extends HttpServlet implements Constants {
    private static final String PROFILE = "profile";
   
    private void checkUser(User user) {
            String path = this.getServletContext().getRealPath(USER_UPLOAD_PATH) + File.separator;
            cms.CMSUtilities.checkDisplayPicture(user, path);     
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
             RequestDispatcher view = request.getRequestDispatcher(HOME_PAGE);
             request.setAttribute(PAGE, PROFILE);
             if (request.getParameter("user") != null) {
                 User user = CMSUtilities.getUser(request.getParameter("user"));
                 if (user != null) {
                     request.setAttribute("member", user);
                     view.forward(request, response);
                 } else {
                     response.sendRedirect(HOME_PAGE);
                 }
             } else {
                 response.sendRedirect(HOME_PAGE);
             }
    }
}
