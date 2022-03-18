/**
 * Landon Crowther
 * u0926601
 * 1/19/2017
 * CS - 2420
 * Assignment 1
 * 
 * I pledge that the work done here was my own and that 
 * I have learned how to write this program, such that I 
 * could throw it out and restart and finish in a timely 
 * manner. I am not turning in any work that I cannot 
 * understand, describe, or recreate.
 * 	-- Landon Crowther
 * 
 */

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Jiggler is the backbone of this project. This is essentially
 * what moves all the components and keeps track of FPS	
 * @author Landon Crowther
 *
 */
public class Jiggler extends Thread {

	protected ArrayList<JComponent> drawables;
	private JLabel fps;
	private boolean spreadOut;


	/**
	 * Constructor. Assigns instance variables to parameters. 
	 * Sets the "spreadOut" instance variable to true.
	 * 
	 * to stop circles from spreading, set spreadOut to false
	 * 
	 * @param fps
	 * @param drawables
	 */
	public Jiggler(JLabel fps, ArrayList<JComponent> drawables)	{

		this.fps = fps;
		this.drawables = drawables;
		//this can be set to true or false - depends on if the user
		//wants the circles to spread out or not
		this.spreadOut = true;	

	}

	/**
	 * This method is what causes the circles to jiggle.
	 * To randomize the movement, I generated a random number using
	 * the Math.random() method. If this number was between 0-0.333,
	 * I assigned the value of -1. If it was between 0.333-0.666,
	 * I assigned the value of  0. If the number was greater than 0.666,
	 * I assigned the value of +1. I did this for the x and y directions,
	 * using two different random numbers.
	 */
	public void move(JComponent c) {

		//generate a random number to help determine which way the circle will move
		double randomNum= Math.random();

		//declare variable for the x & y position adjustment
		int xAdjust;
		int yAdjust;

		//determine if xAdjust will be +1, 0, or -1 based on the random number (arbitrarily assigned)
		if (randomNum < 0.333) 
			xAdjust = -1;
		else if (randomNum >= 0.666)
			xAdjust = 1;
		else 
			xAdjust = 0;

		//reset the random number
		randomNum = Math.random();

		//repeat process for y direction
		if (randomNum < 0.333) 
			yAdjust = -1;
		else if (randomNum >= 0.666)
			yAdjust = 1;
		else 
			yAdjust = 0;

		//reset the location of circle by adding the adjustment to current location
		c.setLocation(c.getX() + xAdjust, c.getY() + yAdjust);

	}

	/**
	 * This method determines if two circles (which are also JComponents) are overlapping.
	 * 
	 * It functions in the following manner:
	 * 
	 * 1) it calculates the center of both circles using geometry and getWidth/getHeight functions
	 * 2) it assigns a radius to each circle. The radius is half of the height or width: whichever is larger
	 * 3) it calculates the distance between the center points of the circle, using the distance formula
	 * 4) it compares the combined radii of the circles to the distance between them to determine if they are overlapping
	 * 
	 * @param circle1
	 * @param circle2
	 * @return true if circles overlap, false otherwise
	 */
	public boolean overlap(JComponent circle1, JComponent circle2) {

		//circle 1 coordinates: 
		int circle1CenterX = (circle1.getX() + circle1.getWidth())/2;
		int circle1CenterY = (circle1.getY() + circle1.getHeight())/2;
		//determine radius:
		int circle1Radius;
		if (circle1.getHeight() > circle1.getWidth())
			circle1Radius = circle1.getHeight()/2;
		else
			circle1Radius = circle1.getWidth()/2;

		//circle 2 coordinates:
		int circle2CenterX = (circle2.getX() + circle2.getWidth())/2;
		int circle2CenterY = (circle2.getY() + circle2.getHeight())/2;		
		//determine radius:
		int circle2Radius;
		if (circle2.getHeight() > circle2.getWidth())
			circle2Radius = circle2.getHeight()/2;
		else
			circle2Radius = circle2.getWidth()/2;

		//calculate distance between circles
		double distanceBetweenCircle = Math.sqrt( ((circle1CenterX - circle2CenterX)*(circle1CenterX - circle2CenterX)) 
				+ ((circle1CenterY - circle2CenterY)*(circle1CenterY - circle2CenterY) ));

		//determine if circles are overlapping
		if ( (circle1Radius + circle2Radius) < distanceBetweenCircle )
			return false;
		else
			return true;

	}

	/**
	 * Method that is run continuously until program is terminated. 
	 * This method does a few things:
	 * 
	 * 1) Calculates frames/second
	 * 2) Moves the circles
	 * 3) Determines if circles are overlapping or not
	 * 4) Adjusts circle position if circles are overlapping
	 * 5) Updates the frames per second JLabel
	 */
	@Override
	public void run()	{

		//start counting frames and timing
		long frameCount = 0;
		long startTime = System.nanoTime();
		long elapsedTime;

		//for the Thread
		while (true) {
			//move each circle randomly
			for (JComponent circle : drawables) {
				move(circle);				
			}

			//check that the instance variable, spreadOut, is set to true
			if (spreadOut == true) {

				//nested loop that checks for overlapping 
				for (JComponent circle1 : drawables) {
					for (JComponent circle2 : drawables) {

						//if two circles (that are not the same) are overlapping, enable spreadOut method
						if ((overlap(circle1, circle2) == true) && (circle1 != circle2)) {
							spreadOut(circle1, circle2);
						}
					}
				}
			}
			//update frameCount
			frameCount++;

			//update time
			elapsedTime = (System.nanoTime() - startTime);
			elapsedTime = elapsedTime/1_000_000_000;

			//for some reason when I didn't have this statement here, I would get a divide
			//by 0 error. This statement was necessary to get everything to work
			if (elapsedTime > 0) {

				//update fps JLabel
				fps.setText("FPS: " + (double)(frameCount/elapsedTime));	

				//print out FPS for graphing purposes
				if (frameCount % 50 == 0) {
					System.out.println("Frame Count:\t" + frameCount + "\t" + "FPS:\t" + (double)(frameCount/elapsedTime));
				}

			}


		}
	}

	/**
	 * This method causes circles to spread out. This method is only called if two circles 
	 * are overlapping. If this is the case, the method compares the position of the circles.
	 *
	 * Whichever circle has a more positive x coordinate gets moved 1 unit in the x direction, 
	 * and the same follows for the y coordinate
	 * @param circle1
	 * @param circle2
	 */
	private void spreadOut(JComponent circle1, JComponent circle2) {

		if (circle1.getX() > circle2.getX() ) 
			circle1.setLocation(circle1.getX() + 1, circle1.getY());
		else
			circle1.setLocation(circle1.getX() - 1, circle1.getY());

		if (circle1.getY() > circle2.getY()) 
			circle1.setLocation(circle1.getX(), circle1.getY() + 1);
		else 
			circle1.setLocation(circle1.getX(), circle1.getY() - 1);

	}
	//end of class
}
