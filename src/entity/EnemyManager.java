package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import main.Details;
import main.GamePanel;
import main.HighScores;
import main.MainFrame;
import main.RegisterFrame;

public class EnemyManager{
    private List<Enemy> enemies;
    private Random random;
    private int spawnInterval; 
    private int spawnTimer;
    GamePanel gp;
    MainFrame mframe;
    HighScores highScores;
    Details details;
    private List<EnemyProjectile> enemyProjectiles;

    public EnemyManager(GamePanel gp,HighScores highScores, Details details) {
    	enemies = new ArrayList<>();
        random = new Random();
        spawnInterval = 180; 
        spawnTimer = spawnInterval;
        this.gp= gp;
        this.highScores = highScores; 
        this.details= details;
        Enemy enemy = new Enemy(gp,this ,0, 0); 
        enemies.add(enemy);
        enemyProjectiles = new ArrayList<>();

        
    }
    public List<Enemy> getEnemies(){
    	return enemies;
    }
    
    
    public void update() {
        spawnTimer--;

        if (spawnTimer <= 0) {
            spawnEnemy();
            spawnTimer = spawnInterval; 
        }

        List<Enemy> enemiesCopy = new ArrayList<>(enemies); 

        for (Enemy enemy : enemiesCopy) {
            enemy.update();

            if (enemy.getY() >= gp.screenlength) {
                removeEnemy(enemy); 
            }
            if(enemy.current_health == 0) {
                highScores.updateScore(RegisterFrame.getUsername(), highScores.getScore(RegisterFrame.getUsername()) + 10);
                details.addScore(10); 
            	removeEnemy(enemy);

            }
        }
        updateEnemyLasers();

    }
    private void updateEnemyLasers() {
        Iterator<EnemyProjectile> iterator = enemyProjectiles.iterator();
        while (iterator.hasNext()) {
        	EnemyProjectile laser = iterator.next();
            laser.update();
            if (laser.getY() > gp.screenlength) {
                iterator.remove(); 
            }
        }
    }
    public HighScores getHighScores() {
        return highScores;
    }


    private void spawnEnemy() {
        int x, y;
        int imageWidth = enemies.get(0).getEnemyImage().getWidth();
        int imageHeight = enemies.get(0).getEnemyImage().getHeight();

        do {
            x = random.nextInt(gp.screenwidth - imageWidth);
            y = 0; 
        } while (isCollision(x, y, imageWidth, imageHeight));

        Enemy enemy = new Enemy(gp, this, x, y);
        enemies.add(enemy);
    }

    private boolean isCollision(int x, int y, int width, int height) {
        Rectangle newEnemyHitbox = new Rectangle(x, y, width, height);

        for (Enemy enemy : enemies) {
            Rectangle existingEnemyHitbox = enemy.getHitbox();

            if (newEnemyHitbox.intersects(existingEnemyHitbox)) {
                return true; 
            }
        }

        return false; 
    }



    
    public void removeEnemy(Enemy enemy) {
        Rectangle enemyHitbox = enemy.getHitbox();

        enemies.remove(enemy);

}

    public void draw(Graphics2D g2) {
        List<Enemy> enemiesCopy = new ArrayList<>(enemies); 

        for (Enemy enemy : enemiesCopy) {
            enemy.draw(g2);
        }
        drawEnemyLasers(g2);

    }
    public void drawEnemyLasers(Graphics2D g2) {
        for (EnemyProjectile laser : enemyProjectiles) {
            laser.draw(g2);
        }
    }
    public void addEnemyLaser(EnemyProjectile laser) {
    	enemyProjectiles.add(laser);
    }
    public List<EnemyProjectile> getEnemyProjectiles() {
        return enemyProjectiles;
    }


}
