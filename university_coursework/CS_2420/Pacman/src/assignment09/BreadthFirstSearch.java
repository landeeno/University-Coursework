package assignment09;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class contains the capability to run a breadth first search for a goal
 * on a graph of Nodes.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date March 29, 2017
 *
 */
public class BreadthFirstSearch
{

	/**
	 * Links the shortest path between the start Node to the node with the given
	 * goalID.
	 * 
	 * After running this, the shortest path can be found by starting from the
	 * goal Node and tracing backwards using the 'previous' field.
	 * 
	 * @param start
	 * @param goalID
	 */
	public static void getShortestPath(Node start, int goalID)
	{
		Queue<Node> queue = new ArrayDeque<Node>();
		queue.offer(start);
		start.markAsSeen();

		while (!queue.isEmpty())
		{
			Node currentNode = queue.poll();
			for (Node adjNode : currentNode.adjacent()) // Breadth first checks
														// all adjacent nodes
														// first
			{
				if (adjNode.getID() == goalID)
				{
					adjNode.setBack(currentNode);
					adjNode.markAsSeen();
					return;
				}

				if (!adjNode.alreadySeen())
				{
					queue.add(adjNode);
					adjNode.markAsSeen();
					adjNode.setBack(currentNode);
				}
			}
		}
	}

}
