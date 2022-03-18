package lab4;

import javax.swing.*;

/**
 * GraphicsWindow.java
 * 
 * This class provides top-level graphics window. Do not modify for Lab 4.
 */
public class GraphicsWindow extends JFrame {

	/**
	 * Launches a GraphicsWindow.
	 */
	public static void main(String[] args) {
		GraphicsWindow g = new GraphicsWindow();
		g.setSize(400, 400);
		g.setVisible(true);
	}

	/**
	 * Constructs a custom CS 1410 graphics window.
	 */
	public GraphicsWindow() {
		setTitle("CS 1410 Graphics Window");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		DrawingArea area = new DrawingArea();
		setContentPane(area);
	}
}
