package snakegame;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class SnakePlayer implements Constants, Serializable {
        
        public enum Type { PLAYER, OPPONENT };
        public enum Compass { NORTH, SOUTH, EAST, WEST };
        private String userName;
        private Snake snake;
	private Compass direction;
        private Type type;
        private int energy; // Percent representing the amount of caffeine in the snakes body. 0 % will cause the snake to stop, and 100% will cause the speed of the snake to increase to a max. Caffeine can only be collected from energy drinks.
        private boolean lock_enabled;
      
        public SnakePlayer() {
                this.userName = "empty";
        }
        
        public SnakePlayer(String userName, Type type) {
                this.userName = userName;
                this.type = type;
                this.lock_enabled = false;
                this.snake = new Snake();
                this.start();
        }
         
        public void start() {
                this.snake.clear(); // Clears the snake.
                this.setDirection((this.type == Type.PLAYER) ? Compass.NORTH : Compass.SOUTH ); // Sets the default starting direction to north.
                Point start = ((this.type == Type.PLAYER) ? new Point((PANEL_WIDTH / 2), (PANEL_HEIGHT / 2)) : new Point((PANEL_WIDTH / 3), (PANEL_HEIGHT / 3)));
                this.snake.add(start); // Adds a starting point which is the screen's center location.
        }
        
        public void setDirection(Compass direction) {
                // The following statement restricts the snake to certain navigation rules.
                if (((direction == Compass.WEST) && (this.direction != Compass.EAST)) ||
                        ((direction == Compass.EAST) && (this.direction != Compass.WEST)) ||
                        ((direction == Compass.NORTH) && (this.direction != Compass.SOUTH)) ||
                        ((direction == Compass.SOUTH) && (this.direction != Compass.NORTH))) {
                        this.direction = direction;
                }
        }

        // This method is used to move the snake. It decrements the energy level for each movement the snake makes.
        public void moveSnake() {
                this.energy = (this.energy > 0) ? this.energy -= 5 : 0;
                if (!this.lock_enabled) {
                    this.snake.move(this.getDirection()); // Moves the snake in one incremented point following the direction passed.
                }
        }
        
        public Compass getDirection() {
                        return this.direction; // Gets the current compass direction of the snake.
        }

        // This method is used to detect whether the snake collided with itself. It has to be checked seperatly due to the exception that will be thrown.
        public boolean detectSnakeCollision() {
                      return (this.snake.collision(this.snake.get(ZERO), SNAKE_SIZE, true)); // If the snake collided with its own body, then it throws a Collision Exception. 
        }

        // Returns the snake as a ArrayList of Point.
        public ArrayList<Point> getSnakeBody() {
                        return this.snake;
        }
        
        public Snake getSnake() {
               return this.snake;
        }
        
        public int getSpeed() {	
                        return SNAKE_SPEED; 
        }
        
        public String getPlayerName() {
                return this.userName;
        }
        
        @Override
        public String toString() {
               return this.userName;
        }
}
