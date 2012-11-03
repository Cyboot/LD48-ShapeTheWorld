package de.timweb.ld48.evo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.Vector2d;

public class Tang extends Entity {
	private BufferedImage img;
	private double dx, dy;

	public Tang(Vector2d pos) {
		super(pos);
		img = ImageLoader.getSubImage(ImageLoader.tang, (int) (Math.random() * 2), (int) (Math.random() * 2), 128);
		dx = (0.5 - Math.random())/30;
		dy = (0.5 - Math.random())/30;
	}

	@Override
	public void update(int delta) {
		move(dx * delta, dy * delta);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, (int) (getPos().x-World.offsetX), (int) ( getPos().y-World.offsetY), null);
	}

}
