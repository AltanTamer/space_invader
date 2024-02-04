package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import entity.Player;

public class KeyboardInp implements KeyListener{
	
	public boolean upP, downP, leftP, rightP, spaceP = false;
	public int mouseX ,mouseY;

	GamePanel gp;
	
	
	public KeyboardInp(GamePanel gp) {
		this.gp=gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code== KeyEvent.VK_W) {
			upP = true;

			}
			
		if(code== KeyEvent.VK_A) {
			leftP = true;
			}
		
		if(code== KeyEvent.VK_S) {
			downP = true;
		}
		if(code== KeyEvent.VK_D) {
			rightP = true;
		}
		if(code== KeyEvent.VK_ESCAPE) {
			if(gp.state) {
				gp.state = false;
			}else {		gp.state = true;	}
		}
		if(code== KeyEvent.VK_SPACE) {
			 spaceP = true;


		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code== KeyEvent.VK_W) {
			upP = false;
			}
			
		if(code== KeyEvent.VK_A) {
			leftP = false;
			}
		
		if(code== KeyEvent.VK_S) {
			downP = false;
		}
		if(code== KeyEvent.VK_D) {
			rightP = false;
		}
		if(code== KeyEvent.VK_SPACE) {
			 spaceP = false;

		}
		
	}
	
}	
