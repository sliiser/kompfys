package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.data.Star;

public class BasicStar extends BasicObject implements Star {

	public BasicStar(float x, float y, float z, float xOld, float yOld,
			float zOld) {
		this(new Vector3f(x, y, z), new Vector3f(xOld, yOld, zOld));
	}

	public BasicStar(Vector3f position, Vector3f oldPosition) {
		this.position = position;
		this.oldPosition = oldPosition;
	}

	public BasicStar(BasicCore core, float distance) {
		this(distance, 0, 0, distance, (float) (Calculator.dt / Math
				.sqrt(distance)), 0);
	}

	public BasicStar(BasicCore core, float distance, double d) {
		float x = (float) (distance * Math.cos(d));
		float y = (float) (distance * Math.sin(d));
		double theta = Calculator.dt / (distance * Math.sqrt(distance));
		this.position = new Vector3f(core.getPosition().x + x,
				core.getPosition().y + y, 0);
		this.oldPosition = new Vector3f(core.getOldPosition().x
				- (float) (x * Math.cos(theta) + y * Math.sin(theta)),
				core.getOldPosition().y
						- (float) (y * Math.cos(theta) - x * Math.sin(theta)),
				0);
	}

	@Override
	public float getSize() {
		return STAR_SIZE;
	}
}
