package processing;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import beans.User;

import java.io.PrintWriter;



public class UpdateUserServlet extends HttpServlet implements Constants {
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType(RESPONSE_CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        try {
            User user = (User)request.getSession().getAttribute(LOGGED_IN_USER);
            user.setEmailAddress(request.getParameter(EMAIL_ADDRESS));
            user.setMessage(request.getParameter(MESSAGE));
            user.update();
            out.println(SPAN_SUCCESS + MSG_USER_UPDATED + END_SPAN);
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