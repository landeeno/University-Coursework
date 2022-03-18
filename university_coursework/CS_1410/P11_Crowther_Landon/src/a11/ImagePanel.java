package a11;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

/** 
 * A JPanel for displaying a BufferedImage.
 *
 * @author David Johnson
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<BufferedImage> images;
	private int frameNumber;
	/***
	 * Construct a panel with a starter images
	 * @param 
	 */
	public ImagePanel(BufferedImage img) {
		images = new ArrayList<BufferedImage>();
		images.add(img);		
		frameNumber = 0;
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}
	
	/***
	 * Add img to the ArrayList of images
	 * @param img
	 */
	public void add(BufferedImage img) {
		images.add(img);
	}
	
	/***
	 * Cycle through the frames using the % mod operation
	 */
	public void nextFrame() {
		frameNumber = (frameNumber + 1)%images.size();		
	}

	/***
	 * Implement the paint method to draw the images
	 */
	@Override
	  public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(images.get(frameNumber),  0,  0, null);

	}

}
