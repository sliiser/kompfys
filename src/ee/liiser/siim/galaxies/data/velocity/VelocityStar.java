package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.data.Star;

public class VelocityStar extends VelocityObject implements Star{

	public VelocityStar(VelocityCore core, float distance) {
		this(core, distance, Math.random()*Math.PI*2);
	}

	public VelocityStar(VelocityCore core, float distance, double angle) {
		float x = (float) (distance * Math.cos(angle));
		float y = (float) (distance * Math.sin(angle));
		this.position = new Vector3f(x,y,0);
		this.position.add(core.getPosition());
		
		this.velocity = new Vector3f(y, -x, 0);
		this.velocity.scale((float) (1 / (distance * Math.sqrt(distance))));
		this.velocity.add(core.getVelocity());

	}
	
	@Override
	public float getSize() {
		return STAR_SIZE;
	}

}
