package de.timweb.ld48.evo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.Vector2d;

public class PickUpAnim extends Entity {
	private int lastStep;
	private int imgIndex;

	public PickUpAnim(Vector2d pos) {
		super(pos);
	}

	@Override
	public void update(int delta) {
		lastStep += delta;
		if(lastStep > 60){
			imgIndex++;
			
			if(imgIndex >= 8)
				kill();
			
			lastStep = 0;
		}
	}

	@Override
	public void render(Graphics g) {
		if(isAlive()){
			BufferedImage img = ImageLoader.getSubImage(ImageLoader.pickup_anim, imgIndex, 0, 32);
			
			g.drawImage(img, (int) (getPos().x()-16-World.offsetX), (int) (getPos().y()-16-World.offsetY), null);
		}
	}

}
