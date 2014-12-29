package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Core;

/**
 * A class for the galaxy core for the basic Verlet' method
 *
 */
public class BasicCore extends BasicObject implements Core {
	public BasicCore() {
		this(new Vector3f(), new Vector3f());
	}

	public BasicCore(Vector3f position, Vector3f oldPosition) {
		this.position = position;
		this.oldPosition = oldPosition;
	}

	@Override
	public float getSize() {
		return CORE_SIZE;
	}

	@Override
	public float getMass() {
		return MASS;
	}
}
