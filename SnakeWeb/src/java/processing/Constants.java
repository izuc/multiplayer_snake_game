package processing;


public interface Constants {
        static final String LOGGED_IN_USER = "LoggedInUser";
        static final String LAST_ACCESSED = "lastAccessed";
        
        static final String USER_NAME = "userName";
        static final String PASSWORD = "password";
        static final String EMAIL_ADDRESS = "emailAddress";
        static final String MESSAGE = "message";
        static final String DEFAULT_MESSAGE = "";
        static final String PAGE = "page";
        
        static final String ACTION = "action";
        static final String LOGOUT_PARAM = "logout";
        static final String HOME_PAGE = "index.jsp";
        
        static final String SPAN_SUCCESS = "<span class=\"success\">";
        static final String SPAN_ERROR = "<span class=\"error\">";
        static final String END_SPAN = "</span>";
        
        static final String MSG_USER_ADDED = "User Added";
        static final String MSG_USER_UPDATED = "User Details Updated";
        static final String ERR_INVALID_CAPTCHA = "Invalid Captcha Entered";
        static final String RESPONSE_CONTENT_TYPE = "text/html;charset=UTF-8";
        
        static final String USERS_LIST = "users";
        
        static final String LOGIN_USER_NAME = "loginUserName";
        static final String LOGIN_PASSWORD = "loginPassword";
        static final String DEFAULT_AVATAR = "default_avatar.jpg";
        static final String EMPTY_STRING = "";
        static final String STATUS_OFFLINE = "offline";
        static final String STATUS_ONLINE = "online";
        
        static final int MAX_FILE_SIZE = 512000;
        static final String USER_UPLOAD_PATH = "avatars/";
        static final String PARAM_USER = "user";
        static final String ATRB_LOAD_GAME = "load_game";
}
