/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cms;
import beans.*;
import java.io.File;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class CMSUtilities {
        private static final String EMAILADDRESS = "EmailAddress";
        private static final String PASSWORD = "Password";
        private static final String DISPLAYPICTURE = "DisplayPicture";
    private static final String STATUS = "Status";
        private static final String USERNAME_EXISTS = "Username Already Exists";
        private static final String USER_NAME = "UserName";
        private static final String MESSAGE = "Message";
        private static String DEFAULT_AVATAR = "default_avatar.jpg";
        private static String EMPTY_STRING = "";
    
        private static boolean hasRows(ResultSet results) throws SQLException {
                boolean hasRows = false;
                if (results != null) {
                    results.last();
                    hasRows = (results.getRow() > 0);
                }
                return hasRows;
        }
        
        private static boolean userNameExists(User user) throws Exception {
                try {
                    Object[] obj = {user.getUserName()};
                    ResultSet records = DBStatement.getRSFromPreparedStatment(SQLConstant.CHECK_USERNAME_EXISTS, obj);
                    return hasRows(records);
                } catch (SQLException ex) {
                   System.err.println(ex.getMessage());
                   return false;
                }
        }
        
        private static User getUserObject(ResultSet records) throws SQLException {
                return new User(records.getString(USER_NAME), 
                                        records.getString(PASSWORD), 
                                        records.getString(EMAILADDRESS),
                                        records.getString(DISPLAYPICTURE),
                                        records.getInt(STATUS),
                                        records.getString(MESSAGE));
        }
        
        private static ArrayList<User> processUsers(ResultSet records) throws SQLException {
                ArrayList<User> users = new ArrayList<User>();
                while (records.next()) {
                       users.add(getUserObject(records));
                }
                return users;
        }
        
        private static User getUserResult(ResultSet records) {
                try {
                    if (hasRows(records)) records.first();
                    return getUserObject(records);
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    return null;
                }
        }
        
        public static ArrayList<User> getUsers() {
                try {
                    return processUsers(DBStatement.getResultSet(SQLConstant.SELECT_USERS));
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    return new ArrayList<User>();
                }
        }
        
        public static ArrayList<User> getUsers(int offset, int perpage) {
                try {
                    Object[] obj = {offset, perpage};
                    return processUsers(DBStatement.getRSFromPreparedStatment(SQLConstant.SELECT_USERS_LIMITED, obj));
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    return new ArrayList<User>();
                }
        }
        
        public static int getUsersCount() {
                try {
                    ResultSet records = DBStatement.getResultSet(SQLConstant.COUNT_USERS);
                    records.first();
                    return records.getInt(1);
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                    return 0;
                }
        }
        
        public static String encryptPassword(String password) {
                try {
                     StringBuilder hexString = new StringBuilder();
                     MessageDigest alg = MessageDigest.getInstance("MD5");
                     alg.reset();
                     alg.update(password.getBytes());
                     byte[] bytes = alg.digest();
                     for (int i=0;i < bytes.length; i++) {
                         hexString.append(Integer.toHexString((bytes[i] >>> 4) & 0x0F));
                         hexString.append(Integer.toHexString(0x0F & bytes[i]));
                     }
                     return hexString.toString();
                } catch (Exception ex) {
                     System.err.println(ex.getMessage());
                     return EMPTY_STRING;
                }
                
        }
    
        public static User getUser(String userName, String password) {
                    Object[] obj = {userName, encryptPassword(password)};
                    return getUserResult(DBStatement.callStoredProcedure(SQLConstant.CHECK_USER, obj));    
        }
        
        public static User getUser(String userName) {
                    Object[] obj = {userName};
                    return getUserResult(DBStatement.getRSFromPreparedStatment(SQLConstant.CHECK_USERNAME_EXISTS, obj));    
        }
        
        public static void commitUserAdd(User user) throws Exception {
                if (userNameExists(user)) throw new Exception(USERNAME_EXISTS);
                Object[] objects = {user.getUserName(), user.getEmailAddress(), user.getDisplayPicture(), user.getStatus(), user.getMessage(), encryptPassword(user.getPassword())};
                DBStatement.doPreparedStatment(SQLConstant.ADD_USER_SQL, objects);
        }
        
        public static void commitUserUpdate(User user) {
                Object[] objects = {user.getEmailAddress(), user.getDisplayPicture(), user.getStatus(), user.getMessage(), user.getPassword(), user.getUserName()};
                DBStatement.doPreparedStatment(SQLConstant.UPDATE_USER_SQL, objects);
        }
        
        public static void checkDisplayPicture(User user, String path) {
                if (!new File(path + user.getDisplayPicture()).exists()) {
                    user.setDisplayPicture(DEFAULT_AVATAR);
                    try {
                        user.update();
                    } catch (Exception ex) {
                        Logger.getLogger(CMSUtilities.class.getName()).log(Level.WARNING, null, ex);
                    }
                }
        }
}
