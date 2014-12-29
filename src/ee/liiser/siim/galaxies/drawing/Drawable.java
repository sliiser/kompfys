package ee.liiser.siim.galaxies.drawing;

import javax.vecmath.Vector3f;

/**
 * Interface that wraps all drawable points: All cores and stars
 */
public interface Drawable {
	
	/**
	 * 
	 * @return the position of the object in physical space
	 */
	public Vector3f getPosition();
	
	/**
	 * Sets the position of the object
	 * @param position of the object
	 */
	public void setPosition(Vector3f position);

	/**
	 * @return Visible size of the object
	 */
	public float getSize();
}
