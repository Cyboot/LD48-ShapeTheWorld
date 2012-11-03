package de.timweb.ld48.evo.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.timweb.ld48.evo.game.EvolutionScreen;
import de.timweb.ld48.evo.game.Game;
import de.timweb.ld48.evo.game.Player;

public class TransformPolygon extends Polygon implements MouseListener, MouseMotionListener {
	public static final TransformPolygon p = new TransformPolygon();

	ArrayList<ClickPoint> points = new ArrayList<TransformPolygon.ClickPoint>();

	public double complexity;
	public double speed;
	public double strength;

	private int evolutionPoints = 200;

	@Override
	public void addPoint(int x, int y) {
		super.addPoint(x, y);
		points.add(new ClickPoint(x, y, npoints - 1));
		calcStatistics();
	}

	// public void addClickPoint(int x, int y, int index) {
	// points.add(new ClickPoint(x + PolygonTransformer.TRANSLATE, y +
	// PolygonTransformer.TRANSLATE,index));
	// // System.out.println("Point( " + x + " : " + y + " ): " + index);
	// }

	@Override
	public void reset() {
		super.reset();
		evolutionPoints = 100;
		points = new ArrayList<TransformPolygon.ClickPoint>();
	}

	public void render(Graphics g) {
		try{
			
		g.fillPolygon(p);

		g.setColor(Color.white);
		for (ClickPoint po : points) {
			if (po.wasClicked())
				g.fillOval(po.pos.x() - 4, po.pos.y() - 4, 8, 8);
			else
				g.drawOval(po.pos.x() - 4, po.pos.y() - 4, 8, 8);
		}
		}catch (java.awt.geom.IllegalPathStateException e) {
			e.printStackTrace();
		}

	}

	private class ClickPoint {
		Vector2d origin;
		Vector2d pos;
		int radius = 8;
		private boolean isClicked = false;
		private int index;
		private boolean locked = false;

		public ClickPoint(int x, int y, int index) {
			this.index = index;
			origin = new Vector2d(x, y);
			pos = origin.copy();
		}

		public boolean isClicked(Point point) {
			if (locked)
				return false;

			isClicked = pos.distance(point.x, point.y) < radius;
			if (isClicked)
				locked = true;

			return isClicked;
		}

		public void mouseRelease(Point point) {
			isClicked = false;
			locked = false;
		}

		public void mouseDragged(Point point) {
			if (isClicked) {
				Vector2d newPos = new Vector2d(point.x - point.x % EvolutionScreen.RASTER, point.y - point.y % EvolutionScreen.RASTER);

				// Prüfen ob Punkt nicht auf einem anderen liegt
				boolean frei = true;
				for (int i = 0; i < p.npoints; i++) {
					if (i == index)
						continue;
					if (newPos.distance(xpoints[i], ypoints[i]) == 0) {
						frei = false;
						break;
					}
				}

				double dist = origin.distance(newPos);
//				System.out.println(getEvolutionPoints(index));
				// nicht zu weit verschieben
				if (dist < getEvolutionPoints(index) && frei) {
					xpoints[index] = newPos.x();
					ypoints[index] = newPos.y();
					pos.set(newPos);
					calcStatistics();
				}
			}
		}

		public double distanceToOrigin() {
			return origin.distance(pos);
		}

		public boolean wasClicked() {
			return isClicked;
		}
	}

	public EvoShape getEvoShape() {
		EvoShape result = new EvoShape(new Color(255, 165, 0));

		double minY = ypoints[0];
		double minX = xpoints[0];
		double maxY = ypoints[0];
		double maxX = xpoints[0];

		for (int i = 0; i < p.npoints; i++) {
			if (ypoints[i] < minY)
				minY = ypoints[i];
			if (p.ypoints[i] > maxY)
				maxY = ypoints[i];

			if (xpoints[i] < minX)
				minX = xpoints[i];
			if (xpoints[i] > maxX)
				maxX = xpoints[i];

		}

		p.translate(-(int) minX, -(int) minY);

		float scale = EvolutionScreen.trans.getScale();

		for (int i = 0; i < p.npoints; i++) {
			xpoints[i] /= scale;
			ypoints[i] /= scale;
		}

		for (int i = 0; i < npoints; i++) {
			result.addPoint(xpoints[i], ypoints[i]);
		}

		EvolutionScreen.trans = null;
		reset();
		Player.p.setHealth(30 + strength);
		Player.p.setDnaGoal((int) (20 - 10 * complexity / 100f));
		Game.getPlayer().addExtraSpeed(speed / 50f);

		return result;
	}

	public int getEvolutionPoints(int index) {
		double sumDistance = 0;
		for (int i = 0; i < p.npoints; i++) {
			if (i == index)
				continue;
			sumDistance += points.get(i).distanceToOrigin();
		}
		if (200 - sumDistance < 0)
			return 0;
		else
			return (int) (200 - sumDistance);
	}

	public void calcStatistics() {
		bounds = null;
		Rectangle rect = getBounds();
		double radius = ((rect.getHeight() + rect.getWidth()) / 2);

		double umfang = 0;
		Vector2d old = new Vector2d(xpoints[0], ypoints[0]);

		ArrayList<Double> distances = new ArrayList<Double>();

		for (int i = 1; i < p.npoints; i++) {
			double dist = old.distance(xpoints[i], ypoints[i]);
			distances.add(dist);

			umfang += dist;
			old.set(xpoints[i], ypoints[i]);
		}
		double dist = old.distance(xpoints[0], ypoints[0]);
		distances.add(dist);
		umfang += dist;

		Collections.sort(distances);

		double mins = 0;
		double maxs = 0;
		for (int i = 0; i < p.npoints / 4; i++) {
			mins += distances.get(0);
			maxs += distances.get(distances.size() - i - 1);
		}

		mins /= p.npoints / 4;
		maxs /= p.npoints / 4;

		complexity = umfang / radius * 10;
		speed = maxs / radius * 75;
		strength = mins / umfang * 700;

		// System.out.println("Umfang Polygon: " + umfang);
		// System.out.println("radius: "+radius);
		// System.out.println("mins: "+mins);
		// System.out.println("maxs: "+maxs);
	}

	@Override
	public void mouseClicked(MouseEvent m) {
	}

	@Override
	public void mouseEntered(MouseEvent m) {
	}

	@Override
	public void mouseExited(MouseEvent m) {
	}

	@Override
	public void mouseMoved(MouseEvent m) {

	}

	@Override
	public void mousePressed(MouseEvent m) {
		if (m.getButton() == MouseEvent.BUTTON1) {
			for (ClickPoint p : points) {
				p.isClicked(m.getPoint());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		if (!Game.isEvolutionScreen())
			return;

		if (m.getButton() == MouseEvent.BUTTON1) {

			if (EvolutionScreen.isButtonPressed(m.getPoint())) {
				Game.g.restart(getEvoShape());
				Player.p.setDNA(0);
			}

			for (ClickPoint p : points) {
				p.mouseRelease(m.getPoint());
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		for (ClickPoint p : points) {
			p.mouseDragged(m.getPoint());
		}
	}

	public String getSpeed() {
		// NumberFormat format = new DecimalFormat("#0");
		// return format.format(speed);
		return "" + (int) speed;
	}

	public String getStrength() {
		// NumberFormat format = new DecimalFormat("#0.0");
		// return format.format(strength);
		return "" + (int) strength;
	}

	public String getComplexity() {
		// NumberFormat format = new DecimalFormat("#0.0");
		// return format.format(complexity);
		return "" + (int) complexity;
	}

}
