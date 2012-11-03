package de.timweb.ld48.evo.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import de.timweb.ld48.evo.game.World;

public class EvoShape extends Polygon {
	private double dx, dy;
	private double[] xDoublePoints;
	private double[] yDoublePoints;

	private double lastOffsetX;
	private double lastOffsetY;

	private Color color;
	private double centerX;
	private double centerY;
	private ArrayList<Point> points = new ArrayList<EvoShape.Point>();

	public EvoShape(Color color) {
		super();
		this.color = color;
		xDoublePoints = new double[xpoints.length];
		yDoublePoints = new double[ypoints.length];
	}

	public void render(Graphics g, boolean doppelt) {
		g.setColor(color);
		g.fillPolygon(this);
		if (doppelt) {
			g.setColor(color.brighter().brighter());
			g.drawPolygon(this);
		}

		double offsetX = -World.offsetX + lastOffsetX;
		double offsetY = -World.offsetY + lastOffsetY;
		if (offsetX != 0 || offsetY != 0) {
			move(offsetX, offsetY);
		}
		lastOffsetX = World.offsetX;
		lastOffsetY = World.offsetY;

		Rectangle rect = getBounds();
		g.setColor(color.brighter().brighter());
		g.fillOval((int) centerX - 2, (int) centerY - 2, 4, 4);

		for (Point p : points) {
			g.fillOval((int) p.x - 2, (int) p.y - 2, 4, 4);
		}

	}

	public void move(double x, double y) {
		dx += x;
		dy += y;

		for (Point p : points) {
			p.x += x;
			p.y += y;
		}

		if (Math.abs(dx) > 1) {
			this.translate((int) dx, 0);
			dx -= (int) dx;
			calcCenter();
		}
		if (Math.abs(dy) > 1) {
			this.translate(0, (int) dy);
			dy -= (int) dy;
			calcCenter();
		}
	}

	public void addMarker(int markerCount) {
		calcCenter();
	}

	@Override
	public void addPoint(int x, int y) {
		int pos = npoints;
		super.addPoint(x, y);

		if (xpoints.length != xDoublePoints.length) {
			double[] xtemp = xDoublePoints.clone();
			double[] ytemp = yDoublePoints.clone();

			xDoublePoints = new double[xpoints.length];
			yDoublePoints = new double[xpoints.length];

			System.arraycopy(xtemp, 0, xDoublePoints, 0, xtemp.length);
			System.arraycopy(ytemp, 0, yDoublePoints, 0, ytemp.length);
		}

		xDoublePoints[pos] = x;
		yDoublePoints[pos] = y;
		calcCenter();
	}

	public void rotate(double degree) {
		calcCenter();

		for (int i = 0; i < points.size(); i++) {

			double[] pt = { points.get(i).x, points.get(i).y };
			AffineTransform.getRotateInstance(Math.toRadians(degree), centerX, centerY).transform(pt, 0, pt, 0, 1);
			double x = pt[0];
			double y = pt[1];

			points.get(i).x = x;
			points.get(i).y = y;

		}

		for (int i = 0; i < npoints; i++) {

			double[] pt = { xDoublePoints[i], yDoublePoints[i] };
			AffineTransform.getRotateInstance(Math.toRadians(degree), centerX, centerY).transform(pt, 0, pt, 0, 1);
			double x = pt[0];
			double y = pt[1];

			xDoublePoints[i] = x;
			yDoublePoints[i] = y;

			xpoints[i] = (int) x;
			ypoints[i] = (int) y;
		}
	}

	@Override
	public void translate(int deltaX, int deltaY) {
		super.translate(deltaX, deltaY);

		for (int i = 0; i < npoints; i++) {
			xDoublePoints[i] += deltaX;
			yDoublePoints[i] += deltaY;
		}
	}

	private void calcCenter() {
		double sumX = 0;
		double sumY = 0;

		for (int i = 0; i < npoints; i++)
			sumX += xDoublePoints[i];

		for (int i = 0; i < npoints; i++)
			sumY += yDoublePoints[i];

		centerX = sumX / npoints;
		centerY = sumY / npoints;
	}

	public int getSize() {
		Rectangle rect = getBounds();
		return (int) ((rect.getHeight()+rect.getWidth()) /2);
	}

	private class Point {
		double x, y;
		Color color;

		public Point(double x, double y, Color color) {
			this.color = color;
		}
	}
}
