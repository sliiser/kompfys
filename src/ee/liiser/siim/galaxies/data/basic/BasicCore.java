package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.data.Core;

/**
 * A class for the galaxy core for the basic Verlet' method
 *
 */
public class BasicCore extends BasicObject implements Core {
	public BasicCore() {
		this(new Vector3d(), new Vector3d());
	}

	public BasicCore(Vector3d position, Vector3d velocity) {
		this.position = position;
		this.oldPosition = new Vector3d(velocity);
		this.oldPosition.scale(-Calculator.dt);
		this.oldPosition.add(position);
	}

	@Override
	public double getSize() {
		return CORE_SIZE;
	}

	@Override
	public double getMass() {
		return MASS;
	}
}
