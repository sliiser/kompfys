package ee.liiser.siim.galaxies.data;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.data.basic.BasicCore;
import ee.liiser.siim.galaxies.data.basic.BasicStar;
import ee.liiser.siim.galaxies.data.velocity.VelocityCore;
import ee.liiser.siim.galaxies.data.velocity.VelocityStar;

public class ObjectFactory {
	
	private Method method;

	public ObjectFactory(Method method){
		this.method = method;
	}
	
	public Core makeCore(Vector3f position) {
		switch (method) {
		case BASIC_VERLET:
			return new BasicCore(position);
		case VELOCITY_VERLET:
			return new VelocityCore(position);
		default:
			return null;
		}
	}

	public Star makeStar(Core core, float distance, double angle) {
		switch (method) {
		case BASIC_VERLET:
			return new BasicStar((BasicCore) core, distance, angle);
		case VELOCITY_VERLET:
			return new VelocityStar((VelocityCore) core, distance, angle);
		default:
			return null;
		}
	}
}