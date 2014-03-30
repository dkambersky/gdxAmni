package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.input.AmniInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class GameplayScreen implements Screen {
	private PerspectiveCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private ModelBatch modelBatch;
	private Environment environment;
	public CameraInputController camController;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public boolean loading;
	public Mesh mesh;
	public ModelInstance moving;
	InputMultiplexer multiplexer;

	public GameplayScreen(final GameAmn gam) {
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

		modelBatch = new ModelBatch();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Camera init
		// g3dbect
		camera = new PerspectiveCamera(67, w, h);

		camera.position.set(5f, 0, 5f);
		camera.lookAt(0, -1, 0);
		camera.near = 0.1f;
		camera.far = 300f;

		camera.update();

		// controls

		multiplexer = new InputMultiplexer();
		camController = new CameraInputController(camera);
		multiplexer.addProcessor(camController);

		multiplexer.addProcessor(new AmniInputProcessor(gam));
		((AmniInputProcessor) multiplexer.getProcessors().get(1))
				.setCamera(camera);

		// Lighting init
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, 1f));

		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));
		environment.add(new DirectionalLight().set(-0.8f, -0.8f, -0.8f, 1f,
				0.8f, 0.2f));

		new ModelBuilder();

		assets = new AssetManager();
		assets.load("data/ship.g3db", Model.class);
		assets.load("data/planet_colors.g3db", Model.class);

		// Old || unused methods
		batch = new SpriteBatch();
		// texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture = new Texture(Gdx.files.internal("data/earth2.jpg"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		new TextureRegion(texture, 0, 0, 512, 275);

		loading = true;

	}

	public void doneLoading() {

		// PLANET
		Model planet = assets.get("data/planet_colors.g3db", Model.class);
		ModelInstance planetInstance = new ModelInstance(planet, 0, 0, 0);

		Material mat = planetInstance.materials.get(0);
		mat.set(new TextureAttribute(TextureAttribute.Diffuse, texture));

		moving = planetInstance;

		instances.add(planetInstance);

		// SPHERES AROUND
		Color color = Color.WHITE;

		for (int a = 0; a < 4; a++) {
			ModelInstance ball = new ModelInstance(planet, 0, -15, -1
					* (a * 10 + 10));
			switch (a) {
			case 0:
				color = Color.GREEN;
				break;
			case 1:
				color = Color.YELLOW;
				break;
			case 2:
				color = Color.BLUE;
				break;
			case 3:
				color = Color.RED;
				break;

			}
			ball.materials.get(0).set(
					new Material(ColorAttribute.createDiffuse(color)));
			instances.add(ball);
		}

		loading = false;
	}

	@Override
	public void render(float delta) {
		if (loading && assets.update())
			doneLoading();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// batch.setProjectionMatrix(camera.combined);
		// batch.begin();
		// DRAWING BELOW

		// sprite.draw(batch);

		// // DRAWING UP
		// batch.end();
		if (camController != null)
			camController.update();
		texture.bind();
		modelBatch.begin(camera);
		modelBatch.render(instances, environment);

		modelBatch.end();
		((AmniInputProcessor) multiplexer.getProcessors().get(1)).update();
		updateGame();

	}

	public void updateGame() {
		try {

			for (int x = 0; x <= 4; x++) {
				Vector3 pos = instances.get(x).transform
						.getTranslation(Vector3.Zero);
				System.out.println("Position: " + pos.x + ", " + pos.y + ", "
						+ pos.z);
				Vector3 wrapper = new Vector3(pos).sub(wrapPoint(pos,
						Vector3.Y, 5));
				instances.get(x).transform.translate(wrapper);
				System.out.println("Translating by: " + wrapper);

			}

		} catch (Exception e) {

		}

	}

	Vector3 wrapPoint(Vector3 position, Vector3 axis, float angle) {

		Vector3 temp = new Vector3(Vector3.Zero);
		Vector3 fin = new Vector3(position);
		temp.sub(position);
		fin.add(temp);
		fin.rotate(axis, angle);
		temp.rotate(axis, angle);
		fin.add(-temp.x, -temp.y, -temp.z);
		return fin;

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	// Camera rotation

	/**
	 * Rotates the camera around the current center of screen projected on
	 * xz-plane
	 * 
	 * @author radioking from Badlogic forum (libGDX)
	 * @param angle
	 *            rotation angle in degrees.
	 */
	@SuppressWarnings("deprecation")
	public void orbitLookAt(float angle) {
		Plane xzPlane = new Plane(new Vector3(1, 0, 2), new Vector3(2, 0, 5),
				new Vector3(6, 0, 8));
		Vector3 lookAtPoint = new Vector3(0, 0, 0);
		Ray cameraViewRay = new Ray(new Vector3(0, 0, 0), new Vector3(0, -1, 0));
		Vector3 cameraPosition = new Vector3(0, 0, 0), orbitReturnVector = new Vector3(
				0, 0, 0);
		float orbitRadius;

		// (1) get intersection point for
		// camera viewing direction and xz-plane
		cameraViewRay.set(camera.position, camera.direction);
		Intersector.intersectRayPlane(cameraViewRay, xzPlane, lookAtPoint);

		// (2) calculate radius between
		// camera position projected on xz-plane
		// and the intersection point from (1)
		orbitRadius = lookAtPoint.dst(cameraPosition.set(camera.position));

		// (3) move camera to intersection point from (1)
		camera.position.set(lookAtPoint);

		// (4) rotate camera by 1° around y-axis
		// according to winding clockwise/counter-clockwise
		camera.rotate(angle, 0, 1, 0);

		// (5) move camera back by radius
		orbitReturnVector.set(camera.direction.tmp().scl(-orbitRadius));
		camera.translate(orbitReturnVector.x, orbitReturnVector.y,
				orbitReturnVector.z);
	}

}
