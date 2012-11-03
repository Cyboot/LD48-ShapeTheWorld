package de.timweb.ld48.evo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.evo.game.Spawner;
import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.Vector2d;

public class Bakteria extends Entity {
	private static final int MAXLIFETIME = 7500;
	private int lifetime = (int) (Math.random() *MAXLIFETIME);
	private BufferedImage img;
	private double dx, dy;

	public Bakteria(Vector2d pos) {
		super(pos);
		
		img = ImageLoader.getSubImage(ImageLoader.bacteria, (int) (Math.random() * 4), (int) (Math.random() * 4), 16);
		dx = (0.5 - Math.random()) / 30;
		dy = (0.5 - Math.random()) / 30;
	}

	@Override
	public void update(int delta) {
		lifetime -= delta;
		if(lifetime < 0)
			kill();
		
		move(dx * delta, dy * delta);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, (int) (getPos().x - World.offsetX), (int) (getPos().y - World.offsetY), null);
	}

	@Override
	protected void onKilled() {
		Spawner.spawnBakteria();
	}
}
