package de.timweb.ld48.evo.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import de.timweb.ld48.evo.game.EvoCanvas;
import de.timweb.ld48.evo.game.Game;
import de.timweb.ld48.evo.game.Player;
import de.timweb.ld48.evo.game.World;
import de.timweb.ld48.evo.util.EvoShape;
import de.timweb.ld48.evo.util.Vector2d;

public class PlayerEntity extends ShapeEntity {
	private static double ROT = 0.3;
	private static double ACCL = 0.0003;
	private static double MINSPEED = 0.001;
	private static double MAXSPEED = ACCL * 500;
	private static double SLOWDOWN = ACCL / 3.;

	private int lastFlicker = -1000;
	private double radius;
	private int lastHurt = 1000;
	private double speedlimit = MAXSPEED;

	public PlayerEntity(Vector2d pos) {
		super(pos, new Color(255, 165, 0));

		shape.addPoint(25, 0);
		shape.addPoint(0, 40);
		shape.addPoint(25, 40);
		shape.addPoint(50, 40);
//		shape.addPoint(50, 10);
		// shape.addPoint(15 + (int) pos.x, 6 + (int) pos.y);
		// shape.addPoint(6 + (int) pos.x, 15 + (int) pos.y);
		// shape.addPoint(12 + (int) pos.x, 18 + (int) pos.y);
		// shape.addPoint(9 + (int) pos.x, 24 + (int) pos.y);
		// shape.addPoint(12 + (int) pos.x, 24 + (int) pos.y);
		// shape.addPoint(12 + (int) pos.x, 27 + (int) pos.y);
		// shape.addPoint(15 + (int) pos.x, 27 + (int) pos.y);
		// shape.addPoint(30 + (int) pos.x, 18 + (int) pos.y);
		// shape.addPoint(33 + (int) pos.x, 6 + (int) pos.y);
		// shape.addPoint(24 + (int) pos.x, 12 + (int) pos.y);

		setShape(shape);
		radius = shape.getSize() / 2f;
	}

	@Override
	public void update(int delta) {
		super.update(delta);

		checkCollision(delta);

		lastHurt += delta;
		lastFlicker += delta;
		if (lastFlicker > 1000)
			lastFlicker = -1000;

		if (Game.VK_UP && speedY > -speedlimit)
			speedY -= delta * ACCL;
		if (Game.VK_DOWN && speedY < speedlimit)
			speedY += delta * ACCL;
		
		
		if (Game.VK_LEFT && speedX > -speedlimit)
			speedX -= delta * ACCL;
		if (Game.VK_RIGHT && speedX < speedlimit)
			speedX += delta * ACCL;

//		System.out.println(radius);
		
//		if (Game.VK_LEFT)
//			rotate(-delta * ROT);
//		if (Game.VK_RIGHT)
//			rotate(delta * ROT);


		if (speedX > 0)
			speedX -= delta * SLOWDOWN;
		else
			speedX += delta * SLOWDOWN;

		if (speedY > 0)
			speedY -= delta * SLOWDOWN;
		else
			speedY += delta * SLOWDOWN;
		
		
		if (speedY < MINSPEED && speedY > -MINSPEED) {
			speedY = 0;
			return;
		}
		if (speedX < MINSPEED && speedX > -MINSPEED) {
			speedX = 0;
			return;
		}
	}

	private void checkCollision(int delta) {
		for (Entity e : Game.g.getInteractingEntities()) {
			if (getPos().distance(e.getPos()) < radius) {
				if (e instanceof DnaPickup) {
					e.kill();
					Player.p.addDNA(1);
				}
				if (e instanceof StingEnemy) {
					e.kill();
					hurt(-2);

				}
			}
		}

		for (EnemyEntity e : Game.g.getEnemies()) {
			if (getPos().distance(e.getPos()) < radius + e.getSize()) {
				if (radius > e.getSize()) {
					e.kill();
					// Player.p.addDNA(e.getSize()/10);
				} else {
					hurt(-delta * 0.05f);
				}

			}
		}
	}

	private void hurt(float i) {
		lastHurt = 0;
		Player.p.addHealth(i);

	}

	@Override
	public void render(Graphics g) {
		if (lastHurt < 400 && Math.random() > 0.5)
			return;

		shape.render(g, lastFlicker > 0);

		g.setColor(Color.red);
		g.fillOval((int) (getPos().x - World.offsetX) - 6, (int) (getPos().y - World.offsetY) - 6, 12, 12);
	}

	public Polygon getPolygon() {
		return shape;
	}

	public void setShape(EvoShape playershape) {
		shape = playershape;
		shape.translate(getPos().x(), getPos().y());

		getPos().set(shape.getBounds().getCenterX(), shape.getBounds().getCenterY());
		radius = shape.getSize() / 2;
		
		if(radius > EvoCanvas.HEIGHT/2)
			Game.win();
		
		speedX = 0;
		speedY = 0;
	}

	public int getSize() {
		return (int) radius;
	}

	public void addExtraSpeed(double d) {
		speedlimit = MAXSPEED + MAXSPEED * d;
	}
}
