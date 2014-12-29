package ee.liiser.siim.galaxies.drawing;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class GraphicsThread extends Thread {

	private static final float fps = 60.0f;

	private Drawable[] points;

	//What and how to look at variables
	private Drawable target;
	private Vector3f eye;
	private Vector3d up;

	public GraphicsThread(Drawable[] points) {
		this.points = points;
		DrawUtil.draw(points);
	}

	public void lookAt(Drawable point, Vector3f eye, Vector3d up) {
		this.target = point;
		this.eye = eye;
		this.up = up;

	}

	@Override
	public synchronized void run() {
		long frametime = 0;
		long start = 0;
		long maxFrame = (long) (1000 / fps);
		while (true) {
			if (frametime > maxFrame) {
				System.out.println(frametime);
			} else {
				try {
					wait(maxFrame - frametime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			start = System.currentTimeMillis();
			DrawUtil.update(points);
			if (target != null) {
				Vector3f currentEye = new Vector3f();
				currentEye.add(target.getPosition(), eye);
				DrawUtil.lookAt(new Point3d(currentEye),
						new Point3d(target.getPosition()), up);
			}
			frametime = System.currentTimeMillis() - start;

		}

	}

}
