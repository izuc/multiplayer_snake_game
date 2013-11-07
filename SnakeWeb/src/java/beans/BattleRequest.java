package beans;
import java.io.Serializable;

public class BattleRequest implements Serializable, Constants {
        public static final int BATTLE_PENDING = 1;
        public static final int BATTLE_DENIED = 2;
        public static final int BATTLE_ACCEPTED = 3;
        
        private User player;
        private User opponent;
        private int status;
        
        public BattleRequest() {
            this.player = new User();
            this.opponent = new User();
            this.status = BATTLE_PENDING;
        }
        
        public BattleRequest(User player, User opponent) {
            this.player = player;
            this.opponent = opponent;
            this.status = BATTLE_PENDING;
        }

        public User getPlayer() {
            return player;
        }

        public void setPlayer(User player) {
            this.player = player;
        }

        public User getOpponent() {
            return opponent;
        }

        public void setOpponent(User opponent) {
            this.opponent = opponent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
}
