/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cms;

/**
 *
 * @author Admin
 */
public class DBSetup {
            
        public static void createTables() {
                DBStatement.doUpdateQuery("CREATE TABLE IF NOT EXISTS users (" +
                                     "        UserID int(10) not null auto_increment," +
                                     "        UserName VARCHAR(15) not null," +
                                     "        EmailAddress VARCHAR(50) not null," +
                                     "        DisplayPicture VARCHAR(50) not null," +
                                     "        Message VARCHAR(50) not null," +
                                     "        Status int(1) not null," +
                                     "        Password VARCHAR(50) not null," +
                                     "        PRIMARY KEY  (UserID)" +
                                     ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;");

                DBStatement.doUpdateQuery("CREATE TABLE IF NOT EXISTS scores (" +
                                              "ScoreID int(10) not null auto_increment," +
                                              "UserID int(10) not null," +
                                              "Opponent int(10) not null," +
                                              "Points VARCHAR(20) not null," +
                                              "Status int(1) not null," +
                                              "PRIMARY KEY  (ScoreID)" +
                                       ") ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;");
                               
                DBStatement.doUpdateQuery("delimiter $$" +
                                          "CREATE PROCEDURE checkUser(IN user VARCHAR(15), IN passwd VARCHAR(50))" +
                                          "  BEGIN" +
                                          "    SELECT * FROM users WHERE UserName = user && Password = passwd;" +
                                          "  END" +
                                          "$$");
                
                DBStatement.doUpdateQuery("INSERT INTO users (UserName, EmailAddress, DisplayPicture, Status, Message, Password) VALUES ('lbaker', 'admin@izuc.net', 'default_avatar.jpg', 2, 'Battle Me', '5f4dcc3b5aa765d61d8327deb882cf99')");
        }
}
