package assignment09;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class represents a graph of vertices and edges.
 * It represents the vertices using Nodes, and it initially builds its
 * Nodes up from a given file.
 * 
 * This class contains methods to find the shortest path between two given Nodes once the graph
 * has been built.
 * 
 * @author Nathan Robbins, Landon Crowther
 * @date March 29, 2017
 *
 */
public class Graph
{
	//Protected are for testing purposes
	protected int[][] gameGrid; //2d representation ofthe text file
	private int id = 0; //The next ID to assign to a Node.
	protected int startID;
	protected int goalID;
	protected ArrayList<Node> vertices;
	private int width;
	private int height;

	/**
	 * Builds a graph from a file with the given filename.
	 * 
	 * The format of the file must be exact:
	 * 'X' represents a maze wall
	 * 'S' represents the starting space
	 * 'G' represents the goal
	 * ' ' represents an empty space in the maze.
	 * 
	 * The outer boundary of the maze must be all X's.
	 * The first line of the file must contain the height and width separated by a space.
	 * Example:
	 * 5 4
	 * XXXX
	 * X SX
	 * XG X
	 * X  X
	 * XXXX
	 * 
	 * @param filename
	 */
	public Graph(String filename)
	{
		vertices = new ArrayList<Node>();
		initializeGrid(filename);

		for (Node node : vertices)
		{
			updateNeighbors(node);
		}

	}

	/**
	 * This method is used by the constructor to initialize the gameGrid from the textfile.
	 * It will throw a RuntimeException error if the given file is not constructed according 
	 * to the rules outlined in the Graph constructor.
	 * @param filename
	 */
	private void initializeGrid(String filename)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

			String[] dimensions = reader.readLine().split(" ");
			try{
			height = Integer.parseInt(dimensions[0]);
			width = Integer.parseInt(dimensions[1]);
			}
			catch(Exception e)
			{
				reader.close();
				throw new RuntimeException("Illegal File Format");
			}
			gameGrid = new int[width][height];

			for (int row = 0; row < height; row++)
			{
				String currentLine = reader.readLine();
				for (int col = 0; col < width; col++)
				{
					char currentChar = currentLine.charAt(col);

					switch (currentChar)
					{
					case 'X':
						gameGrid[col][row] = -1;
						break;
					case ' ':
						gameGrid[col][row] = id;
						vertices.add(new Node(id, col, row));
						id++;
						break;
					case 'S':
						gameGrid[col][row] = id;
						startID = id;
						vertices.add(new Node(id, col, row));
						id++;
						break;
					case 'G':
						gameGrid[col][row] = id;
						goalID = id;
						vertices.add(new Node(id, col, row));
						id++;
						break;
					default:
						throw new RuntimeException("Illegal File Format");

					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File not found.");
			e.printStackTrace();
			System.exit(0);
		} catch (IOException e)
		{
			System.out.println("ERROR: Error occurred while reading the file.");
			e.printStackTrace();
			System.exit(0);
		}
		
		if(!boundariesAreCorrect())
		{
			throw new RuntimeException("Illegal File Format");
		}
	}

	private boolean boundariesAreCorrect()
	{
		for(int row = 0; row < height; row++)
		{
			if(gameGrid[0][row] != -1 || gameGrid[width-1][row] != -1)
			{
				return false;
			}
		}
		
		for(int col = 0; col < width; col++)
		{
			if(gameGrid[col][0] != -1 || gameGrid[col][height-1] != -1)
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Checks if there is a Node to the north of 'node' and adds 
	 * it as a neighbor if it exists.
	 * @param node
	 */
	private void checkNorth(Node node)
	{
		int row = node.getY();
		int col = node.getX();

		int northID = gameGrid[col][row - 1];
		if (northID >= 0)
		{
			makeEdge(node, vertices.get(northID));
		}

	}

	/**
	 * Checks if there is a Node to the south of 'node' and adds 
	 * it as a neighbor if it exists.
	 * @param node
	 */
	private void checkSouth(Node node)
	{
		int row = node.getY();
		int col = node.getX();

		int southID = gameGrid[col][row + 1];
		if (southID >= 0)
		{
			makeEdge(node, vertices.get(southID));
		}
	}

	/**
	 * Checks if there is a Node to the east of 'node' and adds 
	 * it as a neighbor if it exists.
	 * @param node
	 */
	private void checkEast(Node node)
	{
		int row = node.getY();
		int col = node.getX();

		int eastID = gameGrid[col + 1][row];
		if (eastID >= 0)
		{
			makeEdge(node, vertices.get(eastID));
		}

	}

	/**
	 * Checks if there is a Node to the west of 'node' and adds 
	 * it as a neighbor if it exists.
	 * @param node
	 */
	private void checkWest(Node node)
	{
		int row = node.getY();
		int col = node.getX();

		int westID = gameGrid[col - 1][row];
		if (westID >= 0)
		{
			makeEdge(node, vertices.get(westID));
		}
	}

	/**
	 * Checks for neighbors around 'node' and adds them 
	 * to 'node's adjacency list if they exist.
	 * @param node
	 */
	private void updateNeighbors(Node node)
	{
		checkNorth(node);
		checkEast(node);
		checkSouth(node);
		checkWest(node);
	}

	/**
	 * Make a connection between vertex1 and vertex2.
	 * 
	 * @param vertex1
	 * @param vertex2
	 */
	public void makeEdge(Node vertex1, Node vertex2)
	{
		vertex1.connectTo(vertex2);
	}

	/**
	 * Tries to find the shortest path between the start and the goal of this graph.
	 * Returns false if no such path exist, and true if one is found.
	 * 
	 * After running this the gameGrid can be printed to a file with 
	 * the shortest path marked with '.' chars.
	 * 
	 * @return true if path is found, false otherwise
	 */
	public boolean findShortestPath()
	{
		BreadthFirstSearch.getShortestPath(vertices.get(startID), goalID);

		if (vertices.get(goalID).getBack() == null)
		{
			return false;
		}

		Node current = vertices.get(goalID).getBack();
		while (current.getBack() != null)
		{
			int x = current.getX();
			int y = current.getY();

			gameGrid[x][y] = -5; //-5 chosen arbitrarily to represent the shortest path in the int gameGrid.

			current = current.getBack();
		}

		return true;
	}
	
	/**
	 * Writes the current state of the gameGrid to a file with the given fileName.
	 * 
	 * If the shortest path search has been run, the result will have the shortest path
	 * marked with '.' chars. Otherwise it will print out the maze as is.
	 * 
	 * @param fileName
	 */
	public void writeToFile(String fileName) 
	{
		
		try(PrintWriter output = new PrintWriter(new FileWriter(fileName)))
        {
             output.print(height + " " + width);
             for (int row = 0; row < height; row++)
             {
            	 output.println("");
            	 for (int col = 0; col < width; col++)
            	 {
            		 int currentInt = gameGrid[col][row];

            		 if(currentInt == startID)
            		 {
            			 output.print('S');
            			 continue;
            		 }
            		 
            		 else if (currentInt == goalID)
            		 {
            			 output.print('G');
            			 continue;
            		 }
            		 
            		 
 					switch (currentInt)
 					{
 					case -1:
 						output.print('X');
 						break;
 					case -5:
 						output.print('.');
 						break;
 					default:
 						output.print(' ');
 						break;
 					}
            	 }
             }
             output.println();
             output.close();
        } catch (IOException e)
		{
        	System.out.println("ERROR: Error occurred while writing the file.");
			e.printStackTrace();
			System.exit(0);
		}
		
	}

}
