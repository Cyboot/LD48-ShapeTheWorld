package de.timweb.ld48.evo.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.timweb.ld48.evo.EvoMain;

public class ImageLoader {
	public static BufferedImage dnaBar;
	public static BufferedImage dnaBar_trans;
	public static BufferedImage healthBar;
	public static BufferedImage healthBar_trans;
	public static BufferedImage bg_earth;
	public static BufferedImage bg_water;
	public static BufferedImage bg_grass;
	public static BufferedImage bg_sand;
	public static BufferedImage bacteria;
	public static BufferedImage tang;

	public static BufferedImage enemy_tiny;
	public static BufferedImage enemy_small;
	public static BufferedImage enemy_medium;
	public static BufferedImage enemy_big;
	public static BufferedImage enemy_huge;
	public static BufferedImage enemy_tiny_danger;
	public static BufferedImage enemy_small_danger;
	public static BufferedImage enemy_medium_danger;
	public static BufferedImage enemy_big_danger;
	public static BufferedImage enemy_huge_danger;

	public static BufferedImage win;
	public static BufferedImage lost;
	
	public static BufferedImage tutorial1;
	public static BufferedImage tutorial2;
	public static BufferedImage tutorial3;
	
	public static BufferedImage blur;
	public static BufferedImage logo;
	public static BufferedImage evo_screen;
	public static BufferedImage dna;
	public static BufferedImage pickup_anim;
	public static BufferedImage sting;

	public static void init() {
		try {
			bg_earth = ImageIO.read(EvoMain.class.getResource("/bg_earth.png"));
			bg_water = ImageIO.read(EvoMain.class.getResource("/bg_water.png"));
			bg_grass = ImageIO.read(EvoMain.class.getResource("/bg_grass.png"));
			bg_sand = ImageIO.read(EvoMain.class.getResource("/bg_sand.png"));
			bacteria = ImageIO.read(EvoMain.class.getResource("/bacteria.png"));
			tang = ImageIO.read(EvoMain.class.getResource("/tang.png"));
			dna = ImageIO.read(EvoMain.class.getResource("/dna2.png"));
			dnaBar = ImageIO.read(EvoMain.class.getResource("/dnabar.png"));
			dnaBar_trans = ImageIO.read(EvoMain.class.getResource("/dnabar_trans.png"));
			healthBar = ImageIO.read(EvoMain.class.getResource("/healthbar.png"));
			healthBar_trans = ImageIO.read(EvoMain.class.getResource("/healthbar_trans.png"));
			evo_screen = ImageIO.read(EvoMain.class.getResource("/evo_screen.png"));
			pickup_anim = ImageIO.read(EvoMain.class.getResource("/pickup.png"));
			sting = ImageIO.read(EvoMain.class.getResource("/sting.png"));

			enemy_tiny = ImageIO.read(EvoMain.class.getResource("/enemy_32.png"));
			enemy_small = ImageIO.read(EvoMain.class.getResource("/enemy_48.png"));
			enemy_medium = ImageIO.read(EvoMain.class.getResource("/enemy_64.png"));
			enemy_big = ImageIO.read(EvoMain.class.getResource("/enemy_96.png"));
			enemy_huge = ImageIO.read(EvoMain.class.getResource("/enemy_128.png"));
			logo = ImageIO.read(EvoMain.class.getResource("/logo.png"));
			blur = ImageIO.read(EvoMain.class.getResource("/blur.png"));

			win = ImageIO.read(EvoMain.class.getResource("/win.png"));
			lost = ImageIO.read(EvoMain.class.getResource("/loose.png"));
			
			tutorial1 = ImageIO.read(EvoMain.class.getResource("/tutorial1.png"));
			tutorial2 = ImageIO.read(EvoMain.class.getResource("/tutorial2.png"));
			tutorial3 = ImageIO.read(EvoMain.class.getResource("/tutorial3.png"));

			enemy_tiny_danger = ImageIO.read(EvoMain.class.getResource("/enemy_32_danger.png"));
			enemy_small_danger = ImageIO.read(EvoMain.class.getResource("/enemy_48_danger.png"));
			enemy_medium_danger = ImageIO.read(EvoMain.class.getResource("/enemy_64_danger.png"));
			enemy_big_danger = ImageIO.read(EvoMain.class.getResource("/enemy_96_danger.png"));
			enemy_huge_danger = ImageIO.read(EvoMain.class.getResource("/enemy_128_danger.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getSubImage(BufferedImage img, int x, int y, int width) {
		return img.getSubimage(x * width, y * width, width, width);
	}

	public static BufferedImage getCutImage(BufferedImage img, int width) {
		return img.getSubimage(0, 0, width, img.getHeight());
	}

}
