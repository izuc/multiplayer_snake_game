/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snakegame;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class SnakeDisplay implements Serializable {
        private SnakePlayer player;
        private SnakePlayer opponent;
        
        
        public SnakeDisplay() {
                
        }
        
        public void start_game() {
                this.player.start();
                this.opponent.start();
        }
        
        public void setPlayer(SnakePlayer player) {
                this.player = player; // Sets the player name.
        }
        
        public void setOpponent(SnakePlayer player) {
                this.opponent = player; // Sets the player name.
        }

        public SnakePlayer getPlayer() {
                return this.player; // Gets the player name.
        }
        
        public SnakePlayer getOpponent() {
                return this.opponent; // Gets the player name.
        }
}
