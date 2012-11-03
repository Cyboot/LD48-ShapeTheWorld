package de.timweb.ld48.evo.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import de.timweb.ld48.evo.entity.StingEnemy;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.SoundEffect;
import de.timweb.ld48.evo.util.TransformPolygon;

public class EvoCanvas extends Canvas implements Runnable, KeyListener {
	public static int WIDTH;
	public static int WIDTH_GAME;
	public static int HEIGHT;

	public static long currentFPS;
	public static final int FPS_TARGET = 120;
	public static final int DELTA_TARGET = 1000 / FPS_TARGET;

	public EvoCanvas(int width, int height, int border) {
		WIDTH = width;
		WIDTH_GAME = width;
		HEIGHT = height;

		Dimension dim = new Dimension(width - border, height - border);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		this.setMaximumSize(dim);
		this.addKeyListener(this);
		this.addMouseListener(TransformPolygon.p);
		this.addMouseMotionListener(TransformPolygon.p);
	}

	public void start() {
		Thread t = new Thread(this);
		SoundEffect.init();
		ImageLoader.init();
		StingEnemy.init();
		Game.init();
		SoundEffect.MUSIC.loop();
		t.start();
	}

	@Override
	public void run() {
		long delta = 0;

		while (true) {
			long start = System.currentTimeMillis();

			update((int) delta);
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				continue;
			}
			render(bs.getDrawGraphics());

			if (bs != null)
				bs.show();

			long timepassed = System.currentTimeMillis() - start;
			if (timepassed < DELTA_TARGET) {
				try {
					Thread.sleep(DELTA_TARGET - timepassed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			delta = System.currentTimeMillis() - start;
			if (delta != 0)
				currentFPS = 1000 / delta;
		}

	}

	private void update(int delta) {
		Game.g.update(delta);
	}

	private void render(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);

		Game.g.render(g);

		g.setColor(Color.red);
		g.setFont(getFont());
		g.drawString("FPS: " + currentFPS, WIDTH - 80, 20);

		g.dispose();
		Toolkit.getDefaultToolkit().sync();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			Game.VK_UP = true;
			break;
		case KeyEvent.VK_DOWN:
			Game.VK_DOWN = true;
			break;
		case KeyEvent.VK_LEFT:
			Game.VK_LEFT = true;
			break;
		case KeyEvent.VK_RIGHT:
			Game.VK_RIGHT = true;
			break;
		case KeyEvent.VK_SPACE:
			Game.VK_SPACE = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			Game.VK_UP = false;
			break;
		case KeyEvent.VK_DOWN:
			Game.VK_DOWN = false;
			break;
		case KeyEvent.VK_LEFT:
			Game.VK_LEFT = false;
			break;
		case KeyEvent.VK_RIGHT:
			Game.VK_RIGHT = false;
			break;
		case KeyEvent.VK_SPACE:
			Game.VK_SPACE = false;
			break;
		case KeyEvent.VK_ENTER:
			Game.VK_ENTER = true;
			break;
		case KeyEvent.VK_M:
			SoundEffect.muteMusic();
			break;
		case KeyEvent.VK_S:
			SoundEffect.muteSound();
			break;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
