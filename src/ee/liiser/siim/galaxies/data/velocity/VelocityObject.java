package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.BaseObject;

public abstract class VelocityObject extends BaseObject {
	
	protected Vector3f velocity;
	
	public Vector3f getVelocity(){
		return velocity;
	}
	
	public void setVelocity(Vector3f velocity){
		this.velocity = velocity;
	}
	
}
