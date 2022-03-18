/**
 * NOTE!!!
 * I made this class from scratch, but it behaves quite differently than 
 * the ImagePanel class provided in lecture.
 * 
 * That being said, I feel like the ImagePanel class from lecture
 * works better (I think it's closer to what we're trying to achieve)
 */



package a11;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ImagePanel2 extends JPanel {

	//magic code
	private static final long serialVersionUID = 1L;
	private ArrayList<BufferedImage> images;

	/**
	 * Constructor for Image Panel class
	 * makes a new ArrayList for each ImagePanel2 that contains 1 image, the paramter
	 * @param _image
	 */
	public ImagePanel2(BufferedImage _image) {
		images = new ArrayList<BufferedImage>();
		images.add(_image);		
	}

	/**
	 * Not sure if I actually need this method, it's never
	 * called in the GUI class....
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(images.get(0), 0, 0, null);       
	}



}



