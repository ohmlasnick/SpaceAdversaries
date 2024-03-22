package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Character {
	
	PlayerInput keyhandler;
	int gunTimer = 1;
	
	public Player(Display disp, PlayerInput keyhandler) {
		super(disp);
		this.keyhandler = keyhandler;
		this.pos_x = (this.disp.numCols / 2) * this.disp.tileSize;
		this.pos_y = (this.disp.numRows - 1) * this.disp.tileSize;
		this.velocity = 6;
		this.HP = 50;
		try {
			File file = new File("/Users/ohml/eclipse-workspace/SpaceAdversaries/sprites/all_sprites/Player.png");
		    FileInputStream fis = new FileInputStream(file); 
			this.sprite = ImageIO.read(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update() {
		
		if (disp.START_GAME == false) {
			if (this.keyhandler.START) {
				disp.START_GAME = true;
			}
		} else {
			
			if (HP <= 0) {
				this.die();
				this.disp.GAME_OVER = true;
				this.explosionCounter += 1;
				return;
			}
			
			if (this.inHitbox()) {
				this.hit();
			}
		
			if (this.keyhandler.up) {
				pos_y = Math.max(pos_y - velocity, 0);
			}
			if (this.keyhandler.down) {
				pos_y = Math.min(pos_y + velocity, (this.disp.numRows - 1) * this.disp.tileSize);
			}
			if (this.keyhandler.right) {
				pos_x = Math.min(pos_x + velocity, (this.disp.numCols - 1) * this.disp.tileSize);
			}
			if (this.keyhandler.left) {
				pos_x = Math.max(pos_x - velocity, 0);
			}
			if (this.keyhandler.fire & gunTimer % 2 == 0) {
				fire("up");
			}
			gunTimer += 1;
		}
	}
}
