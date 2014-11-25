package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Core;

public class BasicCore extends BasicObject implements Core{
	public BasicCore() {
		this(0,0,0);
	}

	public BasicCore(int x, int y, int z) {
		this(new Vector3f(x,y,z));
	}

	public BasicCore(Vector3f position) {
		this(position, (Vector3f) position.clone());
	}

	public BasicCore(Vector3f position, Vector3f oldPosition) {
		this.position = position;
		this.oldPosition = oldPosition;
	}

	@Override
	public float getSize() {
		return CORE_SIZE;
	}

	public float getMass() {
		return mass;
	}

	public Vector3f getOldPosition() {
		return oldPosition;
	}
}
