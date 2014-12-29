package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Core;

public class BasicCore extends BasicObject implements Core {
	public BasicCore() {
		this(new Vector3f(), new Vector3f());
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
