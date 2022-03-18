package a12;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

/***
 * A Herbivore can eat plants. 
 * @author dejohnso
 *
 */
public class Herbivore extends Lifeform {

	/***
	 * Make a herbivore at an x,y pixel location
	 * @param _x
	 * @param _y
	 */
	public Herbivore(double _x, double _y) {
//		public Lifeform(double x, double y, double _foodValue, double _lifeSpan, 
//				double _reproductionRate, double _metabolizeRate, double _moveSpeed) {
		super(_x, _y, 50.0, 300.0, 0.011, 2.0, 5.0);
		// TODO Auto-generated constructor stub
		super.loadSprite("images/player2.png");
	}
	
	/***
	 * Make a child of Herbivore type
	 */
	@Override
	public Lifeform makeChild() {
		Herbivore h =  new Herbivore(this.getLocationX(), this.getLocationY());
		return h;
	}

	/***
	 * A Herbivore can eat a Plant prey.
	 */
	@Override
	public boolean canEat(Lifeform prey) {
		if (prey instanceof Plant)
			return true;
		// TODO Auto-generated method stub
		return false;
	}
	
	/***
	 * A Herbivore reproduces if it has enough energy and 
	 * has luck.
	 */
	@Override
	public void tryToReproduce(World world) {
		Random rng = new Random();
		if (this.getFoodValue() > 80 && rng.nextDouble() < this.getReproductionRate()) {
			Lifeform l = makeChild();
			world.addToPopulation(l);
			l.useCalories(40);
		}
	}
	
	/***
	 * Some creatures don't hunt, so return that status for efficiency
	 * @return
	 */
	public boolean isHunter() {
		return true;
	}
}
