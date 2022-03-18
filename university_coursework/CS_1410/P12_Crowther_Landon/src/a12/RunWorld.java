package a12;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/***
 * The main application class for the EcoSim. This class loads many of 
 * the resources, builds the GUI, and starts a timer to step through the 
 * simulation.
 * 
 * @author dejohnso
 *
 */
public class RunWorld extends JFrame implements ActionListener {

	// Store the pixel colors to match in the minimap.
	// Use a packed int 4 Byte representation.
	private static final long serialVersionUID = 1L;
	private static int plainsColor = 0xFF7ECE2E;
	private static int desertColor = 0xFFCECE2E;
	private static int rockyColor = 0xFF272715;
	private static int swampColor = 0xFF0E400E;
	private static int waterColor = 0xFF00B0FF;
	private static int dirtColor = 0xFF755E15;

	// References to some UI components
	private Terrain panel;
	private Timer timer;
	private ImagePanel minimapPanel;


	// The world holds all the data
	private World world;

	// sliders:
	private JSlider herbivoreSlider;
	private JSlider plantSlider;
	private JSlider carnivoreSlider;

	//labels:
	private JLabel plantLabel;
	private JLabel herbivoreLabel;
	private JLabel carnivoreLabel;

	private JButton startButton;


	// LifeForm slider values
	static final int minLifeform = 0;
	static final int maxLifeform = 5000;
	static final int initialPlant = 4000;
	static final int initialHerbivore= 100;
	static final int initialCarnivore = 20;





	/***
	 * Construct the main application. It builds the UI, creates the world with terrain and population, and 
	 * starts a timer to tick the simulation forward.
	 * @param plainsImg
	 * @param desertImg
	 * @param rockyImg
	 * @param swampImg
	 * @param waterImg
	 * @param dirtImg
	 * @param minimapImg
	 */
	public RunWorld(BufferedImage plainsImg, BufferedImage desertImg, BufferedImage rockyImg, BufferedImage swampImg,
			BufferedImage waterImg, BufferedImage dirtImg, BufferedImage minimapImg) {
		super("World Simulation");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// This is the panel that will be the JFrame ContentPane. Add things to this.
		JPanel overallPanel = new JPanel();
		overallPanel.setLayout(new BorderLayout());

		// Construct the world tiles
		Terrain[][] terrain = buildTerrain(plainsImg, desertImg, rockyImg, swampImg, waterImg, dirtImg, minimapImg);
		// Store the map in the world.
		world = new World(terrain);
		// The screen is what draws the tiles and the living things
		Screen screen = new Screen(world);

		// Add a scrollpane. The size is set to give the scrollbars a little room.
		JScrollPane scrollPanel = new JScrollPane(screen);
		int scrollBarWidth = (int) scrollPanel.getVerticalScrollBar().getPreferredSize().getWidth();
		int scrollBarHeight = (int)scrollPanel.getHorizontalScrollBar().getPreferredSize().getHeight();
		scrollPanel.setPreferredSize(new Dimension(32*25+scrollBarWidth, 32*20+scrollBarHeight));

		overallPanel.add(scrollPanel, BorderLayout.CENTER);
		minimapPanel = new ImagePanel(minimapImg);



		// RHS panel that will contain all interface components
		JPanel rhsPanel = new JPanel();
		rhsPanel.setLayout(new GridLayout(3, 1));
		rhsPanel.setPreferredSize(new Dimension(280, overallPanel.getHeight()));

		//add minimap to first part of the rhsPanel
		rhsPanel.add(minimapPanel);

		//slider panel will contain all sliders and associated labels
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(6, 1));

		herbivoreSlider = new JSlider(JSlider.HORIZONTAL, minLifeform, maxLifeform, initialHerbivore);
		plantSlider = new JSlider(JSlider.HORIZONTAL, minLifeform, maxLifeform, initialPlant);
		carnivoreSlider = new JSlider(JSlider.HORIZONTAL, minLifeform, maxLifeform, initialCarnivore);

		// Labels for sliders: 
		JLabel herbivoreSliderLabel = new JLabel("Initial herbivore Count", JLabel.CENTER);
		JLabel plantSliderLabel = new JLabel("Initial Plant Count", JLabel.CENTER);
		JLabel carnivoreSliderLabel = new JLabel("Initial Predator Count", JLabel.CENTER);

		// major and minor ticking for sliders
		herbivoreSlider.setMajorTickSpacing(1000);
		plantSlider.setMajorTickSpacing(1000);
		carnivoreSlider.setMajorTickSpacing(1000);		
		herbivoreSlider.setMinorTickSpacing(500);
		plantSlider.setMinorTickSpacing(500);
		carnivoreSlider.setMinorTickSpacing(500);
		herbivoreSlider.setPaintTicks(true);
		herbivoreSlider.setPaintLabels(true);
		plantSlider.setPaintTicks(true);
		plantSlider.setPaintLabels(true);
		carnivoreSlider.setPaintTicks(true);
		carnivoreSlider.setPaintLabels(true);

		//adding sliders and their labels
		sliderPanel.add(herbivoreSliderLabel);
		sliderPanel.add(herbivoreSlider);
		sliderPanel.add(plantSliderLabel);
		sliderPanel.add(plantSlider);
		sliderPanel.add(carnivoreSliderLabel);
		sliderPanel.add(carnivoreSlider);

		//changeListener for sliders
//		herbivoreSlider.addChangeListener(this);
//		plantSlider.addChangeListener(this);
//		carnivoreSlider.addChangeListener(this);

		//adding sliderPanel 
		rhsPanel.add(sliderPanel);

		// start button
		startButton = new JButton("Start!");
		startButton.addActionListener(this);

		//add start button
		rhsPanel.add(startButton);


		// northPanel will contain the current values of herbivors, carnivors, and plants
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(1, 3));
		northPanel.setPreferredSize(new Dimension(overallPanel.getWidth(), 50));

		plantLabel = new JLabel("Plants: " + world.numPlants());
		herbivoreLabel = new JLabel("Herbivores: " + world.numHerbivores());
		carnivoreLabel = new JLabel("Carnivores: " + world.numCarnivores());

		northPanel.add(plantLabel);
		northPanel.add(herbivoreLabel);
		northPanel.add(carnivoreLabel);

		overallPanel.add(northPanel, BorderLayout.NORTH);



		overallPanel.add(rhsPanel, BorderLayout.EAST);


		setContentPane(overallPanel);  


		// This is the main simulation timer
		timer = new Timer(20, this);


		pack();
	}

	/***
	 * Convert the minimap to tiles and store in an array.
	 * @return the 2D array of Terrain tiles
	 */
	private Terrain[][] buildTerrain(BufferedImage plainsImage, BufferedImage desertImage, BufferedImage rockyImage, BufferedImage swampImage,
			BufferedImage waterImage, BufferedImage dirtImage, BufferedImage minimapImage) {
		Terrain[][] terrain = new Terrain[minimapImage.getHeight()][minimapImage.getWidth()];
		for (int row = 0; row < minimapImage.getHeight(); row++) {
			for (int col = 0; col < minimapImage.getWidth(); col++) {
				int colorVal = minimapImage.getRGB(col,row);
				if (colorVal == plainsColor) {
					panel = new Terrain(plainsImage, 0.25, 2.0, 1.0);
				}
				else if (colorVal == desertColor) {
					panel = new Terrain(desertImage, 0.05, 1.0, 2.0);
				}
				else if (colorVal == rockyColor) {
					panel = new Terrain(rockyImage, 0.4, 0.5, 2.0);
				}
				else if (colorVal == swampColor) {
					panel = new Terrain(swampImage, 0.9, 0.3, 2.0);
				}
				else if (colorVal == waterColor) {
					panel = new Terrain(waterImage, 1.0, 0.1, 3.0);
				}
				else if (colorVal == dirtColor) {
					panel = new Terrain(dirtImage, 0.5, 4.0, 1.0);
				}
				else {
					System.out.println("no match for color " + Integer.toHexString(colorVal));
					System.exit(0);
				}
				terrain[row][col] = panel;
			}
		}
		return terrain;
	}


	/***
	 * The main simulation clock ticks here. I have the sliders set to initialize the 
	 * values of different species, and the labels are updated everytime the timer ticks.
	 */
	public void actionPerformed(ActionEvent e) {
		// Enact a step of the Lifeforms' lives.
		if (e.getSource() == startButton) {
			world.resetAll();
			world.setHerbivore(herbivoreSlider.getValue());
			world.setCarnivores(carnivoreSlider.getValue());
			world.setPlants(plantSlider.getValue());
			world.initializePopulation();
			timer.start();
		}		
		//update labels
		plantLabel.setText("Plants: " + world.numPlants());
		herbivoreLabel.setText("Herbivores: " + world.numHerbivores());
		carnivoreLabel.setText("Carnivores: " + world.numCarnivores());

		//update physical appeareance
		world.simulateAll();
		repaint();

	}

	/***
	 * The "main" main function which loads some tile resources and
	 * starts the program.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedImage plainsImg = ImageIO.read(new File("images/Plains.png"));
			BufferedImage desertImg = ImageIO.read(new File("images/Desert.png"));
			BufferedImage rockyImg = ImageIO.read(new File("images/Rocky.png"));
			BufferedImage swampImg = ImageIO.read(new File("images/Swamp.png"));
			BufferedImage waterImg = ImageIO.read(new File("images/Water.png"));
			BufferedImage dirtImg = ImageIO.read(new File("images/Dirt.png"));
			//			BufferedImage minimap = ImageIO.read(new File("images/MinimapMedium.png"));
			BufferedImage minimap = ImageIO.read(new File("images/minimap.png"));
			System.out.println(minimap.getHeight());
			RunWorld si = new RunWorld(plainsImg, desertImg, rockyImg, swampImg, waterImg, dirtImg, minimap);
			si.setVisible(true);
		} catch (IOException e) {
			System.out.println("Something went wrong...");
			e.printStackTrace();
			System.exit(0);

		}
	}



	// TODO Auto-generated method stub


}
