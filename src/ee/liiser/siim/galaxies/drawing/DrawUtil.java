package ee.liiser.siim.galaxies.drawing;

import java.awt.Dimension;
import java.util.HashMap;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import ee.liiser.siim.galaxies.data.Core;

/**
 * A helper class that handles initializing the graphics window and keeping it updated
 * @author Siim
 *
 */
class DrawUtil {

	private static HashMap<Drawable, TransformGroup> tgs = new HashMap<Drawable, TransformGroup>();
	private static SimpleUniverse universe;
	
	private static final Color3f bright = new Color3f(1, 1, 0.7f);

	static void update(Drawable[] points) {
		for (Drawable point : points) {
			TransformGroup tg = tgs.get(point);
			Transform3D trans = new Transform3D();
			trans.setTranslation(point.getPosition());
			tg.setTransform(trans);
		}
	}

	static void draw(Drawable[] points) {
		initWindow();
		
		BranchGroup group = addPoints(points);

		lookAt(new Point3d(0, 0, 20), new Point3d(0, 0, 0), new Vector3d(0, 1,
				0));

		// add the group of objects to the Universe
		universe.addBranchGraph(group);
	}

	private static BranchGroup addPoints(Drawable[] points) {
		BranchGroup group = new BranchGroup();

		for (Drawable point : points) {
			Vector3f vector = new Vector3f(point.getPosition());
			Transform3D transform = new Transform3D();
			transform.setTranslation(vector);

			TransformGroup tg = new TransformGroup();
			tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			tg.setTransform(transform);

			Sphere sphere = new Sphere(point.getSize());
			if (point instanceof Core) {
				sphere.getAppearance().getMaterial().setEmissiveColor(bright);
				
				PointLight light = new PointLight(bright, new Point3f(),
						new Point3f(1, 0, 0.05f));
				light.setInfluencingBounds(new BoundingSphere(new Point3d(0.0,
						0.0, 0.0), 100.0));

				tg.addChild(light);
			}
			tg.addChild(sphere);
			tgs.put(point, tg);

			group.addChild(tg);
		}
		return group;
	}

	private static void initWindow() {
		JFrame frame = new JFrame("Kompfys: Galaktikad");
		
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		
		universe = new SimpleUniverse(canvas);
		universe.getViewer().getView().setBackClipDistance(100);
		
		canvas.setSize(1000,1000);
		ZoomListener listener = new ZoomListener(universe);
		canvas.addMouseListener(listener);
		canvas.addMouseWheelListener(listener);
		canvas.addMouseMotionListener(listener);
		frame.add(canvas);
		frame.pack();
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,500));
		frame.setVisible(true);
		
	}

	private static void lookAt(Point3d eye, Point3d center, Vector3d up) {
		// Position the position from which the user is viewing the scene
		TransformGroup viewTransform = universe.getViewingPlatform()
				.getViewPlatformTransform();
		Transform3D t3d = new Transform3D();
		viewTransform.getTransform(t3d);
		t3d.lookAt(eye, center, up);
		t3d.invert();
		viewTransform.setTransform(t3d);

	}
}
