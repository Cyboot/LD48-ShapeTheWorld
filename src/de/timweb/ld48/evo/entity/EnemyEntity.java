package de.timweb.ld48.evo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.evo.game.Game;
import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.Vector2d;

public class EnemyEntity extends Entity {
	public static final int SIZE_TINY = 0;
	public static final int SIZE_SMALL = 1;
	public static final int SIZE_MEDIUM = 2;
	public static final int SIZE_BIG = 3;
	public static final int SIZE_HUGE = 4;

	private static final int MAXLASTTURN = 2000;
	private static final int MAXSTINGSPAWN = 500;

	private boolean spawnStings = false;
	private BufferedImage img;
	private double speed = 0.05;
	private double dx, dy;
	private int lastTurn = MAXLASTTURN;
	private int lastStingSpawn = (int) (Math.random()*MAXSTINGSPAWN);
	private int radius;
	private int type;
	private int lastBlink = (int) (Math.random() * 1000);
	private boolean flicker;

	public EnemyEntity(Vector2d pos, int size, int level) {
		super(pos);

		type = size;
		
		switch (size) {
		case SIZE_TINY:
			radius = 12;
			if (Game.getPlayer().getSize() <= radius) {
				spawnStings = true;
				img = ImageLoader.getSubImage(ImageLoader.enemy_tiny_danger, level, (int) (Math.random() * 4), 32);
			} else
				img = ImageLoader.getSubImage(ImageLoader.enemy_tiny, level, (int) (Math.random() * 4), 32);

			break;
		case SIZE_SMALL:
			radius = 20;
			if (Game.getPlayer().getSize() <= radius) {
				spawnStings = true;
				img = ImageLoader.getSubImage(ImageLoader.enemy_small_danger, level, (int) (Math.random() * 4), 48);
			} else
				img = ImageLoader.getSubImage(ImageLoader.enemy_small, level, (int) (Math.random() * 4), 48);
			break;
		case SIZE_MEDIUM:
			radius = 26;
			if (Game.getPlayer().getSize() <= radius) {
				spawnStings = true;
				img = ImageLoader.getSubImage(ImageLoader.enemy_medium_danger, level, (int) (Math.random() * 4), 64);
			} else
				img = ImageLoader.getSubImage(ImageLoader.enemy_medium, level, (int) (Math.random() * 4), 64);

			break;
		case SIZE_BIG:
			radius = 45;
			if (Game.getPlayer().getSize() <= radius) {
				spawnStings = true;
				img = ImageLoader.getSubImage(ImageLoader.enemy_big_danger, level, (int) (Math.random() * 4), 96);
			} else
				img = ImageLoader.getSubImage(ImageLoader.enemy_big, level, (int) (Math.random() * 4), 96);
			break;
		case SIZE_HUGE:
			radius = 60;
			if (Game.getPlayer().getSize() <= radius) {
				spawnStings = true;
				img = ImageLoader.getSubImage(ImageLoader.enemy_huge_danger, level, (int) (Math.random() * 4), 128);
			} else
				img = ImageLoader.getSubImage(ImageLoader.enemy_huge, level, (int) (Math.random() * 4), 128);
			break;
		}
	}

	@Override
	public void update(int delta) {
		flicker = false;
		lastBlink += delta;
		if (lastBlink > 800) {
			flicker = true;
			lastBlink = 0;
		}

		move(dx * speed * delta, dy * speed * delta);
		
		lastTurn += delta;
		if (lastTurn > MAXLASTTURN) {
			lastTurn = 0;

			dx = Math.random() - 0.5;
			dy = Math.random() - 0.5;
		}
		
		
		//nur Große spawnen Stings
		if(!spawnStings)
			return;
		
		lastStingSpawn += delta;
		if(lastStingSpawn > MAXSTINGSPAWN){
			lastStingSpawn = 0;
			if(Math.random() > 0.9)
				lastStingSpawn = 30* MAXSTINGSPAWN;
			
			Game.g.addInteracting(new StingEnemy(getPos().copy(), Math.random() - 0.5, Math.random() - 0.5));
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img, (int) (getPos().x - World.offsetX - radius / 2), (int) (getPos().y - World.offsetY - radius / 2), null);
	}

	public int getSize() {
		return radius;
	}

	@Override
	protected void onKilled() {
		Game.g.addInteracting(new DnaPickup(getPos()));
	}

	public int getType() {
		return type;
	}
}
