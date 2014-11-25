package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.BaseObject;

public abstract class BasicObject extends BaseObject{
	
	protected Vector3f oldPosition;
	
	public void updatePosition(Vector3f position){
		oldPosition = this.position;
		this.position = position;
	}

	public Vector3f getOldPosition() {
		return oldPosition;
	}

}
