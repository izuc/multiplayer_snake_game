package snakegame;

public class SnakeGame implements Constants {
          
        private SnakeDisplay display;
        
        public SnakeGame() {
                this.display = new SnakeDisplay();
        }
        
        public SnakeDisplay getDisplay() {
                return this.display;
        }
        
        public void loadPlayer(String player) {
                if (this.display.getPlayer() == null) {
                    this.display.setPlayer(new SnakePlayer(player, SnakePlayer.Type.PLAYER));
                }
        }
        
        public void loadOpponent(String opponent) {
                if (this.display.getOpponent() == null) {
                    this.display.setOpponent(new SnakePlayer(opponent, SnakePlayer.Type.OPPONENT));
                }
        }
   
        public boolean gameReady() {
                if ((this.display.getOpponent() != null) && (this.display.getPlayer() != null)) {
                    return true;
                }
                return false;
        }
}