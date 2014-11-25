package ee.liiser.siim.galaxies;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.calculations.WorkerThread;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.ObjectFactory;
import ee.liiser.siim.galaxies.data.Star;
import ee.liiser.siim.galaxies.data.velocity.VelocityCore;
import ee.liiser.siim.galaxies.drawing.Drawable;
import ee.liiser.siim.galaxies.drawing.GraphicsThread;

public class Main {

	static Star[] stars;
	static Core[] cores;

	/**
	 * Number of stars per core (galaxy)
	 */
	static int starcount = 500;

	private static final Method method = Method.VELOCITY_VERLET;

	public static void main(String[] args) {

		ObjectFactory factory = new ObjectFactory(method);

		Core core1 = factory.makeCore(new Vector3f());
		Core core2 = factory.makeCore(new Vector3f(0, 10, -30));
		((VelocityCore) core2).setVelocity(new Vector3f(0, 0, 0.3f));
		cores = new Core[] { core1, core2};

		stars = new Star[starcount * cores.length];
		int index = 0;
		for (Core core : cores) {
			for (int i = 0; i < starcount; i++) {
				stars[index++] = factory.makeStar(core,
						(float) distanceWithDistribution(), new Vector3f(1,1,1));
			}
		}

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);

		new WorkerThread(cores, stars, method).start();
		GraphicsThread g = new GraphicsThread(points);
		g.start();
	}

	/**
	 * Generates random numbers according to a x*exp(-x) distribution with an error of order O(r^6)
	 * 
	 * @return Random numbers according to a x*exp(-x) distribution
	 */
	public static double distanceWithDistribution() {
		double r = Math.random();
		return r - r * r + Math.pow(r, 3) * 3 / 2 - Math.pow(r, 4) * 8 / 3
				+ Math.pow(r, 5) * 125 / 24;
	}

}
