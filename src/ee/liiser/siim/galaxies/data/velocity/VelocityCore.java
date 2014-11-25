package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.data.Core;

public class VelocityCore extends VelocityObject implements Core{

	public VelocityCore() {
		this(0,0,0);
	}

	public VelocityCore(int x, int y, int z) {
		this(new Vector3f(x,y,z));
	}

	public VelocityCore(Vector3f position) {
		this(position, new Vector3f());
	}

	public VelocityCore(Vector3f position, Vector3f velocity) {
		this.position = position;
		this.velocity = velocity;
		getVelocity().scale(1/Calculator.dt);
	}

	@Override
	public float getSize() {
		return CORE_SIZE;
	}

	public float getMass() {
		return mass;
	}

}
