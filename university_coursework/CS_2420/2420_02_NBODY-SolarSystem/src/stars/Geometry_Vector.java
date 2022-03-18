
/**
 * 
 */
package stars;

import java.awt.geom.Point2D;

/**
 * 
 * This is a vector class which takes in two values corresponding to points in an x and y dimension.
 * This class offers basic vector arithmetic operations, e.g. multiply and add. The main purpose of this class
 * is to simulate vectors of velocity and location in two dimensions.
 * @author germain, Brent Collins, and Landon Crowther
 *
 */
public class Geometry_Vector extends Point2D.Double {

	

	
	/**
	 *
	 * Constructor
	 *
	 * @param x
	 *            - the X
	 * @param y
	 *            - and Y values
	 */
	public Geometry_Vector(double xx, double yy) {
		this.x = xx;
		this.y = yy;

		
	}


	/**
	 *
	 * A "Copy" constructor. Create ourself based on the given vector
	 * 
	 * @param the_copy
	 */
	public Geometry_Vector(Geometry_Vector the_copy) {
		
		this.x = the_copy.x;
		this.y = the_copy.y;
		

	}

	/**
	 * Add the components of the given vector to me.
	 *
	 * @param vector
	 */
	public void add_to_me(Geometry_Vector vector) {
		//add the values pairwise.
		this.x = this.x + vector.x;
		this.y = this.y + vector.y;
		
		

	}

	/**
	 * Subtract the components of the given vector from me.
	 * 
	 * @param vector
	 */
	public void subtract_from_me(Geometry_Vector vector) {
		//subtract the values pairwise
		this.x = this.x - vector.x;
		this.y = this.y - vector.y;
		
	}

	/**
	 * Divide my components by the scalar
	 * 
	 * @param scalar
	 */
	public void divide_by(double scalar) {
		//if scalar is 0 make x and y an arbitrarily large number
		if (scalar == 0) {
			this.x = 100000000;
			this.y = 100000000;
					
		   
		    
		}
		this.x = this.x / scalar;
		this.y = this.y / scalar;
		

	}

	/**
	 * Multiply my components by the scalar
	 * 
	 * @param scalar
	 */
	public void multiply_me_by(double scalar) {
		this.x = this.x * scalar;
		this.y = this.y * scalar;
		
	}

	/**
	 * @return my magnitude (the distance from the origin to my X,Y) 
	 * which is the root of the squared sum.
	 *         
	 */
	public double magnitude() {
		
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)); 
	}

	/**
	 * 
	 * @return the x y values and magnitude in a String
	 */
	public String toString() {
		return "(" + this.x + "," + this.y + ")" + "\n magnitude is :" + this.magnitude();
	}

	/**
	 * Take this vector and turn it into a vector of length 1. This is done by
	 * dividing each component (i.e., x,y) by the magnitude, i.e. the L2-norm.
	 * 
	 */
	public void normalize() {
		//store the magnitude in a variable
		double dist = (Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)));
		
		this.x = this.x / dist;
		this.y = this.y / dist; 
		

	}

}
