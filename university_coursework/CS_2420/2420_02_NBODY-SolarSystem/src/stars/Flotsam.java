package stars;

import java.awt.Graphics;

/**
 * Represents flotsam, which is a subclass of satellite. In general this is
 * small debris like objects that are found in a solar system.
 * 
 * @author Brent Collins and Landon Crowther
 *
 */

public class Flotsam extends Satellite {
	/**
	 * constructor for class which is the same as its superclass Satellite. For
	 * more information see Satellite class.
	 * 
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

	public Flotsam(double x, double y, double velocity_x, double velocity_y, double my_mass, double my_radius,
			String name) {
		super(x, y, velocity_x, velocity_y, my_mass, my_radius, name);

	}

	/**
	 * paint component method which will make the flotsam rectangles as opposed
	 * to ovals, like its superclass.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	protected void update_display_size(double radius_of_system) {
		// check to make sure don't divide by zero
		if (radius_of_system == 0) {
			throw new IllegalArgumentException("Argument 'divisor' is 0");
		}

		double size2 = (this.radius / radius_of_system * 30000000);

		this.setSize((int) size2, (int) size2);
	}

}
