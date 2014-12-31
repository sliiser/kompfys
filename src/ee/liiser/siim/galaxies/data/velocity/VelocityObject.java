package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.data.BaseObject;

/**
 * Base object for all velocity Verlet' objects
 *
 */
public abstract class VelocityObject extends BaseObject {

	protected Vector3d velocity;

	public Vector3d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3d velocity) {
		this.velocity = velocity;
	}

}
