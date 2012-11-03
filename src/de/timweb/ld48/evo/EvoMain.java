package de.timweb.ld48.evo;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.timweb.ld48.evo.game.EvoCanvas;


public class EvoMain {
public static void main(String[] args) {
	EvoCanvas evocanvas = new EvoCanvas(1280, 720,10);
    JFrame frame = new JFrame("Shape the World");
    JPanel panel = new JPanel(new BorderLayout());
    panel.add(evocanvas);
    frame.setContentPane(panel);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    evocanvas.start();
}
}
