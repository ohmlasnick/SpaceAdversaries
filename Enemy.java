package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy extends Character {

	Player human_player;
	String type;
	int gunTimer = 1;
	private int iter;
	double nextSwitch;
	double switchCounter = 0;
	
	public Enemy(Display disp, Player player, int iter, int height) {
		super(disp);
		this.velocity = 3;
		this.HP = 25;
		this.human_player = player;
		this.iter = iter;
		this.nextSwitch = 100 + (iter * 50);
		
		if (iter < 6) {
			// Spawn on left at fixed height
			this.pos_x = 3 * this.disp.tileSize;
		} else if (6 <= iter & iter < 11) {
			// Spawn in middle at fixed height
			this.pos_x = (this.disp.numCols / 2) * this.disp.tileSize;
			height = height - 5;
		} else {
			// Spawn on right at fixed height
			this.pos_x = (this.disp.numCols - 4) * this.disp.tileSize;
			height = height - 10;
		}
		
		if (iter > 2 & iter < 14) {
			type = "naive";
			this.velocity = 1;
		} else {
			type = "pursue";
			this.velocity = human_player.velocity - 1;
		}
		
		this.pos_y = height * this.disp.tileSize;
		
		try {
			File file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Enemy1.png");
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
		
		if (type == "random") {
			if (iter < 8) {
				pos_x = Math.max(pos_x - velocity, 0);
				if (pos_x == 0) {
					iter = 10;
				}
			} else {
				pos_x = Math.min(pos_x + velocity, (this.disp.numCols - 1) * this.disp.tileSize);
				if (pos_x == (this.disp.numCols - 1) * this.disp.tileSize) {
					iter = 5;
				}
			}
			
			if (Math.abs(human_player.pos_x - pos_x) < this.disp.tileSize & gunTimer % 30 == 0) {
				fire("down");
			}
			
			if (switchCounter == nextSwitch) {
				type = "pursue";
				switchCounter = 0;
			}
		}
		
		if (type == "pursue") {
			int x_dir = 0;
			int x_diff = human_player.pos_x - pos_x;
			if (x_diff != 0) {
				x_dir = x_diff / Math.abs(human_player.pos_x - pos_x);
				pos_x += x_dir * velocity;
			}
			
			if (Math.abs(human_player.pos_x - pos_x) < this.disp.tileSize & gunTimer % 30 == 0) {
				fire("down");
				fire("down");
				fire("down");
				type = "random";
			}
			
			if (switchCounter == nextSwitch) {
				type = "random";
				switchCounter = 0;
			}
			
			if (human_player.DEAD) {
				type = "naive";
			}
		}
		gunTimer += 1;
		switchCounter += 1;
	}
}
