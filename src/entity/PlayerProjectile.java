package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class PlayerProjectile extends Entity {
    private GamePanel gp;
	private int x;
    private int y;
    private BufferedImage image;

    
    public PlayerProjectile(int x, int y, int speed, BufferedImage image, GamePanel gp) {
        super(gp);
        this.gp=gp;
    	this.x = x;
        this.y = y;
        this.ProjecileSpeed = 3;
        this.image = image;
    }
    
    public void update() {
        y -= ProjecileSpeed;
    }
	public void getPlayerProjectileImage() {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/xwing_laser.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void draw(Graphics2D g2) {
        int projectileSize = gp.tilesize * 2 / 3; 
        int projectileX = x+8; 
        int projectileY = y - projectileSize; 

        g2.drawImage(image, projectileX, projectileY, projectileSize, projectileSize, null);

        
    }
}
