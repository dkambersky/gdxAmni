package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.input.AmniInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
		camera.far = 100f;

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

		assets.load("data/planet_colors.g3db", Model.class);

		texture = new Texture(Gdx.files.internal("data/earth2.jpg"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

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

			for (int x = 1; x <= 4; x++) {
				Vector3 pos = instances.get(x).transform
						.getTranslation(Vector3.Zero);
				Vector3 posA = wrapPoint(pos, Vector3.Y, 5);
				System.out.println("Original: " + pos + " | New: " + posA);
				instances.get(x).transform.translate(posA.sub(pos));
			}

		} catch (Exception e) {

		}

	}

	Vector3 wrapPoint(Vector3 position, Vector3 axis, float angle) {
		Vector3 temp = new Vector3(position);
		temp.rotate(axis, angle);
		return temp;

	}

	Vector3 wrapPoint(Vector3 position, Vector3 around, Vector3 axis,
			float angle) {
		Vector3 temp = new Vector3(position);
		temp.sub(around);
		temp.rotate(axis, angle);
		temp.add(around);
		return temp;

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

}
