package a12;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class Lifeform {
	private Point2D.Double location; 
	private double age;
	private ArrayList<BufferedImage> sprite;

	private double foodValue;
	private double lifeSpan;
	private double reproductionRate;
	private double metabolizeRate;
	private double moveSpeed;
	
	public Lifeform(double x, double y, double _foodValue, double _lifeSpan, 
			double _reproductionRate, double _metabolizeRate, double _moveSpeed) {
		foodValue = _foodValue;
		lifeSpan = _lifeSpan;
		reproductionRate = _reproductionRate;
		metabolizeRate = _metabolizeRate;
		moveSpeed = _moveSpeed;
		location = new Point2D.Double(x,y);
		
		age = 0;
	}
	
	/***
	 * Reproduce based on sub-class characteristics
	 * @param world
	 */
	public abstract void tryToReproduce(World world); 
	
	/***
	 * Add an increment to the age
	 * @param addAge
	 */
	public void incrementAge(double addAge) {
		age += addAge;
	}

	/***
	 * Getter for reproduction rate
	 * @return
	 */
	public double getReproductionRate() {
		return reproductionRate;
	}
	
	/***
	 * If a Lifeform is older than its lifespan, it dies
	 * @return
	 */
	public boolean tooOld() {
		return age > lifeSpan;
	}

	/***
	 * The foodvalue is the food reserves
	 * @return
	 */
	public boolean starve() {
		return foodValue < 0;
	}

	/***
	 * Clamp a value to a min or max value. The returned value will lie on the min-max range.
	 * @param x
	 * @param minX
	 * @param maxX
	 * @return
	 */
	public double clamp(double x, int minX, int maxX) {
		return Math.max(minX, Math.min(x,maxX));
	}
	
	/***
	 * Move a creature in a random direction and weighted distance.
	 * @param speedModifer
	 * @param maxX - world pixel width
	 * @param maxY - world pixel height
	 */
	public void move(double speedModifer, int maxX, int maxY) {
		Random rng = new Random();
		// Pick a distance to move based on a bell-curve distribution scaled
		// by the creature's speed and the terrain speed modifier
		double speed = moveSpeed * speedModifer * rng.nextGaussian();
		double directionAngle = rng.nextDouble()*360.0;
		double deltax = Math.cos(directionAngle/180.0 * Math.PI) * speed;
		double deltay = Math.sin(directionAngle/180.0 * Math.PI) * speed;

		// We clamp to the edges of the world.
		location.setLocation(clamp(location.getX() + deltax,0, maxX),
							clamp(location.getY() + deltay, 0, maxY));
	}

	/***
	 * Offset a new creature from its parent based on a weighted distance.
	 * @param distance
	 * @param maxX - world pixel width
	 * @param maxY - world pixel height
	 */
	public void offset(double distance, int maxX, int maxY) {
		Random rng = new Random();
		// Pick a distance to move based on a bell-curve distribution
		double speed = distance * rng.nextGaussian();
		double directionAngle = rng.nextDouble()*360.0;
		double deltax = Math.cos(directionAngle/180.0 * Math.PI) * speed;
		double deltay = Math.sin(directionAngle/180.0 * Math.PI) * speed;
		location.setLocation(clamp(location.getX() + deltax,0, maxX),
							clamp(location.getY() + deltay, 0, maxY));
	}

	/***
	 * Load an image for a creature based on a filename. The sub classes know their files.
	 * @param filename
	 */
	public void loadSprite(String filename) {
		sprite = new ArrayList<BufferedImage>();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sprite.add(img);
	}
	
	/***
	 * Get the creature pixel x coordinate
	 * @return x pixel location
	 */
	public double getLocationX() {
		return location.x;
	}
	
	/***
	 * Get the creature pixel y coordinate
	 * @return y pixel location
	 */
	public double getLocationY() {
		return location.y;
	}
	
	/***
	 * Creatures lose a "metabolizeRate" amount of foodValue each turn.
	 */
	public void useCalories() {
		foodValue = foodValue - metabolizeRate;
	}
	
	/***
	 * Creatures lose foodUsed amount of foodValue.
	 */
	public void useCalories(double foodUsed) {
		foodValue -= foodUsed;
	}

	/***
	 * If a Lifeform eats another Lifeform, it gets the foodValue from it.
	 * @param calories
	 */
	public void eat(double calories) {
		foodValue += calories;
	}
	
	/***
	 * Accessor for the current food level of the Lifeform.
	 * @return food value.
	 */
	public double getFoodValue() {
		return foodValue;
	}

	/***
	 * Abstract method overridden by actual creatures. Determine whether it can eat something.
	 * @param prey
	 * @return
	 */
	public abstract boolean canEat(Lifeform prey);
	
	/***
	 * Abstract method overridden by actual creatures. Makes a child of the derived class type.
	 * @return
	 */
	public abstract Lifeform makeChild();
	
	/***
	 * Some creatures don't hunt, so return that status for efficiency
	 * @return
	 */
	public abstract boolean isHunter();
	
	/***
	 * Check to see if a Lifeform is near enough to eat another one. Arbitrary cutoff distance.
	 * @param other
	 * @return 
	 */
	public boolean isNear(Lifeform other, double cutOff) {
		return location.distance(other.location) < cutOff;
	}
	
	/***
	 * Draws the Lifeform image at its location. Called by an overridden paint method.
	 * @param g2d
	 */
	public void draw(Graphics2D g2d) {
//		System.out.println("draw at " + location.x + " " + location.y);
		g2d.drawImage(sprite.get(0), (int)location.x, (int)location.y, null);
	}
		
}
