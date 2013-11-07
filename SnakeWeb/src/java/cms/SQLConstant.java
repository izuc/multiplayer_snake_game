/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cms;

public class SQLConstant {
        static final String ADD_USER_SQL = "INSERT INTO users (UserName, EmailAddress, DisplayPicture, Status, Message, Password) VALUES(?, ?, ?, ?, ?, ?)";
        static final String UPDATE_AVATAR_SQL = "UPDATE users SET DisplayPicture = ? WHERE UserName = ?";
        static final String UPDATE_USER_SQL = "UPDATE users SET EmailAddress = ?, DisplayPicture = ?, Status = ?, Message = ?, Password = ? WHERE UserName = ?";
        static final String SELECT_USERS = "SELECT * FROM users";
        static final String SELECT_USERS_LIMITED = SELECT_USERS + " ORDER BY UserName ASC LIMIT ?, ?";
        static final String CHECK_USERNAME_EXISTS = SELECT_USERS + " WHERE UserName = ?";
        static final String CHECK_USER = "checkUser(?, ?)";
        static final String COUNT_USERS = "SELECT COUNT(*) FROM users";
}
