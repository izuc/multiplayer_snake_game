package beans;
import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable, Constants {

        public static final int USER_ONLINE = 1;
        public static final int USER_OFFLINE = 2;
        public static final int USER_INBATTLE = 3;
        
        
	private String userName;
	private String password;
	private String emailAddress;
        private String displayPicture;
        private int status;
        private String message;
        
        private HashMap<String, BattleRequest> battleRequests;
        private HashMap<String, BattleRequest> sentRequests;
	
	public User() {
		this.userName = EMPTY_STRING;
		this.password = EMPTY_STRING;
		this.emailAddress = EMPTY_STRING;
                this.displayPicture = EMPTY_STRING;
                this.status = USER_OFFLINE;
                this.message = EMPTY_STRING;
                this.battleRequests = new HashMap<String, BattleRequest>();
                this.sentRequests = new HashMap<String, BattleRequest>();
	}
        
        public User(String userName, String password, String emailAddress, String displayPicture, int status, String message) {
                this.setUserName(userName);
                this.setPassword(password);
                this.setEmailAddress(emailAddress);
                this.setDisplayPicture(displayPicture);
                this.setStatus(status);
                this.setMessage(message);
                this.battleRequests = new HashMap<String, BattleRequest>();
                this.sentRequests = new HashMap<String, BattleRequest>();
        }
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return this.userName;
	}
        
        public void setPassword(String password) {
                this.password = password;
        }
        
        public String getPassword() {
                return this.password;
        }
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getEmailAddress() {
		return this.emailAddress;
	}
        
        public String getDisplayPicture() {
                return this.displayPicture;
        }

        public void setDisplayPicture(String displayPicture) {
                this.displayPicture = displayPicture;
        }
        
        public int getStatus() {
                return this.status;
        }

        public void setStatus(int status) {
               this.status = status;
        }
        
        public void save() throws Exception {
               cms.CMSUtilities.commitUserAdd(this);
        }
        
        public void update() {
               cms.CMSUtilities.commitUserUpdate(this);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public HashMap<String, BattleRequest> getBattleRequests() {
            return battleRequests;
        }

        public void setBattleRequests(HashMap<String, BattleRequest> battleRequests) {
            this.battleRequests = battleRequests;
        }
        
        public HashMap<String, BattleRequest> getSentRequests() {
            return sentRequests;
        }

        public void setSentRequests(HashMap<String, BattleRequest> sentRequests) {
            this.sentRequests = sentRequests;
        }
}