package ee.liiser.siim.galaxies;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.calculations.WorkerThread;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.Galaxy;
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

		Galaxy galaxy1 = factory.makeGalaxy(new Vector3f(), new Vector3f(), new Vector3f(0,0,1), 1, starcount);
		Galaxy galaxy2 = factory.makeGalaxy(new Vector3f(0,2,-30), new Vector3f(0,0,0.3f), new Vector3f(0,1,0), 1, starcount);
		
		cores = new Core[] { galaxy1.getCore(), galaxy2.getCore()};

		stars = new Star[starcount * cores.length];
		System.arraycopy(galaxy1.getStars(), 0, stars, 0, starcount);
		System.arraycopy(galaxy2.getStars(), 0, stars, starcount, starcount);

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);

		new WorkerThread(cores, stars, method).start();
		GraphicsThread g = new GraphicsThread(points);
		g.start();
	}

}
