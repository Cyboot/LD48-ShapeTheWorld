package de.timweb.ld48.evo.game;

import de.timweb.ld48.evo.util.SoundEffect;

public class Player {
	public static final int MAXLASTSOUND = 250;
	public static Player p = new Player();

	private float dnaCurrent = 0;
	private float dnaGoal = 8;
	private float health = 100;

	private int lastHurt;
	private int lastHurtSound;

	public float getDNAPercent() {
		return dnaCurrent / dnaGoal;
	}

	public float getHealthPercent() {
		return health / 100;
	}

	public void addHealth(float i) {
		if(i < 0){
			if(lastHurtSound > MAXLASTSOUND){
				SoundEffect.HURT.play();
				lastHurtSound = 0;
			}
			
			lastHurt = 0;
		}
			
		
		health += i;
		if (health > 100)
			health = 100;
		if(health < 0){
			Game.loose();
			health = 0;
		}
	}

	public void addDNA(int evolution) {
		SoundEffect.DNA.play();
		
		dnaCurrent += evolution;
		if(dnaCurrent >= dnaGoal){
			dnaCurrent = dnaGoal;
			Game.evolve(true);
		}
	}

	public void update(int delta){
		lastHurt += delta;
		lastHurtSound += delta;
		
		addHealth(delta*0.0007f);
	}

	public void setDNA(int i) {
		dnaCurrent = i;
	}
	public void setDnaGoal(int i) {
		dnaGoal = i;
	}

	public void setHealth(double d) {
		health = (float) d;
	}
}
