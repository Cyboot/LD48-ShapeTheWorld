package de.timweb.ld48.evo.game;

import java.awt.Graphics;
import java.util.ArrayList;

import de.timweb.ld48.evo.entity.Bakteria;
import de.timweb.ld48.evo.entity.EnemyEntity;
import de.timweb.ld48.evo.entity.Entity;
import de.timweb.ld48.evo.entity.PlayerEntity;
import de.timweb.ld48.evo.util.EvoShape;
import de.timweb.ld48.evo.util.ImageLoader;
import de.timweb.ld48.evo.util.SoundEffect;
import de.timweb.ld48.evo.util.Vector2d;

public class Game {
	public static Game g;
	private static boolean isEvolutionScreen = false;

	public static boolean VK_SPACE;
	public static boolean VK_UP;
	public static boolean VK_DOWN;
	public static boolean VK_LEFT;
	public static boolean VK_RIGHT;
	public static boolean VK_ENTER = false;

	private int tutorial = 0;

	World world;
	static ArrayList<Entity> effects = new ArrayList<Entity>();
	static ArrayList<Entity> entities = new ArrayList<Entity>();
	static ArrayList<Entity> interact = new ArrayList<Entity>();
	static ArrayList<EnemyEntity> enemies = new ArrayList<EnemyEntity>();
	private boolean isStartScreen = true;
	private static PlayerEntity player;

	private static boolean isWon;
	private static boolean isLost;
	private static int lastdelta;

	public Game() {
		player = new PlayerEntity(new Vector2d(EvoCanvas.WIDTH / 2,
				EvoCanvas.HEIGHT / 2));
		world = new World(player);

		for (int i = 0; i < 30; i++) {
			entities.add(new Bakteria(new Vector2d(Math.random() * 1280, Math
					.random() * 768)));
		}
		for (int i = 0; i < 40; i++) {
			Spawner.spawnEnemy(i % 3);
		}

	}

	public void restart(EvoShape playershape) {
		evolve(false);
		player.setShape(playershape);

		world = new World(player);

		effects = new ArrayList<Entity>();
		interact = new ArrayList<Entity>();
		enemies = new ArrayList<EnemyEntity>();
		for (int i = 0; i < 40; i++) {
			Spawner.spawnEnemy(i % 5);
		}
	}

	public void update(int delta) {
		lastdelta = delta;
		if (isEvolutionScreen()) {
			EvolutionScreen.update(delta);
			return;
		}
		if (VK_ENTER) {
			VK_ENTER = false;
			tutorial++;
		}

		try {

			Spawner.update(delta);

			if (!isStartScreen && !isLost && !isWon) {
				Player.p.update(delta);
				player.update(delta);
			}
			for (int i = 0; i < enemies.size(); i++) {
				if (!enemies.get(i).isAlive()) {
					int type = enemies.get(i).getType();
					enemies.remove(i);
					Spawner.spawnEnemy(type);
				} else
					enemies.get(i).update(delta);
			}
			for (int i = 0; i < effects.size(); i++) {
				if (!effects.get(i).isAlive())
					effects.remove(i);
				else
					effects.get(i).update(delta);
			}
			for (int i = 0; i < entities.size(); i++) {
				if (!entities.get(i).isAlive())
					entities.remove(i);
				else
					entities.get(i).update(delta);
			}

			for (int i = 0; i < interact.size(); i++) {
				if (!interact.get(i).isAlive())
					interact.remove(i);
				else
					interact.get(i).update(delta);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		world.update(delta);
	}

	public void render(Graphics g) {
		if (isEvolutionScreen()) {
			EvolutionScreen.render(g);
			return;
		}
		world.render(g);

		try {

			for (Entity e : entities)
				e.render(g);

			for (Entity e : enemies)
				e.render(g);
			for (Entity e : interact)
				e.render(g);

			player.render(g);
			for (Entity e : effects)
				e.render(g);
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (isLost) {
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.lost, 0, 0, null);
		}
		if (isWon) {
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.win, 0, 0, null);
		}

		switch (tutorial) {
		case 0:
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.logo, 0, 0, null);
			break;
		case 1:
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.tutorial1, 0, 0, null);
			break;
		case 2:
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.tutorial2, 0, 0, null);
			break;
		case 3:
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.blur, 0, 0, null);
			g.drawImage(ImageLoader.tutorial3, 0, 0, null);
			break;
		default:
			isStartScreen = false;
		}

		if (!isStartScreen)
			Gui.render(g);
	}

	public static void init() {
		g = new Game();

	}

	public ArrayList<EnemyEntity> getEnemies() {
		return enemies;
	}

	public ArrayList<Entity> getInteractingEntities() {
		return interact;
	}

	public static PlayerEntity getPlayer() {
		return player;
	}

	public void addInteracting(Entity e) {
		interact.add(e);
	}

	public static void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void addEffect(Entity e) {
		effects.add(e);
	}

	public static boolean isEvolutionScreen() {
		return isEvolutionScreen;
	}

	public static void loose() {
		isLost = true;
	}

	public static void win() {
		isWon = true;
	}

	public static void evolve(boolean evolve) {
		Game.isEvolutionScreen = evolve;
		if (evolve)
			SoundEffect.EVOLUTION.play();

	}

	public static void addEnemy(EnemyEntity enemyEntity) {
		enemies.add(enemyEntity);
	}

	public static int getDelta() {
		return lastdelta;
	}
}
