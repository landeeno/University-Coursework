package cards;

public class My_Best_Random_Generator implements Random_Generator{
	

	private long seed = 0;
	private long increment, multiplier;
	private static final long mask = (1L << 48) - 1;
	
	
	@Override
	public int next_int(int max) {
		seed = (multiplier*seed + increment) & mask;
		return (int) (seed % max);
	}

	@Override
	public void set_seed(long seed) {
		this.seed = seed;
	}

	@Override
	public void set_constants(long _multiplier, long _increment) {
		multiplier = _multiplier;
		increment = _increment;
	}

	

}
