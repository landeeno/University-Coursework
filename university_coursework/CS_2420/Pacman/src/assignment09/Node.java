package assignment09;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class represents a single vertex in a Graph.
 * 
 * A Node knows what other Nodes it is adjacent to, and it knows its 
 * location in the original 2d maze grid.
 * 
 * Nodes can be connected to other Nodes.
 * 
 * A Node knows the path backwards from it to the start, if it has been calculated through
 * a breadth first search.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date March 29, 2017
 */
public class Node
{
	//Protected is for testing purposes
	private Node previous;
	private boolean seen; //True if this Node has already been looked at by another Node
	protected HashSet<Node> adjacent; //HashSet was chosen since our Node cannot be doubly linked to another Node.
	private int id; //Unique id for this Node.
	private int xPos, yPos;
	//Used for testing analysis hypothesis
	protected int timesLookedAt;
	
	/**
	 * Construct a Node given a unique id and an xpos and ypos in a 2d grid.
	 * @param id
	 * @param xPos
	 * @param yPos
	 */
	public Node(int id,int xPos,int yPos)
	{
		adjacent = new HashSet<Node>();
		seen = false;
		this.id = id;
		this.xPos = xPos;
		this.yPos = yPos;
		timesLookedAt = 0;
	}
	
	/**
	 * Make a connection between this Node and another node.
	 * @param node
	 */
	public void connectTo(Node node)
	{
		this.adjacent.add(node);
		node.adjacent.add(this);
	}
	
	/**
	 * Give this Node its path back.
	 * @param source
	 */
	public void setBack(Node source)
	{
		this.previous = source;
	}
	
	/**
	 * Returns the previous Node in the path back to the starting Node.
	 * @return previous Node in path back
	 */
	public Node getBack()
	{
		return this.previous;
	}
	
	/**
	 * Marks this Node as having been looked at in a breadth first search.
	 */
	public void markAsSeen()
	{
		seen = true;
	}
	
	/**
	 * Returns the status of this Node in a breadth first search.
	 * @return true if this Node has already been looked at, false otherwise
	 */
	public boolean alreadySeen()
	{
		timesLookedAt++;
		return seen;
	}
	
	/**
	 * Returns the iterable adjacency list of this Node.
	 * This list contains all Nodes directly adjacent to this one.
	 * @return adjacency list
	 */
	public Iterable<Node> adjacent()
	{
		return adjacent;
	}
	
	/**
	 * Get the unique ID for this Node.
	 * @return id
	 */
	public int getID()
	{
		return this.id;
	}

	/**
	 * Get the y position for this Node in the original 2d grid built from a file.
	 * @return y position
	 */
	public int getY()
	{
		return this.yPos;
	}
	
	/**
	 * Get the x position for this Node in the original 2d grid built from a file.
	 * @return x position
	 */
	public int getX()
	{
		return this.xPos;
	}

}
