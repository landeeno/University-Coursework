package assignment09;

/**
 * This class loads a Graph from a .txt maze file and solves it, creating a new
 * .txt file with the solution marked.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date March 29, 2017
 */
public class PathFinder
{
	/**
	 * Loads a maze file from the given file name, solves it, and saves the
	 * solution to a new file with the given output name.
	 * 
	 * If the maze file is of an invalid format this method will throw a
	 * RuntimeException.
	 * 
	 * This method uses breadth first graph pathfinding to find the shortest
	 * path possible as the solution.
	 * 
	 * @param inputFileName
	 * @param outputFileName
	 */
	public static void solveMaze(String inputFileName, String outputFileName)
	{
		Graph testGraph = new Graph(inputFileName);
		testGraph.findShortestPath();
		testGraph.writeToFile(outputFileName);
	}
}
