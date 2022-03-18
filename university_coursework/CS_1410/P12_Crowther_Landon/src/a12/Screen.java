package a12;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JSlider;

/***
 * A Screen holds the terrain tiles for display
 * @author dejohnso
 *
 */
public class Screen extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private World world;
	

	
	public Screen(World shareWorld) {
		super();
		setLayout(new BorderLayout());
		world = shareWorld;
		setLayout(new GridLayout(world.getTerrainRows(), world.getTerrainCols()));
		for (int row = 0; row < world.getTerrainRows(); row++)
			for (int col = 0; col < world.getTerrainCols(); col++) {
				add(world.getTerrain(row, col));
			}
		
		
		
	}
	
	

	/***
	 * We override the paint method to first display the tiles
	 * and then add the Lifeforms on top.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g.create();		
		for (int i = 0; i < world.getPopulationSize(); i++)
			world.getLifeformAtIndex(i).draw(g2d);
		g2d.dispose();
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
