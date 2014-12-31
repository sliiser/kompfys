package ee.liiser.siim.galaxies.data.basic;

import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.data.BaseObject;

/**
 * Base object for all basic verlet' objects
 */
public abstract class BasicObject extends BaseObject{
	
	protected Vector3d oldPosition;
	
	public void updatePosition(Vector3d position){
		oldPosition = this.position;
		this.position = position;
	}

	public Vector3d getOldPosition() {
		return oldPosition;
	}

}
