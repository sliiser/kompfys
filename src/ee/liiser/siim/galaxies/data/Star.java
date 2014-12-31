package ee.liiser.siim.galaxies.data;

import ee.liiser.siim.galaxies.drawing.Drawable;

/**
 * Stars of any method should implement this interface
 */
public interface Star extends Drawable{
	
	/**
	 * Visual size of the stars.
	 */
	public static final double STAR_SIZE = 0.02f;
}
