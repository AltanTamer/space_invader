package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Details {
    GamePanel gp;
    Font info;
    Font pause;
    Graphics2D g2;
    Color overlayColor;
    BufferedImage health;
    int drawCount;
    public int seconds;
    public int score;

    public Details(GamePanel gp) {
        this.gp = gp;

        info = new Font("Cambria", Font.PLAIN, gp.tilesize / 2);
        pause = new Font("Cambria", Font.PLAIN, gp.tilesize * 2);
        overlayColor = new Color(0, 0, 0, 0.5f); 
        getHealthImage();
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(info);
        g2.setColor(Color.WHITE);

        if (!gp.state) { 
            pauseScreen();
            drawDimmedScreen();
        }

        int healthIconX = 20;  
        int healthIconY = 20;  
        int healthIconSize = 81;  
        g2.drawImage(health, healthIconX, healthIconSize - gp.tilesize * 3 / 2, healthIconSize, healthIconSize, null);

        g2.setColor(Color.WHITE); 
        g2.setFont(info); 

        g2.drawString("x " + gp.player.getHealth(), healthIconX + healthIconSize - 10, healthIconY + healthIconSize - gp.tilesize + 10);
        
        int scoreX = gp.screenwidth - gp.tilesize * 4;  
        int scoreY = 40;  
        g2.drawString("Score: " + gp.details.score, scoreX, scoreY);
    }
    public void addSeconds() {
        seconds++;
    }
    public void addScore(int points) {
        score += points;
    }

    public void pauseScreen() {
        g2.setFont(pause);
        g2.setColor(Color.WHITE); 
        g2.drawString("PAUSED", (gp.screenwidth - gp.tilesize) * 1 / 3, gp.screenlength / 2);
    }

    public void drawDimmedScreen() {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, gp.screenwidth, gp.screenlength);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }

    public void getHealthImage() {
        try {
            health = ImageIO.read(getClass().getResourceAsStream("/icons/health_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
