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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * This is the Circle class. This class defines the 
 * Circle object, which is used in the other two classes
 * for this project. 
 * @author Landon Crowther
 *
 */
public class Circle extends JComponent implements MouseListener, MouseMotionListener {

	private boolean selected;
	private Point offset;
	//determines if the circle will be filled or not
	private boolean fill;
	//true if circle should be red, false otherwise
	private boolean isRed;

	/**
	 * Circle constructor. Constructor takes in x/y coordinates, as well as width and
	 * height. Constructor adds MouseListener and MouseMotionListener. Constructor
	 * also randomly determines the color of the circle and whether or not it is filled
	 * @param _x
	 * @param _y
	 * @param _width
	 * @param _height
	 */
	public Circle(int _x, int _y, int _width, int _height) 	{

		//set up location and size 
		this.setLocation(_x, _y);		
		this.setSize(_width, _height);	
		
		// add MouseListener and MouseMotionListener
		addMouseListener(this);
		addMouseMotionListener(this);

		//Determine if the oval should be filled or not
		if ( Math.random() >= 0.5 ) 
			this.fill = true;
		else
			this.fill = false;

		//Determine which color the circle should be:
		if (Math.random() >= 0.5 ) 
			this.isRed = true;
		else
			this.isRed = false;
	}
	
	/**
	 * A second constructor with the option to add a name parameter.
	 * @param _x
	 * @param _y
	 * @param _width
	 * @param _height
	 * @param _name
	 */
	public Circle(int _x, int _y, int _width, int _height, String _name) 	{

		//assign name:
		String name = _name;
		
		//set up location and size 
		this.setLocation(_x, _y);		
		this.setSize(_width, _height);	
		
		// add MouseListener and MouseMotionListener
		addMouseListener(this);
		addMouseMotionListener(this);

		//Determine if the oval should be filled or not
		if ( Math.random() >= 0.5 ) 
			this.fill = true;
		else
			this.fill = false;

		//Determine which color the circle should be:
		if (Math.random() >= 0.5 ) 
			this.isRed = true;
		else
			this.isRed = false;
	}


	/**
	 * Paints circle image on the window. Depending on the fill and isRed instance variables
	 * that are randomly assigned, the paintComponent method will create the circle 
	 * accrodingly. 
	 */
	@Override
	public void paintComponent(Graphics graphics) {

		//Determine which color the oval should be
		if (this.isRed == true )
			graphics.setColor(Color.RED);
		else 
			graphics.setColor(Color.BLUE);

		//draw oval or fill oval, depending on the fill status of the circle
		if (this.fill == true) {
			graphics.fillOval(0, 0, this.getWidth(), this.getHeight());
		}
		else
			graphics.drawOval(0, 0, this.getWidth(), this.getHeight());

	}

	/**
	 * toggles the selected instance variable, and sets the offset.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		this.selected = true;
		this.offset = (e.getPoint());
		//offset = LOCAL
		System.out.println(offset.getX());
		
	}

	/**
	 * adjusts the position of the circle based on the local and global position of the
	 * mouse event
	 * local = where the mouse was pressed within the circle
	 * global = where mouse pressed on screen
	 */
	@Override
	public void mouseDragged(MouseEvent event) {

		this.setLocation( (int)(event.getXOnScreen() - offset.getX()), 
				(int)(event.getYOnScreen() - offset.getY()) );
		
		

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// no use in this program
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// no use in this program
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// no use in this program
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// no use in this program
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// no use in this program
	}
	
//end of class
}