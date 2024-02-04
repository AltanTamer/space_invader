package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import entity.Enemy;
import entity.EnemyManager;
import entity.EnemyProjectile;
import entity.Player;
import entity.PlayerProjectile;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	final int originaltilesize = 18;
	final int scale = 3;
	public final int tilesize = originaltilesize * scale; // 54
	public final int screencol = 20;
	public final int screenrow = 15;
	public final int screenwidth = tilesize * screencol;  // 1080
	public final int screenlength = tilesize * screenrow; // 810
	private int drawCount = 0;
	private boolean gameOver = false;
    Font End;


	int FPS = 60;
		
	TileManager tileBack = new TileManager(this);
	KeyboardInp keyInp = new KeyboardInp(this);
   

	Details details = new Details(this);
	Player player = new Player(this, keyInp);
	EnemyManager enemyManager;
	
	Thread gameThread;

	public boolean state; 
	
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenwidth, screenlength));
		this.setBackground(Color.black);
		this.addKeyListener(keyInp);
		this.setFocusable(true);
        HighScores highScores = new HighScores();
		enemyManager = new EnemyManager(this,highScores,details);
        End = new Font("Cambria", Font.PLAIN,tilesize * 2);


		
		startGameThread();

	}


	public void gameExtras() {
		state = true;
	}
	
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}


	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double nextDraw = System.nanoTime()+ drawInterval;
		long timer = 0;
		drawCount = 0;
		
		
		while(gameThread != null) {
			update();
			repaint();
			try {
				double remaining = nextDraw - System.nanoTime();
				remaining = remaining/1000000;
				if(remaining <0) {
					remaining = 0;
				}
				Thread.sleep((long)remaining);
				nextDraw += drawInterval;
			}catch (InterruptedException e) {
				e.printStackTrace();
				
			}

		}
	
	}


	public void update() {
		
		if(state) {
			player.update();
			enemyManager.update();
            updateProjectiles();
            repaint();
            if (player.getHealth() <= 0) {
                playSound(2);
            	gameOver = true;
                state = false;
            }

		} else {		}
		for(Enemy enemy : enemyManager.getEnemies()) {
			Rectangle playerHitbox = player.getHitbox();
			Rectangle enemyHitbox = enemy.getHitbox();
		     if (playerHitbox.intersects(enemyHitbox)) {
		         player.reduceHealth();
		         enemy.reduceHealth();

	            }


		}

	}


    private void updateProjectiles() {
        List<PlayerProjectile> projectilesToRemove = new ArrayList<>();
        Rectangle playerHitbox = player.getHitbox();
        List<EnemyProjectile> enemyProjectiles = enemyManager.getEnemyProjectiles();
        for (EnemyProjectile projectile : enemyProjectiles) {
            Rectangle projectileHitbox = projectile.getHitbox();
            
            if (playerHitbox.intersects(projectileHitbox)) {
                player.reduceHealth();
                enemyProjectiles.remove(projectile);
                break;
            }
        }
        for (PlayerProjectile projectile : player.getProjectiles()) {
            projectile.update();
            if (projectile.getY() < 0) {
                projectilesToRemove.add(projectile);
            } else {
                for (Enemy enemy : enemyManager.getEnemies()) {
                    Rectangle projectileHitbox = new Rectangle(projectile.getX()+tilesize/2, projectile.getY(), tilesize*2/3,tilesize*2/3);
                    Rectangle enemyHitbox = enemy.getHitbox();
                    if (projectileHitbox.intersects(enemyHitbox)) {
                        enemy.reduceHealth();
                        projectilesToRemove.add(projectile);


                        break;
                    }
                }
            }
        }
        player.getProjectiles().removeAll(projectilesToRemove);
    }
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileBack.scroll(1);
		tileBack.draw(g2);
		
		player.draw(g2);

        enemyManager.draw(g2);
        enemyManager.drawEnemyLasers(g2);
        drawProjectiles(g2);

		details.draw(g2);

		if (gameOver) {
	        g2.setColor(Color.BLACK);
	        g2.fillRect(0, 0, screenwidth, screenlength);
	        g2.setColor(Color.WHITE);
	        g2.setFont(End);
	        g2.drawString("YOU DIED", screenwidth / 2 - tilesize*4, screenlength / 2);
	    }else {  }
		
		g2.dispose();

	}
    private void drawProjectiles(Graphics2D g2) {
        for (PlayerProjectile projectile : player.getProjectiles()) {
            projectile.draw(g2);
        }
    }


	public int getDrawCount() {
        return drawCount;
    }


	public Object getEnemyManager() {
		return null;
	}
	public void playSound(int soundIndex) {
        try {
            String soundPath;
            switch (soundIndex) {
                case 1:
                    soundPath = "laserShoot.wav";
                    break;
                case 2:
                    soundPath = "gameOver.wav";
                    break;

                default:
                    return; 
            }

            File soundFile = new File(soundPath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + soundPath);
                return;
            }

            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));

            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

	

}