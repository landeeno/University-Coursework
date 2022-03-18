package a12;

import java.awt.image.BufferedImage;

/***
 * A Terrain object holds an image it can draw on itself. It also
 * holds some properties that control how Lifeforms on it behave.
 * 
 * @author dejohnso
 *
 */
public class Terrain extends ImagePanel {
	
	private static final long serialVersionUID = 1L;
	private double waterSupply;
	// Currently only speedModifier is used
	private double speedModifier;
	private double dangerModifier;
	
	public Terrain(BufferedImage img, double _water, double _speed, double _danger) {
		super(img);
		waterSupply = _water;
		speedModifier = _speed;
		dangerModifier = _danger;
	}
	
	public double getWater() {
		return waterSupply;
	}
	
	public double getSpeedModifier() {
		return speedModifier;
	}
	
	public double getDangerModifier() {
		return dangerModifier;
	}

}
