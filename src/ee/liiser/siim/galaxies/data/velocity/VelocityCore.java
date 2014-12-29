package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Core;

public class VelocityCore extends VelocityObject implements Core{

	public VelocityCore() {
		this(new Vector3f(0,0,0), new Vector3f());
	}

	public VelocityCore(Vector3f position, Vector3f velocity) {
		this.position = position;
		this.velocity = velocity;
	}

	@Override
	public float getSize() {
		return CORE_SIZE;
	}

	public float getMass() {
		return mass;
	}

}
