package ee.liiser.siim.galaxies.data;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.drawing.Drawable;

public class Core extends Drawable {

	private static final float CORE_SIZE = 0.3f;
	private float mass = 1;

	public Core() {
		this(0,0,0);
	}

	public Core(int x, int y, int z) {
		this(new Vector3f(x,y,z));
	}

	public Core(Vector3f position) {
		this(position, position);
	}

	public Core(Vector3f position, Vector3f oldPosition) {
		this.position = position;
		this.oldPosition = oldPosition;
		this.setVelocity(new Vector3f());
		this.getVelocity().sub(position, oldPosition);
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
