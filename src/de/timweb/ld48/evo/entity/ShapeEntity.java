package de.timweb.ld48.evo.entity;

import java.awt.Color;
import java.awt.Graphics;

import de.timweb.ld48.evo.util.EvoShape;
import de.timweb.ld48.evo.util.Vector2d;

public class ShapeEntity extends Entity {
	protected EvoShape shape;
	protected double speedX;
	protected double speedY;
	
	private double rotation;

	public ShapeEntity(Vector2d pos, Color color) {
		super(pos);
		shape = new EvoShape(color);
	}

	@Override
	public void update(int delta) {
		move(speedX*delta,speedY*delta);
//		move(Math.cos(Math.toRadians(rotation))*speed,Math.sin(Math.toRadians(rotation))*speed);
	}

	@Override
	public void render(Graphics g) {
		shape.render(g,false);
	}

	public void rotate(double degree){
		rotation += degree;
		if(rotation < 0)
			rotation += 360;
		rotation %= 360;
		
		shape.rotate(degree);
		
	}
	
	@Override
	public void move(double x, double y) {
		super.move(x, y);
		shape.move(x, y);
	}
}
