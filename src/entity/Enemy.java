package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Enemy extends Entity {

    public BufferedImage enemyImage;
    public BufferedImage laserImage;

    EnemyManager enemyManager; 
	Rectangle hitbox;
	private long lastProjectile;
	private static final long PROJECTILE_TIME = 2000; 
	public int projectileSpeed = 5;


    public Enemy(GamePanel gp,EnemyManager enemyManager, int x, int y) {
        super(gp);
        this.enemyManager = enemyManager;
        initialize(x, y);
        createHitbox();
        lastProjectile = System.currentTimeMillis() - (long) (Math.random() * PROJECTILE_TIME);

        
    }

    private void initialize(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 2; 
        motion = "moving";
        health = 2;// 
        current_health = health; 
        loadEnemyImage(); 
    }

	
    

    private void loadEnemyImage() {
        try {
            laserImage = ImageIO.read(getClass().getResourceAsStream("/icons/enemy_laser.png"));
            enemyImage = ImageIO.read(getClass().getResourceAsStream("/icons/enemy1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getEnemyImage() {
        return enemyImage;
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }	
    public void createHitbox() {
        hitbox = new Rectangle(x, y, enemyImage.getWidth(), enemyImage.getHeight());
    }
    public Rectangle getHitbox() {
        return hitbox;
    }



    public void update() {

       
    	if (motion.equals("moving")) {
            y += speed; 
        
            if (y>= gp.screenlength) {
            	enemyManager.removeEnemy(this);
            }
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastProjectile >= PROJECTILE_TIME) {
            lastProjectile = currentTime; 
            shootProjectile();
            
        }
    	
        hitbox.setLocation(x, y);
    }
    private void shootProjectile() {
        int laserX = x + enemyImage.getWidth() / 2 - laserImage.getWidth() / 2; 
        int laserY = y + enemyImage.getHeight();

        EnemyProjectile laser = new EnemyProjectile(laserX, laserY, laserImage, projectileSpeed);
        enemyManager.addEnemyLaser(laser);
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        
        if (current_health < health) {
            long currentTime = System.currentTimeMillis();
            
            if (currentTime - lastDamageTime < FLASH_DURATION) {
                if (currentTime % 300 < 200) {
                    image = enemyImage;
                } else {
                    image = null;
                }
            } else {
                image = enemyImage;
            }
        } else {
            image = enemyImage;
        }
        
        if (image != null) {
            g2.drawImage(image, x, y, null);

        }
        
    }
}
