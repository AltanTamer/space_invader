package main;


import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainFrame extends JFrame implements ActionListener {

	JPanel cardPanel;
	CardLayout cardLayout;
	JMenuBar menubar; 
	JMenu file_menu;
	JMenu help_menu;
	
	JMenuItem register; 
	JMenuItem play_game; 
	JMenuItem high_score;
	JMenuItem quit;
	JMenuItem about;
	HighScores highScores = new HighScores();

	JLabel label;
	public boolean loggedIn;
		
		MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); 
		this.setTitle("Space Invader");

		
		JMenuBar menubar = new JMenuBar();
		JMenu file_menu = new JMenu("File");
		JMenu help_menu = new JMenu("Help");
		
		JMenuItem register = new JMenuItem("Register");
		JMenuItem play_game = new JMenuItem("Play Game");
		JMenuItem high_score = new JMenuItem("High Score");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem about = new JMenuItem("About");

		
		
		this.setJMenuBar(menubar);
		menubar.add(file_menu);
		menubar.add(help_menu);
		
		file_menu.add(register);
		file_menu.add(play_game);
		file_menu.add(high_score);
		file_menu.add(quit);
		help_menu.add(about);
		
		cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        
        StartPanel startPanel = new StartPanel();
        GamePanel gamePanel = new GamePanel();
        
        cardPanel.add(startPanel, "start");
        cardPanel.add(gamePanel, "game");

        cardLayout.show(cardPanel, "start");

        this.add(cardPanel);
		
		file_menu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {

			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		} );
		help_menu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub


			}

			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

				
		register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterFrame registerFrame = new RegisterFrame();
                registerFrame.setVisible(true);
                registerFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    	setLoggedIn(RegisterFrame.loggedIn);
                    }
                });
            }
		});
		play_game.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isLoggedIn()) {
					cardLayout.show(cardPanel, "game");
					gamePanel.requestFocusInWindow();
					gamePanel.startGameThread();
					gamePanel.gameExtras();
				}
				else {
		            JOptionPane.showMessageDialog(MainFrame.this,
		                    "Please log in or register to play the game.",
		                    "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});			
			
		
		high_score.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        HighScoresFrame highScoresFrame = new HighScoresFrame(highScores);
		        highScoresFrame.setVisible(true);
		    }
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "Emre Altan Tamer CSE 212 \n20200702046 School ID", "About", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});




		
		this.pack();
		this.setLocationRelativeTo(null);
		
 
		this.setVisible(true);

		
		
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

	

	}
}
