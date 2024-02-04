package entity;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity {
	int x, y;
	double speed;
    private static final long COOLDOWN_DURATION = 500; // 0.5 second cooldown
    public long lastDamageTime = 0;
    public static final long FLASH_DURATION = 500;

    
	GamePanel gp;
	public BufferedImage still, moving;
	public BufferedImage enemy1;
	public int ProjecileSpeed =2;
	public String motion;
	public int health;
	public int current_health;
	
	public Entity(GamePanel gp) {
		this.gp=gp;
	}
    public void reduceHealth() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime > COOLDOWN_DURATION) {
           current_health--;

            
            lastDamageTime = currentTime;
        }
    }
}
