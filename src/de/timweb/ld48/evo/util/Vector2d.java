package de.timweb.ld48.evo.util;


public class Vector2d {
	public double x,y;
	
	public Vector2d() {
		this(0, 0);
	}
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public int x(){
		return (int)x;
	}
	public int y(){
		return (int)y;
	}
	
	public void add(double x, double y){
		this.x += x;
		this.y += y;
	}
	
	public Vector2d copy(){
		return new Vector2d(x, y);
	}
	public void set(Vector2d position) {
		x = position.x;
		y = position.y;
	}
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double distance(Vector2d other) {
		double dx = Math.abs(x-other.x);
		double dy = Math.abs(y-other.y);
		
		return Math.sqrt(dx*dx + dy*dy);
	}
	public double distance(double x, double y) {
		double dx = Math.abs(this.x-x);
		double dy = Math.abs(this.y-y);
		
		return Math.sqrt(dx*dx + dy*dy);
	}
}
