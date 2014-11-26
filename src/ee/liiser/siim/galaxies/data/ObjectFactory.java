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
	
	public Galaxy makeGalaxy(Vector3f position, Vector3f velocityOrOldPosition, Vector3f normal, double width, int starCount){
		Core core = makeCore(position, velocityOrOldPosition);
		Star[] stars = new Star[starCount];
		for(int i = 0; i < starCount; i++){
			stars[i] = makeStar(core, (float) (distanceWithDistribution()*width), normal);
		}
		return new Galaxy(core, stars);
	}
	
	public Core makeCore(Vector3f position, Vector3f velocityOrOldPosition) {
		switch (method) {
		case BASIC_VERLET:
			return new BasicCore(position, velocityOrOldPosition);
		case VELOCITY_VERLET:
			return new VelocityCore(position, velocityOrOldPosition);
		default:
			return null;
		}
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

	public Star makeStar(Core core, float distance) {
		switch (method) {
		case BASIC_VERLET:
			return new BasicStar((BasicCore) core, distance);
		case VELOCITY_VERLET:
			return new VelocityStar((VelocityCore) core, distance);
		default:
			return null;
		}
	}
	
	public Star makeStar(Core core, float distance, Vector3f normal) {
		switch (method) {
		case BASIC_VERLET:
			return new BasicStar((BasicCore) core, distance, normal);
		case VELOCITY_VERLET:
			return new VelocityStar((VelocityCore) core, distance, normal);
		default:
			return null;
		}
	}
	
	/**
	 * Generates random numbers according to a x*exp(-x) distribution with an error of order O(r^6)
	 * 
	 * @return Random numbers according to a x*exp(-x) distribution
	 */
	private static double distanceWithDistribution() {
		double r = Math.random();
		return r - r * r + Math.pow(r, 3) * 3 / 2 - Math.pow(r, 4) * 8 / 3
				+ Math.pow(r, 5) * 125 / 24;
	}
}
