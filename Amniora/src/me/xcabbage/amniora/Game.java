package me.xcabbage.amniora;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.materials.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.materials.Material;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.Environment;

public class Game implements ApplicationListener {
	private PerspectiveCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private Model model;
	private ModelInstance instance;
	private ModelBatch modelBatch;
	private Environment environment;

	@Override
	public void create() {
		modelBatch = new ModelBatch();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Camera init
		camera = new PerspectiveCamera(67, w, h);
		camera.position.set(10f, 10f, 10f);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 300f;
		camera.update();

		// Lighting init
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));
		// Model init
		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.RED)),
				Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);

		// Old || unused methods
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		model.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// batch.setProjectionMatrix(camera.combined);
		// batch.begin();
		// // DRAWING BELOW
		//
		// sprite.draw(batch);
		//
		// // DRAWING UP
		// batch.end();
		modelBatch.begin(camera);
		modelBatch.render(instance);
		modelBatch.end();
		System.out.println("rendering");

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
