package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyboardInp;

public class Player extends Entity {
	
	GamePanel gp;
	KeyboardInp keyInp;
	Rectangle hitbox;

	List<PlayerProjectile> projectiles;
	public boolean firing = false;
	
	public Player(GamePanel gp, KeyboardInp keyInp) {		
		super(gp);
		this.keyInp=keyInp;
		this.gp=gp;

		projectiles = new ArrayList<>();

		
		setValues();
		getPlayerImage();
		createHitbox();
	}
	
	public void setValues() {
		
		x = (gp.screenwidth-gp.tilesize)/2;
		y = gp.screenlength*2/3;
		speed = 4;
		motion = "still";
		
		health = 3;
		current_health = health;	

	}
	
	public void getPlayerImage() {
		
		try {
			still = ImageIO.read(getClass().getResourceAsStream("/player/çakma xwing.png"));
			moving = ImageIO.read(getClass().getResourceAsStream("/player/knockoff_xwing.png"));

		}
		catch(IOException e) {
			e.printStackTrace();
			
		}

		
	}
	public void setPosition(int newX, int newY) {
	    x = newX;
	    y = newY;
	    hitbox.setLocation(x, y);
	}


	public void createHitbox() {
        hitbox = new Rectangle(x, y, gp.tilesize, gp.tilesize);

	}
	public int getHealth() {
		return current_health;
		
	}
	
	public void update() {
		
		if(keyInp.upP == true) {
			motion = "moving";
			y -= speed;

			
			if (y < 0) {
				y =0;
			}
		}
		else if(keyInp.downP == true) {
			motion = "moving";
			y += speed;
			if (y > gp.screenlength-gp.tilesize-speed) {
				y = gp.screenlength-gp.tilesize;
			}
			
		}		
		else if(keyInp.leftP == true) {
			motion = "moving";

			x -= speed;
			if (x < 0) {
				x = 0;
			}
		}
		else if(keyInp.rightP == true) {
			motion = "moving";

			x += speed;
			if(x > gp.screenwidth-gp.tilesize-speed+2) {
				x = gp.screenwidth-gp.tilesize+2;
			}
		}
		else if(keyInp.rightP== false && keyInp.leftP== false &&keyInp.downP== false &&keyInp.upP== false ) {
			motion = "still";
		}
        if (keyInp.spaceP && !firing) {
        	firing = true;
            fireProjectile();
        } else if (!keyInp.spaceP) {
            firing = false;
        }
	    for (PlayerProjectile projectile : projectiles) {
	        projectile.update();}

	    
	    hitbox.setLocation(x, y);
	}
	public void draw(Graphics g2) {
	    BufferedImage image = null;
	    
	    if (current_health < health) {
	        long currentTime = System.currentTimeMillis();
	        
	        if (currentTime - lastDamageTime < FLASH_DURATION) {
	            if (currentTime % 300 < 200) {
	                image = moving;
	            } else {
	                image = null;
	            }
	        } else {
	            image = moving;
	        }
	    } else {
	        switch (motion) {
	            case "still":
	                image = still;
	                break;
	            case "moving":
	                image = moving;
	                break;
	        }
	    }
	    
	    if (image != null) {
	        g2.drawImage(image, x, y, gp.tilesize, gp.tilesize, null);
	    }
	    Graphics2D g2d = (Graphics2D) g2;
	    List<PlayerProjectile> projectilesCopy = new ArrayList<>(projectiles);
	    
	    for (PlayerProjectile projectile : projectilesCopy) {
	        projectile.draw(g2d);
	    }


	}

    public void fireProjectile() {
        int projectileX = x ;
        int projectileY = y;
        PlayerProjectile projectile = new PlayerProjectile(projectileX, projectileY, ProjecileSpeed ,null, gp);
        projectile.getPlayerProjectileImage();
        projectiles.add(projectile);
        gp.playSound(1);

    }
    public List<PlayerProjectile> getProjectiles() {
        return projectiles;
    }
	public Rectangle getHitbox() {
		return hitbox;
	}



}
