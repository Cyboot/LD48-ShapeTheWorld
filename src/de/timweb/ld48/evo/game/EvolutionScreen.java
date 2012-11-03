package de.timweb.ld48.evo.game;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.PolygonTransformer;
import de.timweb.ld48.evo.util.TransformPolygon;

public class EvolutionScreen {
	public static final int max = EvoCanvas.HEIGHT - 20;
	public static final int RASTER = 5;
	public static PolygonTransformer trans;

	private static final Color bright = new Color(100,100,100);
	private static final Color darker = new Color(20,20,20);

	private static Rectangle button = new Rectangle(max+20,EvoCanvas.HEIGHT-20-40,120,40);
	
	public static void update(int delta) {
		if (trans == null)
			trans = new PolygonTransformer(Game.getPlayer().getPolygon(), max - 300);
	}

	public static void render(Graphics g) {
		g.drawImage(ImageLoader.evo_screen, 0, 0, null);

		drawGrid(g);
		g.setColor(new Color(255, 165, 0, 100));

		if (TransformPolygon.p != null)
			TransformPolygon.p.render(g);
		
		g.drawString("Evolution left: ", max+50,110);
		g.drawString(TransformPolygon.p.getEvolutionPoints(-1)+" / 200", max+130,110);
		
		g.drawString("Speed: ", max+50,160);
		g.drawString("Health:  ", max+50,180);
		g.drawString("Complexity: ", max+50,200);
		g.drawString(TransformPolygon.p.getSpeed(), max+130,160);
		g.drawString(TransformPolygon.p.getStrength(), max+130,180);
		g.drawString(TransformPolygon.p.getComplexity(), max+130,200);
		
		g.setColor(Color.darkGray);
		g.fillRect(button.x, button.y, (int)button.getWidth(), (int) button.getHeight());
		g.setColor(Color.gray);
		g.drawRect(button.x, button.y, (int)button.getWidth(), (int) button.getHeight());
		
		g.setColor(Color.white);
		g.drawString("Continue", max+50,EvoCanvas.HEIGHT-35);
	}

	private static void drawGrid(Graphics g) {
		g.setColor(Color.DARK_GRAY);

		for (int y = 100; y <= max; y += RASTER) {
			if(y%20==0)
				g.setColor(bright);
			else
				g.setColor(darker);
				
			g.drawLine(100, y, max, y);
		}
		for (int x = 100; x <= max; x += RASTER) {
			if(x%20==0)
				g.setColor(bright);
			else
				g.setColor(darker);
			g.drawLine(x, 100, x, max);
		}
	}
	
	public static boolean isButtonPressed(Point point){
		return button.contains(point);
	}
}
