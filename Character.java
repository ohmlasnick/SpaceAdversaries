package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character {
	
	public int pos_x, pos_y;
	public int velocity;
	public int HP;
	public BufferedImage sprite;
	Display disp;
	public double explosionCounter = 1;
	public boolean DEAD = false;
	
	public Character(Display disp) {
		this.disp = disp;
	}
	
	public void die() {
		
		DEAD = true;
		
		try {
			File file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Explosion2.png");
		    FileInputStream fis = new FileInputStream(file); 
			this.sprite = ImageIO.read(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void moveCorpse() {
		this.pos_x = -5 * this.disp.tileSize;
		this.pos_y = -5 * this.disp.tileSize;
	}
	
	public boolean inHitbox() {
		
		// Check if character is within bounding box of enemy gunfire
		for (Gunfire f : disp.fireballs) {
			if ((f.owner.getClass() == Enemy.class & this.getClass() == Enemy.class) |
					(f.owner.getClass() == Bomber.class & this.getClass() == Enemy.class) |
					(f.owner.getClass() == Enemy.class & this.getClass() == Bomber.class) |
					(f.owner.getClass() == Bomber.class & this.getClass() == Bomber.class)) {
				continue;
			}
			if (f.pos_x - (disp.tileSize / 4) < pos_x & 
					pos_x < f.pos_x + (disp.tileSize / 4) &
					f.pos_y - (disp.tileSize / 4) < pos_y & 
					pos_y < f.pos_y + (disp.tileSize / 4)) {
				f.moveCorpse();
				f.velocity = 0;
				return true;
			}
		}
		return false;
	}
	
	public void hit() {
		this.HP = this.HP - 1;
	}
	
	public void fire(String dir) {
		
		Gunfire f = new Gunfire(disp, this, dir);
		disp.fireballs.add(f);
	}
	
	public void render(Graphics2D graph2D) {
		
		graph2D.drawImage(this.sprite, pos_x, pos_y, this.disp.tileSize, this.disp.tileSize, null);
	}

}
