package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Matrix3f;
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
		this(core, distance, Math.random()*2*Math.PI);
	}
	public BasicStar(BasicCore core, float distance, Vector3f normal) {
		this(core, distance, Math.random()*2*Math.PI, normal);
	}

	public BasicStar(BasicCore core, float distance, double angle) {
		float x = (float) (distance * Math.cos(angle));
		float y = (float) (distance * Math.sin(angle));
		this.position = new Vector3f(x,y,0);
		this.position.add(core.getPosition());
		
		this.oldPosition = new Vector3f(x,y,0);
		this.oldPosition.add(core.getOldPosition());
		Vector3f velocity = new Vector3f(y,-x,0);
		velocity.scale((float) (Calculator.dt/(Math.sqrt(distance)*distance)));
		this.oldPosition.sub(velocity);
	}

	public BasicStar(BasicCore core, float distance, double angle, Vector3f normal) {
		
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

		this.oldPosition = new Vector3f(relPos);
		this.oldPosition.add(core.getOldPosition());
		Vector3f velocity = new Vector3f(y,-x,0);
		a.transform(velocity);
		velocity.scale((float) (Calculator.dt/(Math.sqrt(distance)*distance)));
		this.oldPosition.sub(velocity);
	}

	@Override
	public float getSize() {
		return STAR_SIZE;
	}
}
