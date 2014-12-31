package ee.liiser.siim.galaxies.drawing;

import java.awt.event.KeyListener;

/**
 * The thread that graphics runs in. Keeps fps in check and uses {@link DrawUtil}
 * @author Siim
 *
 */
public class GraphicsThread extends Thread {

	private static final double fps = 60.0f;

	private Drawable[] points;

	public GraphicsThread(Drawable[] points, KeyListener keyListener) {
		this.points = points;
		DrawUtil.draw(points, keyListener);
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
			frametime = System.currentTimeMillis() - start;

		}

	}

}
