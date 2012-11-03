package de.timweb.ld48.evo.game;

import java.awt.Graphics;

import de.timweb.ld48.evo.util.ImageLoader;

public class Gui {

	public static void render(Graphics g) {
		g.drawImage(ImageLoader.dnaBar_trans, EvoCanvas.WIDTH/8, 10, null);
		
		int width = (int) (Player.p.getDNAPercent()*ImageLoader.dnaBar.getWidth())-1;
		if(width <= 0)
			width = 1;
		
		g.drawImage(ImageLoader.getCutImage(ImageLoader.dnaBar, width), EvoCanvas.WIDTH/8, 10, null);
		g.drawImage(ImageLoader.healthBar_trans, EvoCanvas.WIDTH/8, 30, null);
		
		width = (int) (Player.p.getHealthPercent()*ImageLoader.dnaBar.getWidth())-1;
		if(width <= 0)
			width = 1;
		g.drawImage(ImageLoader.getCutImage(ImageLoader.healthBar, width), EvoCanvas.WIDTH/8, 30, null);
	}
}
