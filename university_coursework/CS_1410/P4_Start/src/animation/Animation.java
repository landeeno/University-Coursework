/**
 * Landon Crowther
 * u0926601
 * CS 1410 - HW 4
 */

package animation;

import java.awt.Color;
import java.awt.Graphics;

public class Animation
{
	/**
	 * This is the method that you need to rewrite to create a custom animation. This method is called repeatedly as the
	 * animation proceeds. It needs to draw to g how the animation should look after t milliseconds have passed.
	 * 
	 * @param g Graphics object on which to draw
	 * @param t Number of milliseconds that have passed since animation started
	 */
	public static void paintFrame (Graphics g, int t, int height, int width){

		//initialize moving platform that stops when it needs to move downwards
		if (t < 2200) {
			drawPlatform(g, 200 + (int) t/7, 200, 1, Color.RED);
		}

		//initialize smiley face (that eventually falls off platform)
		if (t < 1000 ) {
			drawSmileyFace(g, 220, 160, 1, Color.BLUE);
		}

		//smiley face realizes its no longer on platform and turns into 
		//expanding frowney face
		if (t > 1000 && t < 2200) {
			drawFrowneyFace(g, 220, 160, t/1000., Color.black);
		}

		//frowney face stops expanding, but is still falling
		if (t > 2200 & t < 6440) {
			drawFrowneyFace(g, 220, 160 + (t-2200)/10, 2.2, Color.black);
		}

		//platform quickly moves downward to catch frowney face
		if (t > 2200 && t < 3600) {
			drawPlatform(g, 200 + (int)(2200/7), 200 + (int)((t-2200)/3), 1, Color.cyan);
		}

		//I made this variable b/c I was sick of typing the final 
		//"y" location of the platform
		int ylocPlatform = 200 + (int)((3600-2200)/3);

		//platform stops moving down, starts moving towards the left
		//to catch frowney face
		if (t > 3600 && t < 5600) {
			drawPlatform(g, (200 + (int)(2200/7)) - (int)((t-3600)/6), ylocPlatform, 1, Color.cyan);
		}

		//made this variable b/c I was sick of typing the final 
		//"x" location of the platform
		int xlocPlatform = 200 + (int)(2200/7) - (int)((5600 - 3600)/6);

		//platform is in the landing zone! platform stops moving
		if (t > 5600 && t < 6440) { 
			drawPlatform(g, xlocPlatform, ylocPlatform, 1, Color.cyan);
		}

		//Hoorayy! Platform caught the falling smiley face. Platform turns red and 
		//starts expanding.
		if (t > 6440) {
			drawPlatform(g, xlocPlatform, ylocPlatform, (t - 6440 + 2200)/2000., Color.red);
		}

		//frowney needs a second to realize it's no longer falling
		if (t > 6440 && t < 7300) {
			drawFrowneyFace(g, 220, (int)(160 + (6440-2200)/10), 2.2, Color.black);
		}

		//frowney face realizes it's okay. Frowney face turns back into a smiley face.
		if (t > 7300) {
			drawSmileyFace(g, 220, (int)(160 + (6440-2200)/10), 2.2, Color.blue);
		}
		
		//terminate after 10 seconds
		if (t>10000) {
			System.exit(0);
		}

		//the end. :)
	}




	/**
	 * this method draws a platform for the animation
	 * 
	 * @param g - graphics window
	 * @param x - starting x location
	 * @param y - starting y location
	 * @param scale - scale
	 * @param color - color
	 */
	public static void drawPlatform (Graphics g, int x, int y, double scale, Color color) {
		g.setColor(color);
		g.drawRect(x, y, (int)(200 * scale), (int)( 25 * scale));
	}

	/**
	 * this method draws a smiley face used for the animation
	 * 
	 * @param g - graphics window
	 * @param x - starting x location
	 * @param y - starting y location
	 * @param scale - scale
	 * @param color - color
	 */
	public static void drawSmileyFace (Graphics g, int x, int y, double scale, Color color) {
		g.setColor(color);
		//drawing outline for face
		g.drawRect(x, y, (int)(40 * scale) , (int)(40 * scale));
		//creating eyes
		g.drawLine(x + (int)(scale*10), (int)(scale*5) + y, x+ (int)(scale*10), y + (int)(scale*15));
		g.drawLine(x + (int)(scale*30), (int)(scale*5) + y, x+ (int)(scale*30), y + (int)(scale*15));

		//creating base line for smiley face
		g.drawLine((int)(scale*15) + x, y + (int)(scale *30), x + (int)(scale*25), y + (int)(scale*30));
		//creating smile
		g.drawLine(x + (int)(scale*15), y+ (int)(scale*30), x + (int)(scale*10), y+ (int)(scale*20));
		g.drawLine(x + (int)(scale*25), y + (int)(scale*30), x + (int)(scale*30), y + (int)(scale*20));
	}

	/**
	 * this method draws a frowney face used for the animation
	 * 
	 * @param g - graphics window
	 * @param x - starting x location
	 * @param y - starting y location
	 * @param scale - scale
	 * @param color - color
	 */
	public static void drawFrowneyFace(Graphics g, int x, int y, double scale, Color color) {
		g.setColor(color);
		//drawing outline for face
		g.drawRect(x, y, (int)(40 * scale) , (int)(40 * scale));
		//creating eyes
		g.drawLine(x + (int)(scale*10), (int)(scale*5) + y, x+ (int)(scale*10), y + (int)(scale*15));
		g.drawLine(x + (int)(scale*30), (int)(scale*5) + y, x+ (int)(scale*30), y + (int)(scale*15));

		//creating frown
		g.drawRect(x + (int)(10*scale), y + (int)(25*scale), (int) (20 * scale), (int) (10 * scale));
	}
}
