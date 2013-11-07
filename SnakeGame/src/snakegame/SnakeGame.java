package snakegame;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

public class SnakeGame implements Constants, Serializable {
        private static final String SERVLET_URL = "http://www.snakemultiplayer.com/process_game.jsp";
        public static final int TYPE_PLAYER = 1;
        public static final int TYPE_OPPONENT = 2;
        
        private SnakeDisplay display;
        private URLConnection servletConnection;
        private String url;
        private int player_type;
        private SnakePlayer player;
        private String game_name;
        
        public SnakeGame(String game_name, String player, String opponent, String type)  {
                this.url = SERVLET_URL + "?game="+game_name+"&type="+type;
                this.player_type = Integer.valueOf(type);
                this.player = new SnakePlayer();
                this.game_name = game_name;
                this.pushData();
                this.pullData();
        }
        
        public void pushData() {
                    try {
                        URL servletURL = new URL(this.url);
                        this.servletConnection = servletURL.openConnection();
                        this.servletConnection.setDoOutput(true);
                        this.servletConnection.setUseCaches (false);
                        this.servletConnection.setDoInput(true);
                        this.servletConnection.setRequestProperty("Content-Type","multipart;application/octet-stream");
                        ObjectOutputStream outputToServlet = new ObjectOutputStream(this.servletConnection.getOutputStream());
                        outputToServlet.writeObject(this.player);
                        outputToServlet.flush();
                    } catch (Exception ex ) {
                    }
        }
        
        public void pullData() {
                    try {
                        InputStream in = this.servletConnection.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(in);
                        Object object = ois.readObject();
                        this.display = (SnakeDisplay)object;
                        this.player = (this.player_type == TYPE_PLAYER) ? this.display.getPlayer() : this.display.getOpponent();
                    } catch (Exception ex ) { 
                    }
        }
        
        public SnakeDisplay getDisplay() {
                return this.display;
        }
        
        public SnakePlayer getPlayer() {
                return this.player;
        }
        
//        public SnakePlayer.Compass getDirection() {
//                return ((this.player_type == TYPE_PLAYER)? this.getDisplay().getPlayer().getDirection() :
//                        this.getDisplay().getOpponent().getDirection());
//        }
//        
//        public void setDirection(SnakePlayer.Compass direction) {
//                SnakePlayer player = ((this.player_type == TYPE_PLAYER)? this.getDisplay().getPlayer() : this.getDisplay().getOpponent());
//                player.setDirection(direction);
//        }
}