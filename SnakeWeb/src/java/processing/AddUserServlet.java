package processing;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.captcha.Captcha;

import beans.*;

public class AddUserServlet extends HttpServlet implements Constants {
       
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType(RESPONSE_CONTENT_TYPE);
        PrintWriter out = response.getWriter();   
        Captcha captcha = (Captcha)request.getSession().getAttribute(Captcha.NAME);
        
        request.setCharacterEncoding("UTF-8");
        String answer = request.getParameter("answer");
        try {
                if (!captcha.isCorrect(answer)) {
                    throw new Exception(ERR_INVALID_CAPTCHA);
                }
                
                new User(request.getParameter(USER_NAME), 
                                request.getParameter(PASSWORD), 
                                request.getParameter(EMAIL_ADDRESS),
                                DEFAULT_AVATAR, User.USER_OFFLINE, DEFAULT_MESSAGE).save();
                
                
                out.println(SPAN_SUCCESS + MSG_USER_ADDED + END_SPAN);
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
