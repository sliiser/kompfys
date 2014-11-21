package ee.liiser.siim.galaxies.data;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.drawing.Drawable;

public class Star extends Drawable {

	private static final float STAR_SIZE = 0.1f;

	public Star(float x, float y, float z, float xOld, float yOld, float zOld) {
		this(new Vector3f(x, y, z), new Vector3f(xOld, yOld, zOld));
	}

	public Star(Vector3f position, Vector3f oldPosition) {
		this.position = position;
		this.oldPosition = oldPosition;
	}

	public Star(Core core, float distance) {
		this(distance, 0, 0, distance, (float) (Calculator.dt / Math.sqrt(distance)), 0);
	}

	public Star(Core core, float distance, double d) {
		float x = (float) (distance * Math.cos(d));
		float y = (float) (distance * Math.sin(d));
		double theta = Calculator.dt / (distance * Math.sqrt(distance));
		this.position = new Vector3f(core.getPosition().x + x, core.getPosition().y + y, 0);
		this.oldPosition = new Vector3f(core.getPosition().x + (float) (x * Math.cos(theta) + y
				* Math.sin(theta)), core.getPosition().y + (float) (y * Math.cos(theta) - x
				* Math.sin(theta)), 0);
		this.setVelocity(new Vector3f());
		this.getVelocity().sub(position, oldPosition);
		this.getVelocity().scale(1/Calculator.dt);
	}
	
	@Override
	public float getSize() {
		return STAR_SIZE;
	}
}
