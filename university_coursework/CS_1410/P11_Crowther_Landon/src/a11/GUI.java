package a11;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * GUI Class
 * u0926601
 * CS 1410
 * Assignment A11
 * @author Landon Crowther
 *
 */
public class GUI extends JFrame implements ActionListener {

	//magic code
	private static final long serialVersionUID = 1L;
	//defining all the colors for the comparison method
	private static final Color plains = new Color(126, 206, 46);
	private static final Color desert = new Color(206, 206, 46);
	private static final Color rocky = new Color(39, 39, 21); 
	private static final Color swamp = new Color(14, 74, 14);
	private static final Color water = new Color(0, 176, 255);
	private static final Color dirt = new Color(117, 94, 21);

	//Images:
	private BufferedImage Desert; 
	private BufferedImage Rocky ;
	private BufferedImage Plains ;
	private BufferedImage Swamp ;
	private BufferedImage Dirt;
	private BufferedImage Water;

	



	//panels:
	private ArrayList<ImagePanel> images;
	private ImagePanel minimapPanel;


	/**
	 * Main method does a few things: 
	 * 
	 * 1) Attempts to load minimap image, displays error if image can't be found
	 * 2) Creates new GUI object with minimap image
	 */
	public static void main(String[] args ) {

		//declare minimap image
		BufferedImage minimap = null;

		//make sure minimap file exists - exit if image doesn't exist
		try {
			//initialize minimap image
			minimap = ImageIO.read(new File("Images/minimap.png"));
		} catch (IOException e) {
			System.out.println("Minimap Image not found.");
			e.printStackTrace();
		}

		//make new GUI object and set visible
		GUI gui = new GUI(minimap);
		gui.setVisible(true);
	}

	/**
	 * Constructor method for the class. Method does a few special things: 
	 * 
	 * 1) makes sure the minimap parameter contains no invalid colors.
	 * 		if it does, program exits and message is displayed.
	 * 2) builds an ArrayList of ImagePanel objects that will construct the majority
	 * 		layout of the program
	 * 3) Construct an accumulation of Java Swing components and displays them in a JFrame
	 * 
	 * @param minimap
	 */
	public GUI(BufferedImage minimap) {

		//check for any invalid colors
		if (checkMinimap(minimap) == false) {
			System.out.println("Invalid minimap colors");
			System.exit(0);
		}

		//Start setting up GUI:
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//main panel that contains EVERYTHING
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		//add each image (Desert.png, Rocky.png, etc) to the images array
		images = buildArray(minimap);

		//creating panel for all of the images
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(minimap.getHeight(), minimap.getWidth()));

		//adding images to gridLayout
		for (int i = 0; i < minimap.getHeight()*minimap.getWidth(); i++) {
			gridPanel.add(images.get(i));
		}

		//add gridLayout to mainPanel
		mainPanel.add(gridPanel, BorderLayout.CENTER);

		//create ImagePanel for the minimap display:
		minimapPanel = new ImagePanel(minimap);

		//add minimapPanel to mainPanel:
		mainPanel.add(minimapPanel, BorderLayout.EAST);

		//add scrollPane
		JScrollPane scrollPane = new JScrollPane(gridPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		//add scrollPane to main
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		

		//set content and pack
		setTitle("Start of EcoSim - Landon Crowther - u0926601");
		setContentPane(mainPanel);
		setPreferredSize(new Dimension(800, 800));
		pack();

	}

	/**
	 * method that checks each pixel in a m inimap.png image,
	 * and then reports true if all pixels are legal and false otherwise
	 * @param minimap.png
	 * @return - true if image works, false otherwise
	 */
	private boolean checkMinimap(BufferedImage minimap) {

		//loop that searches every pixel
		for (int y = 0; y < minimap.getHeight(); y ++) {
			for (int x = 0; x < minimap.getWidth(); x ++) {
				Color pixel = new Color(minimap.getRGB(x, y));
				if (pixel.equals(plains) || pixel.equals(desert) || pixel.equals(rocky) 
						|| pixel.equals(swamp) || pixel.equals(water) || pixel.equals(dirt)) {
					//don't do anything if pixel is equivalent to one of the colors
					//not the best programming format, but don't know how I would do !.equals() 
				}
				else
					return false;
			}
		}
		//if every pixel matches one of the colors, return true
		return true;
	}


	/**
	 * Method that builds the ArrayList of Image panels. This array list will be
	 * used to display all of the terrain tiles. 
	 * 
	 * NOTE - don't know how to load from 
	 * @param minimap - minimap image to check build terrain off of
	 * @return ArrayList of Image Panels
	 */
	public ArrayList<ImagePanel> buildArray(BufferedImage minimap) {

		//make sure all files are load-able
		try {	
			Desert = ImageIO.read(new File("Images/Desert.png"));
			Water = ImageIO.read(new File("Images/Water.png"));
			Rocky = ImageIO.read(new File("Images/Rocky.png"));
			Plains = ImageIO.read(new File("Images/Plains.png"));
			Swamp = ImageIO.read(new File("Images/Swamp.png"));
			Dirt = ImageIO.read(new File("Images/Dirt.png")); 
		} catch (IOException e) {
			System.out.println("Image(s) not found");
			e.getStackTrace();
		}

		//declare the ArrayList
		ArrayList<ImagePanel> imageList = new ArrayList<>();

		//add each respective image to the ArrayList
		for (int y = 0; y < minimap.getHeight(); y ++) {
			for (int x = 0; x < minimap.getWidth(); x ++) {
				Color pixel = new Color(minimap.getRGB(x, y));				
				if (pixel.equals(desert)) {
					ImagePanel image = new ImagePanel(Desert);
					imageList.add(image);
				}					
				else if (pixel.equals(water)) {
					ImagePanel image = new ImagePanel(Water);
					imageList.add(image);
				}
				else if(pixel.equals(rocky)) {
					ImagePanel image = new ImagePanel(Rocky);
					imageList.add(image);
				}
				else if (pixel.equals(plains)) {
					ImagePanel image = new ImagePanel(Plains);
					imageList.add(image);
				}
				else if(pixel.equals(swamp)) {
					ImagePanel image = new ImagePanel(Swamp);
					imageList.add(image);
				}
				else {
					ImagePanel image = new ImagePanel(Dirt);
					imageList.add(image);
				}
			}
		}

		//return the ArrayList
		return imageList;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}


	//end of class
}
