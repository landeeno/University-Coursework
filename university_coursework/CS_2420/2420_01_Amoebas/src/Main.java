/**
 * Landon Crowther
 * u0926601
 * 1/19/2017
 * CS - 2420
 * Assignment 1
 * 
 * I pledge that the work done here was my own and that 
 * I have learned how to write this program, such that I 
 * could throw it out and restart and finish in a timely 
 * manner. I am not turning in any work that I cannot 
 * understand, describe, or recreate.
 * 	-- Landon Crowther
 * 
 */

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
  * This class does a few things: 
 * 
 * 1) sets up JFrame and JPanel for GUI to work
 * 2) Creates an ArrayList (drawables) that each JComponent is added to
 * 3) Constructs circles and add them to both drawables and main JPanel
 * 4) Starts Jiggler
 * 
 * note - jiggler class does most of the "heavy lifting." This class just sets everything
 * up and initializes
 *  
 * 
 * 
 * @author Landon Crowther
 *
 */
public class Main {

	
	/**
	 * instance variables. I have them declared here to make 
	 * simple changes easy, rather than having to dig through
	 * a lot of code. 
	 */
	protected static int panelWidth = 1000;
	protected static int panelHeight = 1000;
	private static int numberOfCircles = 100;
	
	
	public static void main(String[] args) 	{

		//Set up GUI
		JFrame mainWindow = new JFrame("Play");
		JPanel mainPanel = new JPanel();		
		mainWindow.setContentPane(mainPanel);
		mainWindow.setSize(panelWidth, panelHeight);	
		mainPanel.setLayout(null);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ArrayList that will keep track of all circles
		ArrayList<JComponent> drawables = new ArrayList<>();
		
		//Adding all the circles to the drawables ArrayList
		for (int circleCount = 0; circleCount < numberOfCircles; circleCount++) {			
			Circle c = new Circle( (int)(Math.random() * mainWindow.getWidth()),
					(int)(Math.random() * mainWindow.getHeight()),
					(int)(Math.random() * 100),	(int)(Math.random() * 100) );
			drawables.add(c);		
		}
		
		//Setting & adding the JPanel which will contain the FPS label:
		JPanel fpsPanel = new JPanel();
		JLabel fps = new JLabel("FPS: "); 
		fpsPanel.add(fps);
		fpsPanel.setSize(panelWidth, 40);
		mainPanel.add(fpsPanel);
		
		//Set up Jiggler with previous elements		
		Jiggler jiggler = new Jiggler(fps, drawables);
		
		//add each component of the jiggler to the mainPanel
		for ( JComponent c : jiggler.drawables ) {
			mainPanel.add(c);
		}
		
		//start the simulation
		jiggler.start();
				
		//ensure visibility
		mainWindow.setVisible(true);
				
	}
	
//end of class	
}
