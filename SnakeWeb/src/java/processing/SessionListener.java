package processing;
import beans.User;
import javax.servlet.http.*;
import java.util.*;

public class SessionListener implements Constants {
        private static HashMap<String, HttpSession> activeSessions = new HashMap<String, HttpSession>(); 

        private static boolean isValid(HttpSession session) throws IllegalStateException {
                return  (((Date)session.getAttribute(LAST_ACCESSED)).getTime() > ((new Date(System.currentTimeMillis())).getTime() - 10000));
        }
        
        private static void updateStatus(User user, int match_status, int new_status) {
                if (user.getStatus() == match_status) {
                   user.setStatus(new_status);
                   user.update();
                }
        }
        
        private static boolean checkSession(HttpSession session) throws IllegalStateException {
               User user = (User)session.getAttribute(LOGGED_IN_USER);
               if (isValid(session)) {
                   updateStatus(user, User.USER_OFFLINE, User.USER_ONLINE);
                   return true;
               } else {
                   updateStatus(user, User.USER_ONLINE, User.USER_OFFLINE);
                   return false;
               }
        }
        
        public static void addSession(User user, HttpSession session) {
                if (activeSessions.containsKey(user.getUserName())) activeSessions.remove(user.getUserName());
                updateStatus(user, User.USER_OFFLINE, User.USER_ONLINE);
                activeSessions.put(user.getUserName(), session);
        }
        
        public static void removeSession(HttpSession session) {
                if (session.getAttribute(LOGGED_IN_USER) != null) {
                    User user = (User)session.getAttribute(LOGGED_IN_USER);
                    updateStatus(user, User.USER_ONLINE, User.USER_OFFLINE);
                }
                activeSessions.remove(session);
                session.invalidate();
        }
        
        public static HttpSession findSession(User user) {
                if (activeSessions.containsKey(user.getUserName())) {
                    return activeSessions.get(user.getUserName());
                }
                return null;
        }
        
        public static void updateStatus(User user) throws IllegalStateException {
                try {
                    HttpSession session = findSession(user) ;
                    if (session != null) {
                        checkSession(session);
                    } else {
                        updateStatus(user, User.USER_ONLINE, User.USER_OFFLINE);
                    }
                } catch (IllegalStateException ex) { }
        }
        
        public static boolean isUserOnline(String userName) {
                try {
                    if (activeSessions.containsKey(userName)) {
                        return isValid(activeSessions.get(userName));
                    }
                } catch (IllegalStateException ex) { }
                return false;
        }
                            
	public static ArrayList<User> getSessions() {
                ArrayList<User> users = new ArrayList<User>();
                Collection c = activeSessions.values();
                synchronized (c) {
                    for (Iterator it = c.iterator(); it.hasNext();) {
                        try {
                            HttpSession session = (HttpSession)it.next();                           
                            if (session.getAttribute(LOGGED_IN_USER) != null) {
                                 if (checkSession(session)) {
                                     User user = (User)session.getAttribute(LOGGED_IN_USER);
                                     users.add(user);
                                 }
                             }
                        } catch (IllegalStateException ex) {
                             it.remove();  
                        }
                    }
                }
                return users;
        }        
}
