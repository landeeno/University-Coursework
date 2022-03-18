package stars;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author H. James de St. Germain, Brent Collins and Landon Crowther
 * 
 *         Satellites are bodies that float around each other pulling at each
 *         other with gravity. The mass of an object determines how much
 *         gravitational force it applies to others.
 * 
 *         This abstract Satellite class builds and gives general methods to all
 *         subclasses. Subclasses of satellite share many features, e.g. mass,
 *         radius, velocity, although the values differ greatly. For this reason
 *         most methods in this class are not abstract and class variables are
 *         declared.
 * 
 *         The methods and class variables in this class generally pertain to
 *         calculating the forces applied to an physical object in
 *         two-dimensional space.
 * 
 * 
 * 
 * 
 */

public abstract class Satellite extends JComponent {

	/**
	 * Any data that is common to all heavenly bodies should go here.
	 * 
	 * It is very likely that your Satellite will need to know _at least_ the
	 * following:
	 * 
	 * 
	 * 1) it's location (in x,y) 2) it's velocity (how fast it is moving in x,y)
	 * 3) it's mass
	 * 
	 *
	 * Please note that if a PLANET (which ISA Satellite) needs some specific
	 * data it would NOT go here, but instead in child class.
	 * 
	 * Note: Unlike the previous Circle Program, you _will_ need a location
	 * point for the Satellite separate from the JComponent. (JComponent x,y are
	 * integers and our simulation needs to use doubles. See the
	 * update_screen_coordinates method below which has the job of converting
	 * between Simulation x,y and screen x,y)
	 * 
	 * To make it easier to user your vector class, I suggest that the location
	 * be a vector as well as the velocity
	 *
	 */

	/**
	 * The location of the object in two-dimensional space, i.e. its x and y
	 * coordinates. See Geometry_Vector class for more details.
	 */
	private Geometry_Vector location;

	/**
	 * The current velocity of the object in two-dimensional space. This means
	 * at any given time the object will have two velocity values, but only one
	 * resultant velocity vector.
	 */
	private Geometry_Vector velocity;
	/**
	 * the mass of the object in kilograms
	 */
	protected double mass;
	/**
	 * the radius of the object in kilometers
	 */
	protected double radius;
	/**
	 * The name of the sattelite, e.g. "Earth"
	 */
	private String name;

	/**
	 * This serialVersionUID is there to keep the compiler happy. We don't have
	 * plans for future versions... but perhaps we will
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * returns the name and location of the object
	 */
	public String toString() {
		return "My name is: " + this.name + "\n I am located at x = " + this.location.x + "\n y = " + this.location.y;
	}

	/**
	 * Constructor for Satellite class
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
	 * 
	 * 
	 */

	public Satellite(double x, double y, double velocity_x, double velocity_y, double my_mass, double my_radius,
			String name) {
		this.location = new Geometry_Vector(x, y);
		this.velocity = new Geometry_Vector(velocity_x, velocity_y);
		this.mass = my_mass;
		this.radius = my_radius;
		this.name = name;

	}

	/**
	 * Function Purpose:
	 * 
	 * Change position of this object by adding this object's velocity to this
	 * object's position.
	 * 
	 * Math Example: our position is 1,1. The move vector is 10,10. Our new
	 * position is 11,11
	 *
	 *
	 * Abstract Method Choice: for this function, and many below, you must
	 * choose whether to implement it here or in all child classes. In this
	 * case:
	 * 
	 * 1) is "moving" the same regardless of type. Do stars and planets "move"
	 * through space the same. (Hint, I would expect that they do). If the
	 * answer is that all satellites move the same, then remove the "Abstract"
	 * label and implement the code here.
	 * 
	 * 2) if what it means to move is different based on the type of object
	 * (this would be true in a video game) then you would leave this as an
	 * abstract method and only implement it in the child classes.
	 * 
	 * @param dt
	 *            a small number representing how much time has gone by
	 * 
	 *            Note: we are using discrete math work in a continuous system.
	 *            This means that in the real world time is constantly flowing.
	 *            In a simluation time is broken into a stream of continuous
	 *            tiny time blocks.
	 * 
	 *            To make the simulation work we must scale by a time factor.
	 *            What this means can be shown by looking at a car that is
	 *            moving at 50 miles per hour down the highway. If we took the
	 *            car's current position and velocity and drew a line 50 miles
	 *            forward, we could try and say that is where the car would be
	 *            in an hour. In reality, that would be wrong (no doubt the
	 *            highway would change directions (at least a little) long
	 *            before that)).
	 * 
	 *            What we can do is take the car that is moving at 50 miles per
	 *            hour and compute how far it will go in, say, 1 second. Move it
	 *            forward that much, then recompute. If we do this 60*60 times
	 *            we will have a better approximation of where the car would be
	 *            in reality.
	 *
	 *            Note: the parameter dt represents the fraction of the velocity
	 *            that will be applied at a given instant.
	 * 
	 *            Math Example: Our current position is 5,5. Our current
	 *            velocity is 100,100. The time step is 0.05; Our new position
	 *            would be 10,10 -> 5,5 + (100,100 * .05)
	 * 
	 *            WARNING: even if not explicitly stated in other parts of this
	 *            documentation, all of the math should be done via the Vector
	 *            library.
	 * 
	 *            WARNING 2: This code does not change anything about the GUI
	 *            display of the object
	 * 
	 */
	public void update_position(double dt) {
		// copy velocity vector
		Geometry_Vector stepSize = new Geometry_Vector(this.velocity);

		// multiply velocity by delta time to get the step size
		// this gives us how much we need to change our position
		stepSize.multiply_me_by(dt);
		
		// add the step size to current location
		// which will update the location for the given amount of time
		this.location.add_to_me(stepSize);

	}

	/**
	 *
	 * change our velocity based on acceleration vector (the force applied on
	 * us) multiplied by the delta (time step)
	 * 
	 * @param acceleration
	 *            - a vector of the force being exerted on us
	 * 
	 *            Abstract Method Choice: Once again, you can implement here or
	 *            in the children, as appropriate
	 * 
	 *            Math Example:
	 * 
	 *            if my velocity is 100,100 (mph) and I have been told to
	 *            accelerate by 50,50 mph (in say dt = one second) then after
	 *            one second my new velocity would be 150,150 (mph).
	 * 
	 *            if my velocity is 100,0 and I am told to accelerate by 200,100
	 *            (in say one tenth of a second) we only apply one tenth of the
	 *            200,100, so the answer would be 100,0 + (200,100)/10 -->
	 *            120,10
	 * 
	 *            Thus the acceleration vector must be scaled by the time
	 *            duration (dt) before adding to the velocity vector
	 * 
	 */
	public void update_velocity(Geometry_Vector acceleration, double dt) {
		// copy the acceleration vector so when we add it to acceleration
		// it is only incremental, in accordance with our time step.
		Geometry_Vector accelerationCopy = new Geometry_Vector(acceleration);
		
		// multiply the vector by the time step to scale it
		accelerationCopy.multiply_me_by(dt);
		
		// add the acceleration to update the current velocity
		this.velocity.add_to_me(accelerationCopy);

	}

	/**
	 *
	 *
	 * As previously stated, the location of the Satellite in the simulation is
	 * not the same as the pictures location in our GUI. For example, the earth
	 * might be at: x,y --> 149600000, 0 but on the GUI the earth is displayed
	 * at 300,300
	 * 
	 * In this method, transform the current objects position from WORLD
	 * coordinates into GUI coordinates x,y.
	 * 
	 * In order to do this we have to know our relation to the center of the
	 * solar system and how wide the solar system is. We also have to know how
	 * wide the GUI window is.
	 * 
	 * Note: The first thing to in an update_screen_coordinates method would be
	 * to update the display size of the object. the last thing you would do is
	 * set the location of the object.
	 * 
	 * 
	 *
	 */
	public void update_screen_coordinates(Geometry_Vector system_center, double system_radius, int window_width,
			int window_height) {

		// scale the screen size to reflect the size of
		// the system we are currently looking at
		this.update_display_size(system_radius);

		// get the x and y coordinates from the satellite to transform them into
		// screen coordinates
		double screenX = this.location.x;
		double screenY = this.location.y;
		
		// store the x and y coordinates in a geometry vector
		Geometry_Vector simCoords = new Geometry_Vector(screenX, screenY);
		
		// Make the coordinates proportionate to the radius
		simCoords.divide_by(2 * system_radius);
		
		// multiply the proportionate values by the screen size to obtain how
		// many pixels wide and tall they should be
		simCoords.x = ((simCoords.x * window_width) + (window_width / 2)) - this.getWidth() / 2;
		simCoords.y = ((simCoords.y * window_height) + (window_height / 2)) - this.getHeight() / 2;

		// set location based off of the new scaled coordinates
		this.setLocation((int) simCoords.x, (int) simCoords.y);

	}

	/**
	 *
	 * return the mass of the satellite
	 *
	 * @return our mass
	 */
	public double get_mass() {
		return this.mass;
	}

	/**
	 *
	 * get the x and y coordinates of the satellite
	 *
	 * @return our position
	 */
	public Geometry_Vector get_position() {
		return this.location;
	}

	/**
	 * get the x and y velocities of the satellite
	 * 
	 * @return our velocity
	 */
	public Geometry_Vector get_velocity() {
		return this.velocity;
	}

	/**
	 *
	 * based on our physical size, change the gui component to be large enough
	 * to show itself....
	 * 
	 * unfortunately, the scales of the solar system are so large that we
	 * wouldn't be able to see more than one planet at a time if we simply
	 * divided the radius of the object by the radius of the solar system, so we
	 * must scale the satellite by some arbitrary "looks good" factor. You will
	 * have to play with this to find an appropriate size such that we can see
	 * everything, but they don't overlap each other
	 * 
	 * Because the objects in our solar system vary so much in size we need to
	 * implement this method in the subclasses
	 * 
	 * Abstract Method: Consider the problems caused by the scales involved in
	 * "Seeing" the solar system. Choice 1) If all satellites should be drawn at
	 * the same scale, implement the code here. Choice 2) If all satellites need
	 * to be drawn at a larger scale, then implement this in the child classes.
	 * Choice 3) If some satellites need to be drawn at a larger scale, but most
	 * can be drawn at a common scale, write the code here, then override it in
	 * the appropriate child class.
	 * 
	 * 
	 * Note: this function should use the JComponent Set Size function to change
	 * the size of the GUI display of the object.
	 * 
	 * Note 2: we take in the radius_of_system parameter to allow this function
	 * to know how big the displayed area is which can factor into how big we
	 * draw our planets. To start you may want to make all displayed objects the
	 * same size regardless, then see how changing their size affects the
	 * simulation
	 * 
	 * Note 3: in general, the larger the region we want to see on the screen,
	 * the smaller the satellites should be drawn
	 * 
	 * @param radius_of_universe
	 *            - how far across the displayable universe (in our standard
	 *            case we set the orbit of saturn as this value)
	 */
	protected abstract void update_display_size(double radius_of_system);

	/**
	 * get the name of the satellite
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Paint the satellite object
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillOval(0, 0, this.getWidth(), this.getHeight());
	}

}
