package a12;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/***
 * A Plant is the lowest level of the ecosystem. It has a negative metabolize, so that it
 * automatically gains food over time (presumably through photosynthesis). It has density caps 
 * on its growth.
 * 
 * @author dejohnso
 *
 */
public class Plant extends Lifeform {

	/***
	 * Make a Plant at an x,y pixel location
	 * @param _x
	 * @param _y
	 */
	public Plant(double _x, double _y) {
//		public Lifeform(double x, double y, double _foodValue, double _lifeSpan, 
//		double _reproductionRate, double _metabolizeRate, double _moveSpeed) {

		super(_x, _y, 20.0, 300.0, 0.02, -0.5, 0.2);
		// TODO Auto-generated constructor stub
		super.loadSprite("images/flower.png");
	}
	
	/***
	 * Make a child of Plant type
	 */
	@Override
	public Lifeform makeChild() {
		Plant h =  new Plant(this.getLocationX(), this.getLocationY());
		return h;
	}
	
	/***
	 * A plant cannot eat any other Lifeforms, so it is always false
	 */
	@Override
	public boolean canEat(Lifeform prey) {
		// TODO Auto-generated method stub
		return false;
	}

	/***
	 * A plant reproduces if it has enough energy, it is not near other plants and 
	 * has luck.
	 */
	@Override
	public void tryToReproduce(World world) {
		Random rng = new Random();
		if (this.getFoodValue() > 40 && rng.nextDouble() < this.getReproductionRate()) {
			// Try to avoid hyper-dense plant growth
			boolean nearPlant = false;
			for (int j = 0; j < world.getPopulationSize(); j++) {
				Lifeform other = world.getLifeformAtIndex(j);
				if (other != this && other instanceof Plant)
					if (isNear(other, 10.0))
						nearPlant = true;
			}
			if (!nearPlant) {				
				Lifeform l = makeChild();
				world.addToPopulation(l);
				this.useCalories(20);
			}
		}
		
	}
	
	/***
	 * Some creatures don't hunt, so return that status for efficiency
	 * @return
	 */
	public boolean isHunter() {
		return false;
	}


}
