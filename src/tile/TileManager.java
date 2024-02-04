package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    private BufferedImage background; // Background image
    private int yOffset = 0; // Vertical offset of the background

    public TileManager(GamePanel gamePanel) {
        try {
            // Load the background image
            background = ImageIO.read(getClass().getResourceAsStream("/tiles/uzay1_complete_background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scroll(int speed) {
        yOffset += speed;
        if (yOffset >= background.getHeight()) {
            yOffset = 0; // Reset the offset when the background reaches the bottom
        }
    }

    public void draw(Graphics g) {
        // Draw the background image with the vertical offset
        g.drawImage(background, 0, yOffset, null);
        // Draw the background image again above the current one if needed
        if (yOffset > 0) {
            g.drawImage(background, 0, yOffset - background.getHeight(), null);
        }
    }
    public BufferedImage getTile() {
        return background;
    }
}
