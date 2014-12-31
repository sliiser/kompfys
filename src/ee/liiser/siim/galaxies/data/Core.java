package ee.liiser.siim.galaxies.data;

import ee.liiser.siim.galaxies.drawing.Drawable;

/**
 * Galaxy cores of any method should implement this interface
 */
public interface Core extends Drawable{

	/**
	 * Visual size of the galaxy cores.
	 */
	public static final double CORE_SIZE = 0.05f;
	/**
	 * The default mass of galaxies. Change to modify the strength of the gravity
	 */
	public double MASS = 1;
	
	/**
	 * Returns the mass of the galaxy. {@link Core#MASS} by default.
	 * @return mass of the galaxy
	 */
	double getMass();

}
