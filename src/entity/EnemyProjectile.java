package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class EnemyProjectile {
    private int x;
    private int y;
    private BufferedImage image;
    private int speed;
    private Rectangle hitbox;

    public EnemyProjectile(int x, int y, BufferedImage image, int speed) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.speed = speed;
        createHitbox();
    }

    private void createHitbox() {
        hitbox = new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void update() {
        y += speed;
        hitbox.setLocation(x, y);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, null);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }}
