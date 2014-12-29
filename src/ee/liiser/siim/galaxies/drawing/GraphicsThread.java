package ee.liiser.siim.galaxies.drawing;

public class GraphicsThread extends Thread {

	private static final float fps = 60.0f;

	private Drawable[] points;

	public GraphicsThread(Drawable[] points) {
		this.points = points;
		DrawUtil.draw(points);
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
