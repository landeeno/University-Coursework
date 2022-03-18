
/**
 * @author H. James de St. Germain Brent Collins and Landon Crowther
 * @date   Spring 2007
 *
 * The star_field class is a very simple graphical simulation
 * of gravitational forces on bodies of mass.
 * 
 * The star_field class is itself a JPanel for graphical representation
 * of the simulation.
 * 
 * We simulate several types of heavenly bodies, planets, flotsam, 
 * and stars... others could be easily addedn
 * 
 * At each time_step, we calculate the force of gravity for
 * every object based on the other heavenly bodies.  When we
 * add all of these "vectors" (the pull of each body) together
 * we get the new "acceleration force" that should be applied
 * to the heavenly body.
 */
package stars;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Star_Field extends JPanel
		implements MouseListener, MouseWheelListener, ActionListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * The following are public and used by the Simulator thread
	 * 
	 * move - start or pause the simulation (used by the simulator thread)
	 * gravity_calculations - used for our GCPS calculations total_calc_time - a
	 * sum of all the time used in the gravity computations
	 *
	 * The rest are private:
	 * 
	 * radiuse_of_universe - how much of the solar system to show in the GUI
	 * (modifiable) center_of_system - where to center the simulation in the GUI
	 * show_vectors - display direction and velocity of bodies time_step - our
	 * way of discretizing time.
	 * 
	 * initial_start_time - used for our FPS (frames per second) calculation
	 * gui_paint_counts - used in the FPS
	 * 
	 * give_flotsam_initial_velocity - let the flotsam fly freely (or not)
	 *
	 * 
	 * 
	 */

	public boolean move = false;
	public long gravity_calculations;
	public double total_calc_time;

	private double radius_of_universe = Solar_System_Facts.saturn_distance;
	private Geometry_Vector center_of_system = new Geometry_Vector(0, 0);
	private boolean show_vectors = false;
	private double time_step = 0.1;

	// these four are used for translating the screen based on dragging the
	// mouse
	private double offset_x = 0;
	private double offset_y = 0;
	private double prev_offset_x = 0;
	private double prev_offset_y = 0;

	private double mouse_click_x = 0;
	private double mouse_click_y = 0;

	private long initial_start_time;
	private long gui_paint_counts;
	private boolean give_flotsam_initial_velocity = true;

	ArrayList<Satellite> planets = new ArrayList<Satellite>();

	/**
	 * Create the star field GUI simulator
	 * 
	 * @param height
	 *            - how big is the display
	 * @param width
	 *            - how big is the display
	 */
	public Star_Field(int width, int height) {
		this.setSize(width, height);
		this.setLocation(0, 0);
		this.setLayout(null);
		this.create_planets();
		this.add_planets_to_solar_system(1);
		this.setBackground(Color.black);

		this.addMouseListener(this);
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);
		this.grabFocus();
	}

	/**
	 * Set all our measurements to 0, so we can try again with another number of
	 * objects.
	 */
	private void reset_stats() {
		this.initial_start_time = System.nanoTime();
		this.gravity_calculations = 0;
		this.gui_paint_counts = 0;
		this.total_calc_time = 0;
	}

	/**
	 * Our star field panel has a few more interesting things to do when it is
	 * being drawn:
	 * 
	 * 1) it needs to make sure all of the NBodies (the satellite JComponents)
	 * are drawn. fortunately, java does this for us (via the super notation)
	 * 
	 * 2) draw some information about the status of the simulation
	 * 
	 * 3) draw lines on the screen to represent vectors. (note the vectors are
	 * drawn "below" the bodies because the bodies are components on top of the
	 * "background" which is where the vectors are drawn.
	 * 
	 * 
	 * @param g
	 *            - the graphics object
	 */
	
	public void paintComponent(Graphics g) {
		this.setForeground(Color.black);
		super.paintComponent(g);

		Font f = g.getFont();
		g.setFont(f.deriveFont(20.0f));

		g.translate((int) offset_x, (int) offset_y);

		this.gui_paint_counts++;

		double total_time = (System.nanoTime() - this.initial_start_time) / 1000000000.0;

		String fps = String.format("FPS: %.3f, Particles: %3d", this.gui_paint_counts / total_time,
				this.getComponentCount());

		String gcps = String.format("GCPS: %10.3f, Time to Calculate: %f", this.gravity_calculations / total_time,
				this.total_calc_time / this.gravity_calculations / 1000000000.0);

		String u_radius = String.format("Solar System Radius: %.1f km", this.radius_of_universe);

		String time_step_str = String.format("Time Step: %.4f", this.time_step);

		//////////////////////////////
		// Put up the stats!

		g.setColor(Color.white);
		g.drawString(gcps, this.getWidth() / 2, 50);
		g.drawString(fps, this.getWidth() / 2, 75);

		g.drawString(u_radius, this.getWidth() / 2, 125);
		g.drawString(time_step_str, this.getWidth() / 2, 150);

		/////////////////////////////
		// Draw the Vectors of the Bodies for an Extra Nice Touch
		//
		if (this.show_vectors) {
			Component[] comps = this.getComponents();
			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof Satellite) {
					Satellite sat = (Satellite) comps[i];
					int x = (int) (sat.getX() + sat.getWidth() / 2.0);
					int y = (int) (sat.getY() + sat.getHeight() / 2.0);

					Geometry_Vector v = new Geometry_Vector(sat.get_velocity());
					v.divide_by(1000);
					g.setColor(Color.white);
					g.drawLine(x, y, (int) (x + v.x), (int) (y + v.y));
				}
			}
		}

	}

	/**
	 * Create the menu bar
	 */

	public JMenuBar create_up_menu_bar() {

		JMenuBar menu_bar = new JMenuBar(); // create the menu bar

		JMenu file_menu = new JMenu("Simulator"); // create the first menu
		JMenu flotsam_menu = new JMenu("Flotsam");
		JMenu solar_system_menu = new JMenu("Solar System");

		JMenuItem show_vectors_menu_item = new JMenuItem("Show Vectors");
		JMenuItem start_simulation = new JMenuItem("Start Simulation");

		start_simulation.addActionListener(this);
		show_vectors_menu_item.addActionListener(this);

		file_menu.add(start_simulation);
		file_menu.add(show_vectors_menu_item);

		JMenuItem flot_start_speed = new JMenuItem("Without Initial Velocity");
		flot_start_speed.addActionListener(this);
		flotsam_menu.add(flot_start_speed);

		for (int i = 0; i <= 2000; i += 100) {
			JMenuItem flot = new JMenuItem("" + i);
			flot.setActionCommand("flot_" + i);
			flot.addActionListener(this);
			flotsam_menu.add(flot);
		}

		for (int i = 0; i < 8; i++) {
			JMenuItem planet_menu = new JMenuItem("" + i);
			planet_menu.setActionCommand("planet_" + i);
			planet_menu.addActionListener(this);
			solar_system_menu.add(planet_menu);
		}

		JMenuItem planet_menu = new JMenuItem("oops");
		planet_menu.addActionListener(this);
		solar_system_menu.add(planet_menu);

		menu_bar.add(file_menu);
		menu_bar.add(flotsam_menu);
		menu_bar.add(solar_system_menu);

		return menu_bar;
	}

	/**
	 * 
	 */
	private void create_planets() {
		// create all the planets (though we might not use them....)
		Star sun = new Star(this.center_of_system.x, this.center_of_system.y, 0, 0, Solar_System_Facts.sun_mass,
				Solar_System_Facts.sun_radius, "Sun");

		Planet mercury = new Planet(Solar_System_Facts.mercury_distance, 0.0, 0.0, Solar_System_Facts.mercury_speed,
				Solar_System_Facts.mercury_mass, Solar_System_Facts.mercury_radius, "Mercury");
		Planet venus = new Planet(Solar_System_Facts.venus_distance, 0.0, 0.0, Solar_System_Facts.venus_speed,
				Solar_System_Facts.venus_mass, Solar_System_Facts.venus_radius, "Venus");
		Planet earth = new Planet(Solar_System_Facts.earth_distance, 0.0, 0.0, Solar_System_Facts.earth_speed,
				Solar_System_Facts.earth_mass, Solar_System_Facts.earth_radius, "Earth");
		Planet mars = new Planet(Solar_System_Facts.mars_distance, 0, 0, Solar_System_Facts.mars_speed,
				Solar_System_Facts.mars_mass, Solar_System_Facts.mars_radius, "Mars");
		Planet jupiter = new Planet(Solar_System_Facts.jupiter_distance, 0, 0, Solar_System_Facts.jupiter_speed,
				Solar_System_Facts.jupiter_mass, Solar_System_Facts.jupiter_radius, "Jupiter");
		Planet saturn = new Planet(Solar_System_Facts.saturn_distance, 0, 0, Solar_System_Facts.saturn_speed,
				Solar_System_Facts.saturn_mass, Solar_System_Facts.saturn_radius, "Saturn");

		sun.setForeground(Color.yellow);
		mercury.setForeground(Color.orange);
		venus.setForeground(Color.blue);
		earth.setForeground(Color.green);
		mars.setForeground(Color.red);
		jupiter.setForeground(Color.magenta);
		saturn.setForeground(Color.blue);

		sun.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());
		mercury.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());
		venus.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());
		earth.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());
		mars.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());
		jupiter.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());
		saturn.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
				this.getHeight());

		this.planets.add(sun);
		this.planets.add(earth);
		this.planets.add(mercury);
		this.planets.add(venus);
		this.planets.add(mars);
		this.planets.add(jupiter);
		this.planets.add(saturn);

	}

	/**
	 * Create a new solar system for the simulation.
	 * 
	 * Remove all the old planets (or actually anything that is not a flotsam)
	 * and replace it with the requested number of planets
	 * 
	 * Special case - if count = 8 then simply add a black hole to the system
	 *
	 * @param count
	 *            how many bodies (0 - only sun, 1- only earth, 2 - add from
	 *            inner out
	 */
	public void add_planets_to_solar_system(int count) {
		if (count >= 8) {
			Black_Hole bh = new Black_Hole(-Solar_System_Facts.jupiter_distance, Solar_System_Facts.saturn_distance,
					0.0, 0.0, Solar_System_Facts.sun_mass * 10, 0.0, "Black Hole");
			bh.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
					this.getHeight());
			this.add(bh, 0);

		} else {
			
			Component[] comps = this.getComponents();

			for (int i = 0; i < comps.length; i++) {

				this.remove(comps[i]);

			}

			// add the planets back
			for (int i = 0; i < count; i++) {
				this.add(planets.get(i));
			}
		}

	}

	/**
	 * Remove all the Flotsam from the simulation
	 */
	public void remove_flotsam() {
		Component[] comps = this.getComponents();
		for (int i = 0; i < comps.length; i++) {

			
			if (comps[i].getName().equals("Flotsam")) {
				this.remove(comps[i]);

			}
		}
		this.repaint();
	}

	/**
	 * Add new Flotsam to the Simulation
	 * 
	 * All Flotsam is contrained to the Solar System ( or at least to the orbit
	 * of Neptune) upon creation.
	 *
	 * @param how_many
	 *            - how many flotsam to create
	 */
	public void create_flotsam(int how_many) {

		Random generator = new Random();

		for (int i = 0; i < how_many; i++) {
			Flotsam flotsam = null;

			double radius = generator.nextInt((int) Solar_System_Facts.neptune_distance);

			double theta = generator.nextDouble() * Math.PI * 2;

			int x = (int) (radius * Math.cos(theta));
			int y = (int) (radius * Math.sin(theta));

			if (this.give_flotsam_initial_velocity) {
				flotsam = new Flotsam((double)x, (double)y, (double)generator.nextInt(50000) - 25000, // velocity
						generator.nextInt(50000) - 25000, 100, 100, "Flotsam"); // velocity
				
			} else {
				flotsam = new Flotsam((double)x, (double)y, 0.0, 0.0, 100, 100, "Flotsam");
				
			}
			flotsam.setForeground(Color.DARK_GRAY);
			
			flotsam.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
					this.getHeight());
			this.add(flotsam, 0);
		}
	}

	/**
	 * 
	 * HERE IS THE SIMULATION
	 * 
	 * (all the rest is fluff)
	 * 
	 * This function compares all objects to all other objects to determine the
	 * force of gravity on an object.
	 * 
	 * @param me
	 *            - the object we are comparing everyone else to (and thus not
	 *            to be used in our loop)
	 * 
	 * @return - the amount of force on (me)
	 */

	public Geometry_Vector calculate_gravitational_acceleration(Satellite me) {
		Geometry_Vector force = new Geometry_Vector(0, 0);
		Geometry_Vector direction = new Geometry_Vector(0, 0);

		Component[] comps = this.getComponents();

		for (int i = 0; i < comps.length; i++) {
			if (comps[i] instanceof Satellite) {
				Satellite sat = (Satellite) comps[i];

				if (sat != me) {
					direction = new Geometry_Vector(sat.get_position());
					direction.subtract_from_me(me.get_position());

					if (direction.magnitude() < 100) {
						System.out.println("collision,not counting gravity");
					} else {
						double distance = direction.magnitude();
						double dist_squared = distance * distance;
						double total_mass = sat.get_mass() * me.get_mass();

						double new_force = total_mass / dist_squared;
						direction.normalize();

						direction.multiply_me_by(new_force);

						force.add_to_me(direction);
					}
				}
			}
		}

		/*
		 * Note: We have to divide by our mass because the computed force is the
		 * total gravity pulling on both bodies (or us and all the rest).
		 * Consider two bodies, the earth and sun. The sun pulls the earth a lot
		 * and the earth pulls the sun almost nothing at all.
		 */

		force.divide_by(me.get_mass());
		force.multiply_me_by(Solar_System_Facts.gravitational_constant);
		force.divide_by(10000); // transform km^2 into m^2 ?

		return force;
	}

	/**
	 * Update Positions of all the Bodies
	 * 
	 * Loop through all of the objects and update each one's position based on
	 * the gravity of the other Objects.
	 * 
	 * Note: All the updates are scaled by the global value timestamp, thus
	 * allowing for more accurate simulations (smaller time steps) or faster
	 * displays (larger time steps)..
	 * 
	 * Note: you can scale the timestep using the control mouse wheel
	 */
	public void update_positions() {
		Geometry_Vector force = new Geometry_Vector(0, 0);

		Component[] comps = this.getComponents();
		for (int i = 0; i < comps.length; i++) {
			if (comps[i] instanceof Satellite) {
				Satellite sat = (Satellite) comps[i];
				force = calculate_gravitational_acceleration(sat);
				sat.update_velocity(force, this.time_step);
				sat.update_position(this.time_step);
			}
		}

		for (int i = 0; i < comps.length; i++) {
			if (comps[i] instanceof Satellite) {
				Satellite sat = (Satellite) comps[i];
				sat.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
						this.getHeight());
			}
		}

		repaint();
	}

	/**
	 * Start or stop the simulation depending on the given parameter
	 * 
	 * We clean up the FPS stats and GCPS stats whenever we stop/start
	 * 
	 * @param start
	 *            - if true, start simulating
	 */
	public void start_or_pause_simulation(boolean start) {
		if (start) {
			this.reset_stats();
			this.move = true;
		} else {
			this.move = false;
			this.reset_stats();
		}
	}

	/**
	 * offset based on mouse drag
	 */
	public void mouseDragged(MouseEvent e) {
		this.offset_x = this.prev_offset_x + (e.getX() - this.mouse_click_x);
		this.offset_y = this.prev_offset_y + (e.getY() - this.mouse_click_y);
	}

	/**
	 * no affect when mouse moved
	 */
	public void mouseMoved(MouseEvent e) {
		/* nothing to do */
	}

	/**
	 * nothing is done for this event
	 */
	public void mouseEntered(MouseEvent e) {
		/* not needed */
	}

	/**
	 * nothing is done for this event
	 */
	public void mouseExited(MouseEvent e) {
		/* not needed */
	}

	/**
	 *
	 *Method to make a star go super nova, or planet explode if clicked on. 
	 *
	 * if the mouse is clicked on top of a star it will go super nova.
	 * if the mouse is clicked on top of a planet it will explode
	 *
	 * @param e
	 *            - the mouse event
	 */
	public void mousePressed(MouseEvent e) {
		//loop through the planets object to detect if we clicked on
		//a planet or a star
		for(Satellite p:planets) {
			//if star, go supernova
			if(p instanceof Star & p.getBounds().contains(e.getPoint())){
				Star sn = (Star) p;
				sn.goSuperNova();
				
				this.create_flotsam(1000);
			   
			  //if star,  explode it	
			} else if(p instanceof Planet & p.getBounds().contains(e.getPoint())) {
				Planet pn = (Planet) p;
								
				pn.explode();
				this.create_flotsam(100);
			}
		}

	}

	/**
	 * Nothing happens here
	 */
	public void mouseReleased(MouseEvent e) {
		// nothing to do here now
	}

	/**
	 * scale the size of the shown solar system
	 * 
	 * (or if control is pressed, change the time step) of the simulation
	 * 
	 */
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.isControlDown()) {
			if (e.getWheelRotation() > 0) {
				this.time_step *= 1.1;
			} else {
				this.time_step *= .9;
			}

			if (this.time_step > 50.0) {
				this.time_step = 50.0;
			}
			if (this.time_step < .1) {
				this.time_step = .1;
			}
		} else {
			if (e.getWheelRotation() > 0) {
				this.radius_of_universe *= 1.1;
			} else {
				this.radius_of_universe *= .9;
			}

			Component[] comps = this.getComponents();

			for (int i = 0; i < comps.length; i++) {
				if (comps[i] instanceof Satellite) {
					Satellite sat = (Satellite) comps[i];
					sat.update_screen_coordinates(this.center_of_system, this.radius_of_universe, this.getWidth(),
							this.getHeight());
				}
			}

		}
	}

	/**
	 * Deal with the menu bar events
	 * 
	 * @param e
	 *            the action to handle
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Show Vectors")) {
			this.show_vectors = true;
			JMenuItem mi = (JMenuItem) e.getSource();
			mi.setText("Hide Vectors");
		} else if (e.getActionCommand().equals("Hide Vectors")) {
			this.show_vectors = false;
			JMenuItem mi = (JMenuItem) e.getSource();
			mi.setText("Show Vectors");
		} else if (e.getActionCommand().equals("Start Simulation")) {
			this.start_or_pause_simulation(true);
			JMenuItem mi = (JMenuItem) e.getSource();
			mi.setText("Pause Simulation");
		} else if (e.getActionCommand().equals("Pause Simulation")) {
			this.start_or_pause_simulation(false);
			JMenuItem mi = (JMenuItem) e.getSource();
			mi.setText("Start Simulation");
		} else if (e.getActionCommand().contains("flot")) {
			this.reset_stats();
			this.remove_flotsam();
			String[] halves = e.getActionCommand().split("_");
			int count = Integer.parseInt(halves[1]);
			this.create_flotsam(count);
			this.repaint();
		} else if (e.getActionCommand().contains("planet")) {
			this.reset_stats();

			String[] halves = e.getActionCommand().split("_");
			int count = Integer.parseInt(halves[1]);
			this.add_planets_to_solar_system(count);
			this.repaint();
		} else if (e.getActionCommand().contains("oops")) {
			this.reset_stats();
			this.add_planets_to_solar_system(8);
			this.repaint();
		} else if (e.getActionCommand().equals("Without Initial Velocity")) {
			this.give_flotsam_initial_velocity = false;
			JMenuItem mi = (JMenuItem) e.getSource();
			mi.setText("With Initial Velocity");
		} else if (e.getActionCommand().equals("With Initial Velocity")) {
			this.give_flotsam_initial_velocity = true;
			JMenuItem mi = (JMenuItem) e.getSource();
			mi.setText("Without Initial Velocity");
		}

	}

	/**
	 * we are not concerned with this
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// do nothing
	}

}
