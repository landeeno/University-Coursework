package stars;

import java.awt.Graphics;

/**
 * Represents a star, which is a subclass of satellite. In general this will be a very large body with
 * planets and other flotsam orbiting it
 * @author Brent Collins and Landon Crowther
 *
 */

public class Star extends Satellite {
	
	
	
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

	public Star(double x, double y, double velocity_x, double velocity_y, double my_mass, double my_radius,
			String name) {
		
		super(x, y, velocity_x, velocity_y, my_mass, my_radius, name);
		
		
	}

	
	
	
	/**
	 * Method to update the display size of a star object. The screen is scaled and the size of the object made
	 * convenient for viewing the entire solar system.
	 * @param radius_of_system - Radius of the system, used to scale the screen size.
	 */
	protected void update_display_size(double radius_of_system){
		//check to make sure don't divide by zero
		if (radius_of_system == 0) {
		    throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
		
		//scale the size multiplied by a scalar that "looks alright"
		double size2 = (this.radius/radius_of_system)*70000;
		
		this.setSize((int)size2, (int)size2);
	}
	/**
	 * Method to explode the star and remove it from the screen.
	 */
	public void goSuperNova() {
		
		//make the star appear to grow very fast
		this.setSize(10000, 100000);
		repaint();
		//effectively remove the star
		this.radius = 0;
		this.setSize(0, 0);
		this.mass = 0;
		
	}

}
