package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gunfire extends Character {
	
	String direction;
	Character owner;
	
	public Gunfire(Display disp, Character owner, String direction) {
		super(disp);
		this.owner = owner;
		this.direction = direction; 
		this.pos_x = owner.pos_x;
		if (owner.getClass() == Player.class) {
			this.pos_y = owner.pos_y - (this.disp.tileSize / 2);
		} else {
			this.pos_y = owner.pos_y + (this.disp.tileSize / 2);
		}
		this.velocity = owner.velocity;
		
		try {
			File file = null;
			if (owner.getClass() == Bomber.class) {
				file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Explosion1.png");
			} else if (owner.getClass() == Enemy.class) {
				file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Fireball2.png");
			} else {
				file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Fireball.png");
			}
		    FileInputStream fis = new FileInputStream(file); 
			this.sprite = ImageIO.read(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		// Move gunfire up or down
		if (direction == "down") {
			pos_y = Math.min(pos_y + velocity, this.disp.numRows * this.disp.tileSize);
		} else {
			pos_y = Math.max(pos_y - velocity, -1 * this.disp.tileSize);
		}
	}
	
	@Override
	public boolean inHitbox() {
		return false;
	}
	
	@Override
	public void hit() {
		return;
	}
}
