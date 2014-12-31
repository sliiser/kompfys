package ee.liiser.siim.galaxies.data;

import javax.vecmath.Vector3d;

/**
 * The base class for all cores and stars of all methods
 *
 */
public abstract class BaseObject {
	
	protected Vector3d position;
	
	public Vector3d getPosition() {
		return position;
	}

	public void setPosition(Vector3d position) {
		this.position = position;
	}

}
