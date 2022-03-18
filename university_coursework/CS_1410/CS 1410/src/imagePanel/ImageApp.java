package imagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;

/**
 * Creates an animated image viewer with simple controls
 * @author David Johnson
 */
public class ImageApp extends JFrame implements ActionListener { 
	
	// Menu item for closing the GUI
	private JMenuItem closeItem;
	
	// Menu items for selecting a color
	private JMenuItem pauseItem;
	private JMenuItem restartItem;
	private JMenuItem stepItem;
	
	private ImagePanel panel;

	// Cause a repaint
	private Timer timer;

	public ImageApp(BufferedImage img) {
		super("Show an Image");
  		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  		// Use our new custom image panel
		panel = new ImagePanel(img);
		// put the image panel in the content area of the JFrame
		setContentPane(panel);
		// Add a timer which triggers an event for the listener
		timer = new Timer(100, this);
        timer.start();  
		
		// Add a File menu with a Close option.
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		closeItem = new JMenuItem("Close");
		closeItem.addActionListener(this);
		fileMenu.add(closeItem);
		menubar.add(fileMenu);
		
		// Add an animation menu with control options.
		JMenu animationMenu = new JMenu("Animation");
		pauseItem = new JMenuItem("Pause");
		pauseItem.addActionListener(this);
		animationMenu.add(pauseItem);
		restartItem = new JMenuItem("Restart");
		restartItem.addActionListener(this);
		animationMenu.add(restartItem);

		stepItem = new JMenuItem("Step");
		stepItem.addActionListener(this);
		animationMenu.add(stepItem);

		menubar.add(animationMenu);
		setJMenuBar(menubar);

		// Use a tool tip to explain the purpose of the animation menu.
		animationMenu.setToolTipText("Control the animation.");

		// Setup this frame.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	// Add another image to animate
	public void add(BufferedImage img) {
		panel.add(img);
	}
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		try {
			BufferedImage image = ImageIO.read(new File("me.png"));
			BufferedImage image2 = ImageIO.read(new File("me2.png"));
			ImageApp si = new ImageApp(image);
			si.add(image2);
			si.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * The method is called when the user makes a menu selection.
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == closeItem)  {
			dispose();   
			System.exit(0);
		}
		else {
			if(src == pauseItem) 
				timer.stop();
			else if(src == restartItem)	
				timer.restart();
			else // paint on step or timer	
				repaint();
		}
		
	}	
}