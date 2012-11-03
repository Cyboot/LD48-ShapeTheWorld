package de.timweb.ld48.evo.entity;

import java.awt.Graphics;

import de.timweb.ld48.evo.util.Vector2d;

public abstract class Entity {
	private Vector2d pos;
	private boolean isAlive = true;

	public Entity(Vector2d pos) {
		this.pos = pos.copy();
	}
	
	
	public abstract void update(int delta);
	public abstract void render(Graphics g);
	public  void move(double x, double y){
		pos.add(x, y);
	}
	
	public Vector2d getPos() {
		return pos;
	}
	
	public void kill(){
		onKilled();
		isAlive = false;
	}
	protected void onKilled() {
	}


	public boolean isAlive() {
		return isAlive;
	}
}
