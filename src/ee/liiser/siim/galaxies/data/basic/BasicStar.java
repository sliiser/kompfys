package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.data.Star;

/**
 * A class for a star of the basic method
 */
public class BasicStar extends BasicObject implements Star {

	public BasicStar(BasicCore core, double distance, Vector3d normal) {
		this(core, distance, Math.random()*2*Math.PI, normal);
	}

	private BasicStar(BasicCore core, double distance, double angle, Vector3d normal) {
		
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

		this.oldPosition = new Vector3d(relPos);
		this.oldPosition.add(core.getOldPosition());
		
		Vector3d acc = new Vector3d();
		// m * r / r^3
		acc.scale( - core.getMass() / Math.pow(distance, 3), relPos);
		acc.scale(Calculator.dt/2);

		Vector3d velocity = new Vector3d(y,-x,0);
		a.transform(velocity);
		velocity.add(acc);
		
		velocity.scale(Calculator.dt/(Math.sqrt(distance)*distance));
		velocity.scale(Math.random()/2 + 0.75);
		this.oldPosition.sub(velocity);
	}

	@Override
	public double getSize() {
		return STAR_SIZE;
	}
}
