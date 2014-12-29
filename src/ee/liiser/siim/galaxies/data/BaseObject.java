package ee.liiser.siim.galaxies.data;

import javax.vecmath.Vector3f;

/**
 * The base class for all cores and stars of all methods
 *
 */
public abstract class BaseObject {
	
	protected Vector3f position;
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

}
