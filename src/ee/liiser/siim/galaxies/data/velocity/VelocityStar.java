package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.data.Star;

/**
 * A class for a star for the velocity Verlet' method
 *
 */
public class VelocityStar extends VelocityObject implements Star {

	public VelocityStar(VelocityCore core, double distance, Vector3d normal) {
		this(core, distance, Math.random() * Math.PI * 2, normal);
	}

	private VelocityStar(VelocityCore core, double distance, double angle,
			Vector3d normal) {

		normal.normalize();
		Vector3d v = new Vector3d(1, 0, 0);
		if (v.equals(normal)) {
			v = new Vector3d(0, 1, 0);
		}

		Vector3d v1 = new Vector3d();
		v1.cross(v, normal);
		v1.normalize();
		Vector3d v2 = new Vector3d();
		v2.cross(v1, normal);
		v2.normalize();
		Matrix3d a = new Matrix3d();
		a.setColumn(0, v1);
		a.setColumn(1, v2);
		a.setColumn(2, normal);

		double x = distance * Math.cos(angle);
		double y = distance * Math.sin(angle);
		Vector3d relPos = new Vector3d(x, y, 0);
		a.transform(relPos);

		this.position = new Vector3d(relPos);
		this.position.add(core.getPosition());

		this.velocity = new Vector3d(y,-x,0);
		a.transform(velocity);

		this.velocity.scale(1 / (distance * Math.sqrt(distance)));
		this.velocity.scale(Math.random()/2 + 0.75);
		this.velocity.add(core.getVelocity());

	}

	@Override
	public double getSize() {
		return STAR_SIZE;
	}

}
