package de.timweb.ld48.evo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.evo.game.Game;
import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.Vector2d;

public class DnaPickup extends PickUp {
	private int lastStep;
	private int imgIndex;

	public DnaPickup(Vector2d pos) {
		super(pos);
	}

	@Override
	public void update(int delta) {
		lastStep += delta;
		if(lastStep > 100){
			imgIndex = ++imgIndex %4;
			lastStep = 0;
		}
	}

	@Override
	public void render(Graphics g) {
		BufferedImage img = ImageLoader.getSubImage(ImageLoader.dna, imgIndex, 0, 24);
		
		g.drawImage(img, (int) (getPos().x()-12-World.offsetX), (int) (getPos().y()-12-World.offsetY), null);
	}

	@Override
	protected void onKilled() {
		Game.g.addEffect(new PickUpAnim(getPos()));
	}
}
