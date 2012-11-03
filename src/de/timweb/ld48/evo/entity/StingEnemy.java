package de.timweb.ld48.evo.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.Vector2d;

public class StingEnemy extends Entity {
	private static BufferedImage[] img = new BufferedImage[8];
	private int lifetime = (int) (Math.random() * 5000);
	private int lastStep = (int) (Math.random()*150);
	private int imgIndex;
	private double dx;
	private double dy;
	private double speed = 0.1f;

	public StingEnemy(Vector2d pos, double dx, double dy) {
		super(pos);
		
		this.dx = dx;
		this.dy = dy;
		
		imgIndex = (int) (Math.random()*8);
	}

	public static void init(){
		for(int i=0;i<8;i++){
			img[i] = ImageLoader.getSubImage(ImageLoader.sting, i, 0, 16);
		}
	}
	
	@Override
	public void update(int delta) {
		lastStep += delta;
		if(lastStep > 100){
			lastStep = 0;
			imgIndex = ++imgIndex % 8;
		}
		
		lifetime -= delta;
		if(lifetime < 0)
			kill();
		
		move(dx * speed * delta, dy* speed  * delta);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(img[imgIndex], (int) (getPos().x()-8-World.offsetX), (int) (getPos().y()-8-World.offsetY), null);
	}

}
