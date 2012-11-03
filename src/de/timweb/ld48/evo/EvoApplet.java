package de.timweb.ld48.evo;

import java.applet.Applet;

import de.timweb.ld48.evo.game.EvoCanvas;
import de.timweb.ld48.evo.util.SoundEffect;


public class EvoApplet extends Applet{
	@Override
	public void init() {
		super.init();
		EvoCanvas game = new EvoCanvas(getWidth(),getHeight(),0);
		add(game);
		game.start();
	}
	
	@Override
	public void stop() {
		super.stop();
		
		SoundEffect.stopMusic();
		System.out.println("stop");
		System.exit(0);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		SoundEffect.stopMusic();
		System.out.println("destroy");
		System.exit(0);
	}
}
