package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
*	The Snake game frame is the starting point for the application. 
*	The class contains a MenuAction enumerated type, which completes actions based on the Menu item selected. 
*	The class stores the games in a structure of LinkedList<SnakeGame> and can be serialized and reloaded back into the system. 
*	The snake game frame creates the initial layout, and firstly instantiates a Snake Panel with no game loaded. 
*	The SnakeGame is later created, when the user selects the �New Game� option; then the Snake Panel receives the SnakeGame created and begins the game play.
*	@author: Lance Baker.
**/

public class SnakeGameFrame extends JApplet implements Constants {
		private static final long serialVersionUID = 1L;
		
		private SnakePanel snakePanel; // The snake panel
		private JDesktopPane desktop;
                		
		public SnakeGameFrame() {
                                this.desktop = new MyDesktopPane(); //a specialized layered pane
				this.desktop.setDesktopManager(new DefaultDesktopManager());
				this.desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
				this.setContentPane(desktop);
				this.setSize(new Dimension(480, 400));
                }
                
		public void createGame(String game_name, String player, String opponent, String type) {	
                                    this.snakePanel = new SnakePanel();
                                    this.snakePanel.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                            snakePanel.requestFocus(); // Requests focus.
                                            }
                                    });
                                    snakePanel.setSize(480, 400);
                                    this.showInternalFrame((JInternalFrame)snakePanel); // Adds the snake panel to the default frame container.
                                    this.snakePanel.start_display(new SnakeGame(game_name, player, opponent, type)); // Adds the SnakeGame to the SnakePanel.
                                    this.snakePanel.requestFocus();
		}
                
                private void showLoading() {
                        if ((this.getParameter("player") != null) && (this.getParameter("opponent") != null && this.getParameter("type") != null)) {
                            this.showInternalFrame(new EstablishGame(this, this.getParameter("player"), this.getParameter("opponent"), this.getParameter("type")));
                        } else {
                            JOptionPane.showMessageDialog(this, "no player data supplied - cannot connect");
                        }
                }
		
		private void showInternalFrame(JInternalFrame frame) {
				this.desktop.add(frame);
                                ((BasicInternalFrameUI)frame.getUI()).setNorthPane(null);
				frame.show();
				try {
					frame.setSelected(true);
					frame.setMaximum(true);
				} catch (java.beans.PropertyVetoException e) {}
		}
		
		public void setGameVisible(boolean value) {
				if (snakePanel != null) {
					snakePanel.setVisible(value);
					if (value) snakePanel.requestFocus();
				}
		}
		
                @Override
		public void init() { 
				int inset = 50;
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				setBounds(inset, inset,
				screenSize.width  - inset*2,
				screenSize.height - inset*2);
                                this.showLoading();
                                this.setVisible(true);
		}
                
		
		private class MyDesktopPane extends JDesktopPane {
				private static final long serialVersionUID = 1L;
				
				public MyDesktopPane() {
						
				}
                                               
                                @Override
				protected void paintComponent(Graphics g) {
						super.paintComponent(g);
				}
		} 
}


class EstablishGame extends JInternalFrame implements Runnable {
    private static final String SERVLET_URL = "http://www.snakemultiplayer.com/game.jsp";
    private URLConnection servletConnection;
    private SConnection snake_info;
    private JPanel output;
    private SnakeGameFrame parent;
    
    private String player;
    private String opponent;
    private String type;
    
    
    public EstablishGame(SnakeGameFrame parent, String player, String opponent, String type) {
        this.snake_info = new SConnection();
        this.player = player;
        this.opponent = opponent;
        this.type = type;
        this.output = new JPanel();
        
        Border etchedBdr = BorderFactory.createEtchedBorder();
        Border titledBdr = BorderFactory.createTitledBorder(etchedBdr, "Game Loading");
        Border emptyBdr  = BorderFactory.createEmptyBorder(10,10,10,10);
        Border compoundBdr = BorderFactory.createCompoundBorder(titledBdr, emptyBdr);
        output.setBorder(compoundBdr);
        output.setPreferredSize(new Dimension(200, 50));
        this.getContentPane().add(output, BorderLayout.CENTER);
        this.parent = parent;
        new Thread(this).start();
    }
    
    private String getURL() {
        return SERVLET_URL + "?player="+this.player+"&opponent="+this.opponent+"&type="+this.type;
    }
    
    public void getConnectionInfo() {
            try {
                URL servletURL = new URL(this.getURL());
                this.servletConnection = servletURL.openConnection();
                this.servletConnection.setDoInput(true);
                this.servletConnection.setUseCaches (false);
                this.servletConnection.setRequestProperty("Content-Type","multipart;application/octet-stream");
                ObjectInputStream inputFromServlet = new ObjectInputStream(servletConnection.getInputStream());
                this.snake_info = (SConnection)inputFromServlet.readObject();
                for (String message : this.snake_info.getMessages()) {
                    this.output.add(new JLabel(message), BorderLayout.CENTER);
                }
                if (this.snake_info.getState() == SConnection.State.CN_Connected) {
                    parent.createGame(this.snake_info.getGame(), this.player, this.opponent, this.type);
                    this.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage());
            }             
    }
    
    public void run() {
        while(this.snake_info.getState() == SConnection.State.CN_Waiting) {
            try { 
               this.getConnectionInfo();
               Thread.sleep(1000);
            } catch( InterruptedException e ) {
                System.out.println("Interrupted Exception caught");
            } 
        }
    }
    
}