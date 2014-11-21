package ee.liiser.siim.galaxies.drawing;

import javax.vecmath.Vector3f;

public abstract class Drawable {
	
	protected Vector3f position;
	
	//Basic Verlet'
	protected Vector3f oldPosition;
	
	//Velocity Verlet'
	private Vector3f velocity;
	
	
	public Vector3f getPosition(){
		return position;
	}
	public Vector3f getOldPosition(){
		return oldPosition;
	}
	public Vector3f getVelocity(){
		return velocity;
	}
	
	public void updatePosition(Vector3f newPos) {
		this.oldPosition = position;
		this.position = newPos;
	}
	
	public void updateVel(Vector3f newVel){
		setVelocity(newVel);
	}

	public abstract float getSize();
	
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
}
