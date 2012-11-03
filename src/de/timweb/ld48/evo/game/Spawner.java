package de.timweb.ld48.evo.game;

import java.util.ArrayList;

import de.timweb.ld48.evo.entity.Bakteria;
import de.timweb.ld48.evo.entity.EnemyEntity;
import de.timweb.ld48.evo.util.Vector2d;

public class Spawner {
	private static final int KILLDISTANCE = EvoCanvas.WIDTH;

	static Vector2d worldPos = new Vector2d();

	public static void update(int delta){
		worldPos.set(World.offsetX+EvoCanvas.WIDTH/2, World.offsetY+EvoCanvas.HEIGHT/2);
		
		
		ArrayList<EnemyEntity> array = Game.g.getEnemies();
		for (int i = 0; i < array.size(); i++) {
			if(array.get(i).getPos().distance(worldPos.x, worldPos.y) > KILLDISTANCE){
				int type = array.get(i).getType();

				array.remove(i);
				spawnEnemy(type);
				
//				Vector2d pos = worldPos.copy();
//				pos.add(Math.random()*EvoCanvas.WIDTH*2-EvoCanvas.WIDTH, Math.random()*EvoCanvas.WIDTH*2-EvoCanvas.WIDTH);
//				array.add(new EnemyEntity(pos, (int) (Math.random()*5), (int) (Math.random()*4)));
			}
		}
	}
	
	public static void spawnBakteria(){
		Vector2d pos = worldPos.copy();
		pos.add(Math.random()*EvoCanvas.WIDTH-EvoCanvas.WIDTH/2, Math.random()*EvoCanvas.WIDTH-EvoCanvas.WIDTH/2);
		Game.addEntity(new Bakteria(pos));
	}
	
	
	public static void spawnEnemy(int size){
		Vector2d pos = Game.getPlayer().getPos().copy();
		
		double dist = Math.random() * KILLDISTANCE/2;
		double dist2 = Math.random() * KILLDISTANCE/2;
		
		if(Math.random() > 0.5)
			dist += 200;
		else
			dist2 += 200;
			
		if(Math.random() > 0.5)
			dist = -dist;
		if(Math.random() > 0.5)
			dist2 = -dist2;
		
		pos.add(dist, dist2);
		
		Game.addEnemy(new EnemyEntity(pos, size, (int) (Math.random()*4)));
	}
}
