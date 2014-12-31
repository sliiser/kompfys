package ee.liiser.siim.galaxies.data;

import javax.vecmath.Vector3d;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.data.basic.BasicCore;
import ee.liiser.siim.galaxies.data.basic.BasicStar;
import ee.liiser.siim.galaxies.data.velocity.VelocityCore;
import ee.liiser.siim.galaxies.data.velocity.VelocityStar;

/**
 * A helper class that generates galaxies according to the method provided
 *
 */
public class ObjectFactory {

	private Method method;

	/**
	 * Initializes this factory with the specified method
	 * @param method is the used method for the simulation. One of {@link Calculator#Method}
	 */
	public ObjectFactory(Method method) {
		this.method = method;
	}

	/**
	 * Generates a galaxy
	 * 
	 * @param position Position of the core of the galaxy 
	 * @param velocity Initial velocity of the core
	 * @param normal The normal vector of the plane of the galaxy
	 * @param width Standard deviation width of the stars around the core
	 * @param starCount Number of stars in the galaxy
	 * @return a generated {@link Galaxy} object with the given parameters
	 */
	public Galaxy makeGalaxy(Vector3d position, Vector3d velocity,
			Vector3d normal, double width, int starCount) {
		Core core = makeCore(position, velocity);
		Star[] stars = new Star[starCount];
		for (int i = 0; i < starCount; i++) {
			stars[i] = makeStar(core, (distanceWithDistribution() * width), normal);
		}
		return new Galaxy(core, stars);
	}

	private Core makeCore(Vector3d position, Vector3d velocity) {
		switch (method) {
		case BASIC_VERLET:
			return new BasicCore(position, velocity);
		case VELOCITY_VERLET:
			return new VelocityCore(position, velocity);
		default:
			return null;
		}
	}

	private Star makeStar(Core core, double distance, Vector3d normal) {
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
	 * Generates random numbers according to a x*exp(-x) distribution with an
	 * error of order O(r^6)
	 * 
	 * @return Random numbers according to a x*exp(-x) distribution
	 */
	private static double distanceWithDistribution() {
		double r = Math.random();
		return r - r * r + Math.pow(r, 3) * 3 / 2 - Math.pow(r, 4) * 8 / 3
				+ Math.pow(r, 5) * 125 / 24;
	}
}
