package stars;

import java.awt.Graphics;

/**
 * Represents a planet, which is a subclass of satellite. In general this will be a large body orbiting
 * a star, also subclass of satellite.
 * @author Brent Collins and Landon Crowther
 *
 */

public class Planet extends Satellite {
	
	/**
	 * constructor for class which is the same as its superclass Satellite. For more information see Satellite class.
	 * @param x
	 *            - where (in 2D) we are in the solar system
	 * @param y
	 *            - where (in 2D) we are in the solar system
	 * @param velocity_x
	 *            - how fast in 2D we are moving in meters per second
	 * @param velocity_y
	 *            - how fast in 2D we are moving in meters per second
	 * @param my_mass
	 *            - how big we are (in kilograms)
	 *            
	 * @param my_radius
	 *            - Radius of object in kilometers
	 * @param name
	 *            - What do we call ourselves
	 */

	public Planet(double x, double y, double velocity_x, double velocity_y, double my_mass, double my_radius,
			String name) {
		super(x, y, velocity_x, velocity_y, my_mass, my_radius, name);
	
		
	}
	
	/**
	 * Method to update the display size of a planet object. The screen is scaled and the size of the object made
	 * convenient for viewing the entire solar system.
	 * @param radius_of_system - Radius of the system, used to scale the screen size.
	 */
	protected void update_display_size(double radius_of_system){
		//check to make sure don't divide by zero
		if (radius_of_system == 0) {
		    throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
				
		//scale the screen size using a logarithmic function
		//the reason for this is we want small planets to show up, but larger planets
		//can not take up the entire screen. This logarithmic function works fairly well.
		double screenSize = (5*Math.log(this.radius/1000)/radius_of_system)*1_000_000_000;
		
		this.setSize((int)screenSize, (int)screenSize);
	}
	
	
	/**
	 * Method to explode the planet and remove it from the screen.
	 */
	public void explode() {
		
		
		//effectively remove the planet
		this.radius = 0;
		this.removeAll();
		this.setSize(0, 0);
		
		
	}



	

}
