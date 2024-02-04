package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final int originaltilesize = 18;
	final int scale = 3;
	public final int tilesize = originaltilesize * scale; // 54
	public final int screencol = 20;
	public final int screenrow = 15;
	public final int screenwidth = tilesize * screencol;  // 1080
	public final int screenlength = tilesize * screenrow; // 810
    
    private BufferedImage image;

	
	public StartPanel()  {
		
		this.setPreferredSize(new Dimension(screenwidth, screenlength));
		this.setBackground(Color.black);
		try {                
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/space_invaders_title22.png"));
			
	       } catch (IOException e) {
				e.printStackTrace();
	       }
		this.setFocusable(true);

	}
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0,screenwidth ,screenlength , this);
        

    }
}
//#290254