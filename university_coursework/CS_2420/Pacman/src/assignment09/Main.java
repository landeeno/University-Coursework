package assignment09;

/**
 * This classis used to test the analysis hypothesis under the worst case scenario for
 * our pathfinder.
 * 
 * The worst case considered is an empty maze where the start and goal are on opposite corners.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date March 29, 2017
 */
public class Main
{

	/**
	 * Runs the basic analysis.
	 * @param args
	 */
	public static void main(String[] args)
	{
		Graph testGraph = new Graph("Resources/28.txt");
		testGraph.findShortestPath();
		int totalSearches = 0;
		for (Node node : testGraph.vertices)
		{
			totalSearches += node.timesLookedAt;
		}
		double averageTotalSearches = (double) totalSearches / testGraph.vertices.size();
		System.out.println("Average number of searches done per node in an empty (0 walls) maze of 28x28."); 
		System.out.println(averageTotalSearches);

	}

}
