package a12;

import java.util.Random;

public class Carnivore extends Lifeform {

	public Carnivore(double _x, double _y) {
		super(_x, _y, 80, 550, .0075, 0.5, 9);
		//double _foodValue = 80;
		//double _lifeSpan = 500;
		//double _reproductionRate = .006;
		//double _metabolizeRate = 4;
		//double _moveSpeed = 8
		super.loadSprite("images/carnivore.png");
		
	}

	/**
	 * Carnivore requirements to reproduce. FoodValue needs to be relatively high
	 * (aka predator healthy enough to reproduce), and takes 150 calories
	 */
	@Override
	public void tryToReproduce(World world) {
		Random rng = new Random();
		if (this.getFoodValue() > 110 && rng.nextDouble() < this.getReproductionRate()) {
			Lifeform l = makeChild();
			world.addToPopulation(l);
			l.useCalories(150);
		}
	}

	/**
	 * Carnivores can eat herbivores
	 */
	@Override
	public boolean canEat(Lifeform prey) {
		if (prey instanceof Herbivore)
			return true;
		return false;
	}

	/**
	 * Makes new carnivore if makeChild() method is called
	 */
	@Override
	public Lifeform makeChild() {
		Carnivore c = new Carnivore(this.getLocationX(), this.getLocationY());
		return c;
	}

	/**
	 * True - carnivore eats herbivore
	 */
	@Override
	public boolean isHunter() {
		return true;
	}
	
	

}
