package lab4;

import java.awt.*;
import javax.swing.*;

/**
 * DrawingArea.java
 * 
 * This class represents the area where custom graphics can be drawn.
 */
public class DrawingArea extends JPanel {

	public void drawHouse(Graphics g, int x, int y) {
		// y measures distance from the top.
		g.setColor(	Color.blue);                  // Draw using red.
		g.drawRect(x, y, 50, 30);               // Draw a rectangle.
		g.drawLine(x, y, x + 25, y - 15);       // Draw first line of a triangle.
		g.drawLine(x + 25, y - 15, x + 50, y);  // Draw second line of a triangle.
		g.drawOval(x+5,y+5, 8, 12);			// Draws window
		g.drawRect(x+30,y+10 , 10, 20);
	
	}
	public void paintComponent(Graphics g) {
		for (int i = 0; i < 5; i++) {
			drawHouse(g, 5 + i * 55, 50);
		}
	}

}
