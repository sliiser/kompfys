package ee.liiser.siim.galaxies.drawing;

import java.util.HashMap;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import ee.liiser.siim.galaxies.data.Core;

public class DrawUtil {

	private static HashMap<Drawable, TransformGroup> tgs = new HashMap<Drawable, TransformGroup>();
	private static SimpleUniverse universe;
	
	private static final Color3f bright = new Color3f(1, 1, 0.7f);

	public static void update(Drawable[] points) {
		for (Drawable point : points) {
			TransformGroup tg = tgs.get(point);
			Transform3D trans = new Transform3D();
			trans.setTranslation(point.getPosition());
			tg.setTransform(trans);
		}
	}

	public static void draw(Drawable[] points) {
		universe = new SimpleUniverse();
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

		Color3f light1Color = new Color3f(1, 1, 1);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		// group.addChild(light1);

		lookAt(new Point3d(0, 0, 30), new Point3d(0, 0, 0), new Vector3d(0, 1,
				0));

		// add the group of objects to the Universe
		universe.addBranchGraph(group);
	}

	public static void lookAt(Point3d eye, Point3d center, Vector3d up) {
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
