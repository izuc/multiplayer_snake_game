package snakegame;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.event.*;
import java.util.*;

/**
*	The Snake Panel class handles the rendering and processing of game elements. 
*	It contains a reference to the SnakeGame, and does not contain any direct data relating to the game play; therefore allowing the game to create a different game without having to recreate the Snake Panel itself.
*	The Snake Panel class processes the game items, (it doesn�t detect collisions directly) and determines whether the item has been picked up and performs an associated action associated with the item type.
*	@author: Lance Baker
**/

public class SnakePanel extends JInternalFrame implements Runnable, Constants {
		private static final long serialVersionUID = 1L;
		private static Image gameImage;
		
		private SnakeGame game; // Stores the current game, which gets loaded from the load game method.
		
		public SnakePanel() {
				super(MAIN_HEADING, false, true, false, false);
				this.setFocusable(true); // Makes it so the game's panel can be focused on.
				this.setControls(); // This calls a method which registers the keyboard actions to the panel.
				this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // Sets the initial layout.
				this.getContentPane().add(new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource(IMAGES_PATH + BACKGROUND_IMG))));
				this.setSize(480, 400);
		}
                
		
		private void setControls() {
				// This is a inner Action class for the registering of the current movement direction for the snake.
				final Action movement = new AbstractAction() {
						public static final long serialVersionUID = 1L;
						public void actionPerformed(ActionEvent e) { // The actionPerformed method gets triggered when the action has occured.
								SnakePlayer.Compass currentDirection = SnakePlayer.Compass.valueOf(e.getActionCommand()); // Matches the action command to a compass enum.
								if (currentDirection != game.getPlayer().getDirection()) { // Checks whether its a different direction before proceeding, to avoid holding down the KeyBoard button.
									game.getPlayer().setDirection(currentDirection); // Sets the new direction based off the converted ActionCommand
									//updateGame(); // Updates the Game
								}
						}
				};
				
				// Binds the KeyBoard Directional Keys to a Specified Compass Direction. I Just did it this way to try something different.
				this.registerKeyboardAction(movement, SnakePlayer.Compass.NORTH.toString(), KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_FOCUSED);
				this.registerKeyboardAction(movement, SnakePlayer.Compass.SOUTH.toString(), KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_FOCUSED);
				this.registerKeyboardAction(movement, SnakePlayer.Compass.WEST.toString(), KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), JComponent.WHEN_FOCUSED);
				this.registerKeyboardAction(movement, SnakePlayer.Compass.EAST.toString(), KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), JComponent.WHEN_FOCUSED);
				
		}
		
		// This method gets called when the user presses a keyboard directional button, and when the thread updates the game.
		// Therefore it will need to be synchronized to only allow one execution of the method to occur at the same time.
		private synchronized void updateGame() {
				try {
                                        this.game.pushData();
                                        this.game.pullData();
					if (gameImage == null) gameImage = this.getContentPane().createImage(PANEL_WIDTH, (PANEL_HEIGHT + HUD_HEIGHT));
					if (gameImage != null) { // If the game image is not null it will render the screen and perform actions to the snake game.
							Graphics g = this.gameRender(); // Renders the game, and adds a background image, returns the Graphics from the gameImage.
                                                        this.game.getPlayer().moveSnake(); // Moves the snake by one point which is based on the current direction.
                                                        this.drawSnake(g, this.game.getDisplay().getPlayer().getSnakeBody()); // Draws the snake to the screen.
                                                        this.drawSnake(g, this.game.getDisplay().getOpponent().getSnakeBody());
                                                        this.drawHud(g); // Draws the game statistics HUD to the game.
                                                        this.paintScreen(); // Paints the screen         
					}
				} catch (Exception ex) {}
		}
		
		// This method is used to get the game graphics from the gameImage. It also adds the game background image to the graphics returned.
		private Graphics gameRender() {
				Graphics g = gameImage.getGraphics(); // Gets the Graphics from the gameImage.
				Image image = new ImageIcon(this.getClass().getClassLoader().getResource(IMAGES_PATH + BACKGROUND_IMG)).getImage();
				g.drawImage(image, 0, 0, image.getWidth(this.getContentPane()), image.getHeight(this.getContentPane()), this.getContentPane());
				return g;
		}
		
		// This method draws the Heads Up Display unit, which contains the statistics of the current game.
		private void drawHud(Graphics g) {				
				
		}
		
		public void drawSnake(Graphics g, ArrayList<Point> points) {
				if (points.size() > ZERO) { // If the size is above zero.
					ImageIcon body = new ImageIcon(getClass().getClassLoader().getResource(IMAGES_PATH + BODY_CELL + PNG)); // Sets the imageicon for the body cells.
					ImageIcon head = new ImageIcon(getClass().getClassLoader().getResource(IMAGES_PATH + SNAKE_HEAD + PNG)); // Sets the imageicon used for the snake head.
					for (int i = 1; i < points.size(); i++) { // Iterates for each point in the Snake.
						body.paintIcon(this, g, points.get(i).x, points.get(i).y); // Paints the snake body cell.
					}
					head.paintIcon(this, g, points.get(ZERO).x, points.get(ZERO).y); // Adds the snake head.
				}
		}
		
		public void start_display(SnakeGame game) {
                                this.game = game;
				new Thread(this).start(); // Launches a new thread.
		}
		
		public void run() { 
				while (true) { // Loops whilst the game is running.
					try {
                                            this.updateGame(); // Updates the game and renders the graphics.
                                            Thread.sleep(this.game.getPlayer().getSpeed());	// sleeps for the time period from the getSpeed method.
					} catch(InterruptedException ex) { }
				}
		}
		
		// This method paints the graphics to the JPanel.
		public void paintScreen() { 
				Graphics g;
				g = this.getContentPane().getGraphics(); // Gets the graphics from the JPanel
				if ((g != null) && (gameImage != null)) {
					if (this.isVisible()) {
						g.drawImage(gameImage, ZERO, ZERO, null); // Draws the image to the graphics.
					}
				}
				Toolkit.getDefaultToolkit().sync();
				g.dispose();
		}
		
		// Returns the current game.
		public SnakeGame getCurrentGame() {
				return this.game;
		}
		
}
