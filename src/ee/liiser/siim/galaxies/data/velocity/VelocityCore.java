package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.data.Core;

/**
 * A class for the galaxy core for the velocity Verlet' method
 *
 */
public class VelocityCore extends VelocityObject implements Core{

	public VelocityCore() {
		this(new Vector3d(), new Vector3d());
	}

	public VelocityCore(Vector3d position, Vector3d velocity) {
		this.position = position;
		this.velocity = velocity;
	}

	@Override
	public double getSize() {
		return CORE_SIZE;
	}

	public double getMass() {
		return MASS;
	}

}
