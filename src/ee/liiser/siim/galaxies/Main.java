package ee.liiser.siim.galaxies;

import javax.vecmath.Vector3f;

import ee.liiser.siim.galaxies.calculations.Calculator;
import ee.liiser.siim.galaxies.calculations.Calculator.Method;
import ee.liiser.siim.galaxies.data.Core;
import ee.liiser.siim.galaxies.data.ObjectFactory;
import ee.liiser.siim.galaxies.data.Star;
import ee.liiser.siim.galaxies.drawing.DrawUtil;
import ee.liiser.siim.galaxies.drawing.Drawable;

public class Main {

	static Star[] stars;
	static Core[] cores;

	static float[] starDistances = new float[] { 2, 3, 4, 5, 6 };
	static int[] starCounts = new int[] { 12, 18, 24, 30, 36 };
	
	private static final Method method = Method.BASIC_VERLET;

	public static void main(String[] args) {

		ObjectFactory factory = new ObjectFactory(method);
		
		Core core = factory.makeCore(new Vector3f());
//		Core core2 = new Core(new Vector3f(0, 10, 30));
//		core.setVelocity(new Vector3f(0, 0, -0.3f));
		cores = new Core[] { core };

		int totalCount = 0;
		for (int i : starCounts)
			totalCount += i;
		stars = new Star[totalCount*cores.length];
		int index = 0;
		for (int i = 0; i < starDistances.length; i++) {
			for (int j = 0; j < starCounts[i]; j++) {
				for( Core c : cores){
					stars[index++] = factory.makeStar(c, starDistances[i], Math.random()
						* 2 * Math.PI);
				}
			}
		}

		Drawable[] points = new Drawable[stars.length + cores.length];
		System.arraycopy(cores, 0, points, 0, cores.length);
		System.arraycopy(stars, 0, points, cores.length, stars.length);
		DrawUtil.draw(points);


		Calculator calc = new Calculator(cores, stars, method);
		
		
		long frametime = 0;
		long start = 0;
		long maxFrame = 1000;
		while (true) {
			//TODO graafika ja calc eraldi threadi
			if (frametime > maxFrame) {
				System.out.println(frametime);
			} else {

				try {
					Thread.sleep(maxFrame - frametime);
				} catch (InterruptedException e) {
					throw new RuntimeException();
				}
			}
			start = System.currentTimeMillis();
			calc.step();
			DrawUtil.update(stars);
			DrawUtil.update(cores);
//			DrawUtil.lookAt(
//					new Point3d(cores[1].getPosition().x + 30, cores[1]
//							.getPosition().y, cores[1].getPosition().z),
//					new Point3d(cores[1].getPosition()), new Vector3d(0, 0, -1));
			frametime = System.currentTimeMillis() - start;

		}

	}

}
