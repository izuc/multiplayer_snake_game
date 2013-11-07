/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package processing;

import snakegame.SnakeGame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import snakegame.SnakeDisplay;
import snakegame.SnakePlayer;
/**
 *
 * @author Admin
 */
public class ProcessGameDisplayServlet extends HttpServlet {
    
    public static final int TYPE_PLAYER = 1;
    public static final int TYPE_OPPONENT = 2;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
                try {
                        if ((request.getParameter("type") != null) && (request.getParameter("game") != null)) {
                            int type = Integer.parseInt(request.getParameter("type"));
                            SnakeGame game = GameControllerServlet.findGame(request.getParameter("game"));
                            if (game != null) {
                                if (game.gameReady()) {
                                   
                                }
                                ObjectInputStream inputFromApplet = new ObjectInputStream(request.getInputStream());
                                SnakePlayer player = (SnakePlayer)inputFromApplet.readObject();
                                if ((player != null) && (!player.toString().equalsIgnoreCase("empty"))) {
                                        if (type == GameControllerServlet.TYPE_PLAYER) {
                                            game.getDisplay().setPlayer(player);
                                        } else {
                                            game.getDisplay().setOpponent(player);
                                        }
                                }
                                SnakeDisplay display = game.getDisplay();
                                response.setContentType("java-internal/" + SnakeDisplay.class.getName());
                                OutputStream out = response.getOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(out);
                                oos.writeObject(display);
                                oos.flush();
                                oos.close(); 
                            }
                        }
                } catch(Exception ex) {
                    System.out.println(ex.getMessage());
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
