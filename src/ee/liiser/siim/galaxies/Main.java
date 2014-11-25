package ee.liiser.siim.galaxies;

import javax.vecmath.Vector3d;
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

	static float[] starDistances = new float[] { 2, 3, 4, 5, 6 };
	static int[] starCounts = new int[] { 12, 18, 24, 30, 36 };

	private static final Method method = Method.VELOCITY_VERLET;

	public static void main(String[] args) {

		ObjectFactory factory = new ObjectFactory(method);

		Core core = factory.makeCore(new Vector3f());
		Core core2 = factory.makeCore(new Vector3f(0, 10, -30));
		((VelocityCore) core2).setVelocity(new Vector3f(0, 0, 0.3f));
		cores = new Core[] { core, core2 };

		int totalCount = 0;
		for (int i : starCounts)
			totalCount += i;
		stars = new Star[totalCount * cores.length];
		int index = 0;
		for (int i = 0; i < starDistances.length; i++) {
			for (int j = 0; j < starCounts[i]; j++) {
				for (Core c : cores) {
					stars[index++] = factory.makeStar(c, starDistances[i]);
				}
			}
		}

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);

		new WorkerThread(cores, stars, method).start();
		GraphicsThread g = new GraphicsThread(points);
		g.lookAt(cores[1], new Vector3f(30, 0, 0), new Vector3d(0,0,1));
		g.start();
	}

}
