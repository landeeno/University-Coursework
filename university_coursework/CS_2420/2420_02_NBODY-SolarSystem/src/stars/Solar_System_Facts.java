/**
 * 
 */
package stars;

/**
 * @author H. James de St. Germain
 * @date Spring 2007
 * 
 *       Facts about our solar system courtesy of www.nineplanets.org
 *
 */
public class Solar_System_Facts {

	public final static double speed_scale = 1609.344; // transform miles per
														// second into meters
														// per second
	public final static double gravitational_constant = 6.67300 * 10e-11; // m^3
																			// /
																			// kg
																			// /
																			// s^2

	public final static double sun_mass = 1.989e30; // kg
	public final static double sun_radius = 696000; // km

	public final static double mercury_distance = 57910000; // km
	public final static double mercury_radius = 2439; // km
	public final static double mercury_mass = 3.30e23; // kg
	public final static double mercury_speed = 29 * speed_scale; // miles per
																	// second *
																	// kilometers
																	// per mile

	public final static double venus_distance = 108200000;
	public final static double venus_radius = 6052;
	public final static double venus_mass = 4.87e24;
	public final static double venus_speed = 21 * speed_scale;

	public final static double earth_distance = 149600000;
	public final static double earth_radius = 6378;
	public final static double earth_mass = 5.98e24;
	public final static double earth_speed = 17 * speed_scale;

	public final static double mars_distance = 227940000;
	public final static double mars_radius = 3397;
	public final static double mars_mass = 6.42e23;
	public final static double mars_speed = 15 * speed_scale;

	public final static double jupiter_distance = 778330000;
	public final static double jupiter_radius = 71492;
	public final static double jupiter_mass = 1.90e27;
	public final static double jupiter_speed = 8 * speed_scale;

	public final static double saturn_distance = 1426940000;
	public final static double saturn_radius = 60268;
	public final static double saturn_mass = 5.69e26;
	public final static double saturn_speed = 6 * speed_scale;

	public final static double neptune_distance = 4497070000.0;

	// distance radius mass
	// Uranus 2,870,990,000 25559 8.69e25
	// Neptune 4,497,070,000 24764 1.02e26
	// Pluto 5,913,520,000 1160 1.31e22

}
