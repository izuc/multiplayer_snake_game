/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import snakegame.SnakeGame;
import java.util.HashMap;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import snakegame.SConnection;

/**
 *
 * @author Admin
 */
public class GameControllerServlet extends HttpServlet {
    
    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_OPPONENT = 2;
    
    private static HashMap<String, SnakeGame> current_games = new HashMap<String, SnakeGame>();
    
    
    private boolean isNumeric(String value) {
            Matcher m = Pattern.compile("^[0-9]+$").matcher(value);		
            return m.find();
    }
    
    private static void registerPlayer(SnakeGame game, int player_type, String player, String opponent) {
            if (player_type == TYPE_PLAYER) {
                game.loadPlayer(player);
            } else if (player_type == TYPE_OPPONENT) {
                game.loadOpponent(opponent);
            }
    }
    
    private static void createGameIFNotExists(SConnection connection, String game_name, int player_type, String player, String opponent) {
         if (current_games.containsKey(game_name)) {
            connection.addMessage("found game");
            SnakeGame game = current_games.get(game_name);
            if (!game.gameReady()) {
                registerPlayer(game, player_type, player, opponent);
                connection.addMessage("registering player...");
            }
         } else {
            connection.addMessage("starting new game");
            SnakeGame game = new SnakeGame();
            current_games.put(game_name, game);
            registerPlayer(game, player_type, player, opponent);
            connection.addMessage("registering player...");
         }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
            SConnection connection = new SConnection();
            if ((request.getParameter("player") != null) && (request.getParameter("opponent") != null) && (request.getParameter("type") != null)) {
                if (isNumeric(request.getParameter("type"))) {
                     int player_type = Integer.valueOf(request.getParameter("type"));
                     if (player_type == TYPE_PLAYER || player_type == TYPE_OPPONENT) {
                         String player = request.getParameter("player");
                         String opponent = request.getParameter("opponent");
                         connection.addMessage("Waiting: ");
                         if ((SessionListener.isUserOnline(player)) && (SessionListener.isUserOnline(opponent))) {
                             String game_name = player + "_" + opponent;
                             createGameIFNotExists(connection, game_name, player_type, player, opponent);
                             if (current_games.containsKey(game_name)) {
                                connection.addMessage("connecting..." + game_name);
                                connection.setGame(game_name);
                                connection.setState(SConnection.State.CN_Connected);
                             }
                         }
                     }
                }
            } else {
                connection.addMessage("player data not supplied");
            }
            ObjectOutputStream outputToApplet;
            response.setContentType("java-internal/" + SConnection.class.getName());
            outputToApplet = new ObjectOutputStream(response.getOutputStream());
            outputToApplet.writeObject(connection);
            outputToApplet.flush();
            outputToApplet.close(); 
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
    
    public static SnakeGame findGame(String game_name) {
           return (current_games.containsKey(game_name)) ? current_games.get(game_name) : null;
    }
    
}
