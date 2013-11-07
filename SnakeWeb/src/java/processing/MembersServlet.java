/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import beans.User;
import cms.CMSUtilities;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MembersServlet extends HttpServlet implements Constants {
    private static final String CURRENT_PAGE = "currentpage";
    private static final String DISABLE_LINK = " disablelink";
    private static final String NEXT = "next";
    private static final String NEXT_PAGE = "next_page";
    private static final String PAGE_COUNT = "pagecount";
    private static final String MEMBERS = "members";
    private static final int PER_PAGE = 5;
    private static final String PREV = "prev";
    private static final String PREV_PAGE = "prev_page";

    
    private boolean isNumeric(String value) {
            Matcher m = Pattern.compile("^[0-9]+$").matcher(value);		
            return m.find();
    }
    
    private void updateUser(ArrayList<User> users) {
            String path = this.getServletContext().getRealPath(USER_UPLOAD_PATH) + File.separator;
            for (User user : users) {
                cms.CMSUtilities.checkDisplayPicture(user, path);
                SessionListener.updateStatus(user);
            }
    }
    
    private int getPageCount() {
            return (int)Math.ceil((double)CMSUtilities.getUsersCount()/PER_PAGE);
    }
    
    private int getNextPage(int currentPage, int pageCount) {
            currentPage = ((currentPage > 0) && (currentPage < pageCount))? ++currentPage:
                           (currentPage == pageCount) ? pageCount : 1;
            return currentPage;     
    }
    
    private int getPrevPage(int currentPage, int pageCount) {
            currentPage = ((currentPage > 1) && (currentPage <= pageCount))? --currentPage: 1;
            return currentPage;     
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            int page = 1, offset = 0, page_count = this.getPageCount();
            if (request.getParameter(PAGE) != null) {
                page = (isNumeric(request.getParameter(PAGE))) ? Integer.parseInt(request.getParameter(PAGE)): 1;
                if (request.getParameter(ACTION) != null) {
                    page = (request.getParameter(ACTION).equalsIgnoreCase(NEXT)) ? this.getNextPage(page, page_count) :
                           (request.getParameter(ACTION).equalsIgnoreCase(PREV)) ? this.getPrevPage(page, page_count) : 1;
                }
                page = (page > page_count) ? page_count : 
                       (page <= 1) ? 1 : page;
            }
            offset = (page - 1) * PER_PAGE;
            RequestDispatcher view = request.getRequestDispatcher(HOME_PAGE);
           
            request.setAttribute(PAGE, MEMBERS);
            request.setAttribute(CURRENT_PAGE, page);
            request.setAttribute(PAGE_COUNT, page_count);
            request.setAttribute(NEXT_PAGE, (page >= page_count) ? DISABLE_LINK : EMPTY_STRING);
            request.setAttribute(PREV_PAGE, (page <= 1) ? DISABLE_LINK : EMPTY_STRING);
            
            ArrayList<User> users = CMSUtilities.getUsers(offset, PER_PAGE);
            this.updateUser(users);
            request.setAttribute(MEMBERS, users);
            view.forward(request, response);
    }
}
