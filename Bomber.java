package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bomber extends Enemy {
	
	String direction;
	int bombTimer = 1;

	public Bomber(Display disp, Player player, int iter, int height) {
		super(disp, player, iter, height);
		this.pos_x = (this.disp.numCols / 2) * this.disp.tileSize;
		this.pos_y = height * this.disp.tileSize;
		this.velocity = iter + 6;
		if (iter % 2 == 0) {
			direction = "left";
		} else {
			direction = "right";
		}
		try {
			File file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Bomber.png");
		    FileInputStream fis = new FileInputStream(file); 
			this.sprite = ImageIO.read(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		// Move enemies according to rules
		
		if (HP <= 0) {
			this.die();
			this.explosionCounter += 1;
			this.disp.DEAD_ENEMIES += 1;
			return;
		}
		
		if (this.inHitbox()) {
			this.hit();
		}
		
		if (System.currentTimeMillis() % 10 == 0) {
			pos_y = Math.min(pos_y + 1, (this.disp.numRows - 1) * this.disp.tileSize);
		}
		
		if (direction == "left") {
			pos_x = Math.max(pos_x - velocity, 0);
			if (pos_x == 0) {
				direction = "right";
			}
		} else {
			pos_x = Math.min(pos_x + velocity, (this.disp.numCols - 1) * this.disp.tileSize);
			if (pos_x == (this.disp.numCols - 1) * this.disp.tileSize) {
				direction = "left";
			}
		}
		
		if (Math.abs(human_player.pos_x - pos_x) < this.disp.tileSize & bombTimer % 20 == 0) {
			fire("down");
		}
		bombTimer += 1;
	}
}
