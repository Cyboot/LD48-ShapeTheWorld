package de.timweb.ld48.evo.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import de.timweb.ld48.evo.entity.PlayerEntity;
import de.timweb.ld48.evo.util.ImageLoader;

public class World {
	private static final int TYPE_EARTH = 0;
	private static final int TYPE_WATER = 1;
	private static final int TYPE_GRASS = 2;
	private static final int TYPE_SAND = 3;

	private static int TEXTURE_WIDTH = 1280;
	private static int TEXTURE_HEIGTH = 768;

	private static int BORDER_WIDTH = EvoCanvas.WIDTH / 3;
	private static int BORDER_HEIGHT = EvoCanvas.HEIGHT / 3;

	static HashMap<Integer, Integer> terrain = new HashMap<Integer, Integer>();
	public World w;

	public static double offsetX = 0;
	public static double offsetY = 0;

	PlayerEntity player;

	public World(PlayerEntity player) {
		this.player = player;

		terrain.put(getKey(-1, 0), TYPE_WATER);
		terrain.put(getKey(0, 0), TYPE_EARTH);
		terrain.put(getKey(1, 0), TYPE_WATER);
	}

	public void update(int delta) {
		double screenX = player.getPos().x - offsetX;
		double screenY = player.getPos().y - offsetY;

		if (screenX < BORDER_WIDTH)
			offsetX -= BORDER_WIDTH - screenX;
		if (screenX > EvoCanvas.WIDTH - BORDER_WIDTH)
			offsetX += BORDER_WIDTH - (EvoCanvas.WIDTH - screenX);

		if (screenY < BORDER_HEIGHT)
			offsetY -= BORDER_HEIGHT - screenY;
		if (screenY > EvoCanvas.HEIGHT - BORDER_HEIGHT)
			offsetY += BORDER_HEIGHT - (EvoCanvas.HEIGHT - screenY);

	}

	public void render(Graphics g) {
		int x = (int) (offsetX / TEXTURE_WIDTH);
		int y = (int) (offsetY / TEXTURE_HEIGTH);

		drawTexture(x - 1, y - 1, g);
		drawTexture(x + 0, y - 1, g);
		drawTexture(x + 1, y - 1, g);
		drawTexture(x - 1, y + 0, g);
		drawTexture(x + 0, y + 0, g);
		drawTexture(x + 1, y + 0, g);
		drawTexture(x - 1, y + 1, g);
		drawTexture(x + 0, y + 1, g);
		drawTexture(x + 1, y + 1, g);

		g.setColor(Color.yellow);

	}

	private void drawTexture(int x, int y, Graphics g) {
		Integer type = terrain.get(getKey(x, y));
		if (type == null) {
			type = new Integer((int) (Math.random() * 4));
			terrain.put(getKey(x, y), type);
		}

		int xTerrain = (int) (offsetX % TEXTURE_WIDTH);
		int yTerrain = (int) (offsetY % TEXTURE_HEIGTH);

		int xMid = (int) (offsetX / TEXTURE_WIDTH);
		int yMid = (int) (offsetY / TEXTURE_HEIGTH);

		int xTile = x - xMid;
		int yTile = y - yMid;

		BufferedImage img = ImageLoader.bg_earth;
		switch (type) {
		case TYPE_EARTH:
			img = ImageLoader.bg_earth;
			break;
		case TYPE_WATER:
			img = ImageLoader.bg_water;
			break;
		case TYPE_SAND:
			img = ImageLoader.bg_sand;
			break;
		case TYPE_GRASS:
			img = ImageLoader.bg_grass;
			break;
		}

		g.drawImage(img, (int) -xTerrain + TEXTURE_WIDTH * xTile, (int) -yTerrain + TEXTURE_HEIGTH * yTile, null);

	}
	
	public static int getTexturType(double xPos, double yPos){
		if(xPos < 0)
			xPos -= TEXTURE_WIDTH;
		if(yPos < 0)
			yPos -= TEXTURE_HEIGTH;
		
		int x = (int) (xPos / TEXTURE_WIDTH);
		int y = (int) (yPos / TEXTURE_HEIGTH);
		
		return terrain.get(getKey(x, y));
	}

	private static Integer getKey(int x, int y) {
		return new Integer((x << 16) + y);
	}
}
