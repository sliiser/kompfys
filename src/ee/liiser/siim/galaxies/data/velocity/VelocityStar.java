package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Star;

/**
 * A class for a star for the velocity Verlet' method
 *
 */
public class VelocityStar extends VelocityObject implements Star {

	public VelocityStar(VelocityCore core, float distance, Vector3f normal) {
		this(core, distance, Math.random() * Math.PI * 2, normal);
	}

	private VelocityStar(VelocityCore core, float distance, double angle,
			Vector3f normal) {

		normal.normalize();
		Vector3f v = new Vector3f(1, 0, 0);
		if (v.equals(normal)) {
			v = new Vector3f(0, 1, 0);
		}

		Vector3f v1 = new Vector3f();
		v1.cross(v, normal);
		v1.normalize();
		Vector3f v2 = new Vector3f();
		v2.cross(v1, normal);
		v2.normalize();
		Matrix3f a = new Matrix3f();
		a.setColumn(0, v1);
		a.setColumn(1, v2);
		a.setColumn(2, normal);

		float x = (float) (distance * Math.cos(angle));
		float y = (float) (distance * Math.sin(angle));
		Vector3f relPos = new Vector3f(x, y, 0);
		a.transform(relPos);

		this.position = new Vector3f(relPos);
		this.position.add(core.getPosition());

		this.velocity = new Vector3f(y,-x,0);
		a.transform(velocity);

		this.velocity.scale((float) (1 / (distance * Math.sqrt(distance))));
		this.velocity.add(core.getVelocity());

	}

	@Override
	public float getSize() {
		return STAR_SIZE;
	}

}
