package snakegame;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */

public class SConnection implements Serializable {

    private static final String EMPTY_STRING = "";
    public enum State {CN_Waiting, CN_Connected};
    private ArrayList<String> messages;
    private State state;
    private String game_name;

    public SConnection() {
            this.state = State.CN_Waiting;
            this.messages = new ArrayList<String>();
            this.game_name = EMPTY_STRING;
    }
    
    public void addMessage(String message) {
        this.messages.add(message);
    }
    
    public ArrayList<String> getMessages() {
        return this.messages;
    }
    
    public void setGame(String game) {
        this.game_name = game;
    }
    
    public String getGame() {
        return this.game_name;
    }
    
    public void setState(State state) {
        this.state = state;
    } 
    
    public State getState() {
        return this.state;
    }
}
