package de.timweb.ld48.evo.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import de.timweb.ld48.evo.game.EvolutionScreen;

public class PolygonTransformer {
	public static final int TRANSLATE = 200;
	private int minY;
	private int minX;
	private int maxY;
	private int maxX;
	private Polygon p;
	private float scalefactor;
	

	public PolygonTransformer(Polygon p, int maxRaster) {
		this.p = p;
		calcMaxPoints();

		int xDiff = maxX - minX;
		int yDiff = maxY - minY;

		int maxDiff = 0;
		if (xDiff > yDiff)
			maxDiff = xDiff;
		else
			maxDiff = yDiff;

		p.translate(-minX, -minY);
		
		scalefactor = maxRaster / (float) maxDiff;
		multiply(scalefactor);
		toRaster();

		makePoints();
		
		p.translate(TRANSLATE, TRANSLATE);
		
		calcTransPolygon();
	}

	private void makePoints() {

		int index1 = (int) (Math.random()*(p.npoints-2)+1); 
		int index2 = index1;
		while(index2 == index1){
			index2 = (int) (Math.random()*(p.npoints-2)+1);
		}

//		addPoint(0,2);
		addPoint(1,1);
	}

	private void addPoint(int x1, int y1) {
		int x = p.xpoints[p.npoints-1];
		int y = p.ypoints[p.npoints-1];
		
		p.addPoint(x-EvolutionScreen.RASTER*x1, y-EvolutionScreen.RASTER*y1);
	}

	private void toRaster() {
		for (int i = 0; i < p.npoints; i++) {
			p.xpoints[i] -= p.xpoints[i] % EvolutionScreen.RASTER;
			p.ypoints[i] -= p.ypoints[i] % EvolutionScreen.RASTER;
		}
	}

	private void calcMaxPoints() {
		minY = p.ypoints[0];
		minX = p.xpoints[0];
		maxY = p.ypoints[0];
		maxX = p.xpoints[0];

		for (int i = 0; i < p.npoints; i++) {
			if (p.ypoints[i] < minY)
				minY = p.ypoints[i];
			if (p.ypoints[i] > maxY)
				maxY = p.ypoints[i];

			if (p.xpoints[i] < minX)
				minX = p.xpoints[i];
			if (p.xpoints[i] > maxX)
				maxX = p.xpoints[i];

		}
	}

	private void multiply(float factor) {
		for (int i = 0; i < p.npoints; i++) {
			p.xpoints[i] *= factor;
			p.ypoints[i] *= factor;
		}
	}

	
	
	public Polygon getPolygon() {
		return p;
	}
	
	public void calcTransPolygon(){
		TransformPolygon result = TransformPolygon.p;
		
		for (int i = 0; i < p.npoints; i++) {
			result.addPoint(p.xpoints[i], p.ypoints[i]);
		}
	}

	public float getScale() {
		return scalefactor;
	}
}
