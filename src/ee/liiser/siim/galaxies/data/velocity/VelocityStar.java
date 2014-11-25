package ee.liiser.siim.galaxies.data.velocity;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.data.Star;

public class VelocityStar extends VelocityObject implements Star{

	public VelocityStar(VelocityCore core, float distance) {
		this(core, distance, Math.random()*Math.PI*2);
	}

	public VelocityStar(VelocityCore core, float distance, double d) {
		float x = (float) (distance * Math.cos(d));
		float y = (float) (distance * Math.sin(d));
		double theta = 1 / (distance * Math.sqrt(distance));
		this.position = new Vector3f(core.getPosition().x + x, core.getPosition().y + y, 0);
		
		this.velocity = new Vector3f((float) (x*Math.cos(theta) + y*Math.sin(theta)), (float) (y*Math.cos(theta) - x*Math.sin(theta)), 0.0f);
		velocity.add(core.getVelocity());
		this.getVelocity().scale(1/Calculator.dt);
	}
	
	@Override
	public float getSize() {
		return STAR_SIZE;
	}

}
