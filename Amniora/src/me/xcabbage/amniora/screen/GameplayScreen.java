package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.input.AmniInputProcessor;
import me.xcabbage.amniora.util.Geometry;

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
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
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
import com.badlogic.gdx.graphics.g3d.model.data.ModelMesh;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class GameplayScreen implements Screen {

	private static final int VECTOR_COUNT = 100;
	private static final float SQUARE_SIDE = 100;
	private static final float ORBIT_SCALE = 4;
	private static final int VECTORS_SQRT = 10;
	private static final float SPHERE_SCALE = 0.6f;

	private PerspectiveCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private ModelBatch modelBatch;
	private Environment environment;
	private ShapeRenderer shapeRenderer;
	public CameraInputController camController;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public boolean loading;
	public Mesh mesh;
	public ModelInstance moving;
	InputMultiplexer multiplexer;
	Pixmap globeMap;
	ModelBuilder modelBuilder;
	MeshBuilder meshBuilder;
	Mesh globeMesh;
	Model globeModel;
	public Vector2[] xzVect;
	public Vector3[] sphereVect;
	public Color[] pointColor;
	ModelInstance globeInstance;

	// LOADING - CREATION
	public GameplayScreen(final GameAmn gam) {
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

		modelBatch = new ModelBatch();
		shapeRenderer = new ShapeRenderer();
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

		// MESHES
		/*
		 * 
		 * meshBuilder = new MeshBuilder(); meshBuilder.begin(new
		 * VertexAttributes(new VertexAttribute( Usage.Position, 3,
		 * "a_position"))); meshBuilder.sphere(1f, 1f, 1f, 5, 3); globeMesh =
		 * meshBuilder.end(); instances.add(new ModelInstance(new
		 * Model(globeMesh));
		 */

		modelBuilder = new ModelBuilder();
		globeModel = modelBuilder.createSphere(SPHERE_SCALE, SPHERE_SCALE,
				SPHERE_SCALE, 32, 32,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position | Usage.Normal);
		// instances.add(new ModelInstance(globeModel, 2, 2, 2));

		// OTHER ASSETS
		assets = new AssetManager();

		assets.load("data/planet_colors.g3db", Model.class);

		texture = new Texture(Gdx.files.internal("data/earth2.jpg"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		/*
		 * stuff with xz -> xyz (spherical) FIRST: generate xz slots, create
		 * colors
		 */
		xzVect = new Vector2[VECTOR_COUNT];
		pointColor = new Color[VECTOR_COUNT];
		int count = 0;
		for (float i = 0; i <= 1f; i = i + 0.1f) {
			for (float j = 0; j <= 1f; j = j + 0.1f) {
				xzVect[count] = new Vector2(i, j);
				System.out.println("Registered xz vector on position " + count
						+ ": " + xzVect[count]);
				switch (count % 6) {
				case 1:
					pointColor[count] = Color.RED;
					break;
				case 2:
					pointColor[count] = Color.CYAN;
					break;
				case 3:
					pointColor[count] = Color.YELLOW;
					break;
				case 4:
					pointColor[count] = Color.BLUE;
					break;
				case 5:
					pointColor[count] = Color.WHITE;
					break;
				default:
					pointColor[count] = Color.ORANGE;
					break;
				}

				count++;
			}
		}

		/* translate the vectors to spherical xyz coords */
		sphereVect = new Vector3[VECTOR_COUNT];
		for (int a = 0; a < VECTOR_COUNT; a++) {
			sphereVect[a] = mapFromPlaneToSphere2(xzVect[a]);
		}

		/* output the final product */

		for (int a = 0; a < VECTOR_COUNT; a++) {

			System.out.println("Translated " + xzVect[a] + " to "
					+ sphereVect[a] + ".");
		}

		// proceed
		loading = true;

	}

	public void doneLoading() {

		// PLANET
		Model planet = assets.get("data/planet_colors.g3db", Model.class);
		ModelInstance planetInstance = new ModelInstance(planet, 0, 0, 0);

		Material mat = planetInstance.materials.get(0);
		mat.set(new TextureAttribute(TextureAttribute.Diffuse, texture));

		moving = planetInstance;

		// instances.add(planetInstance);

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
			// instances.add(ball);
		}

		// create small spheres in the xyz world with co-ords and colors taken
		// from the previous processes

		/*
		 * OLD METHOD
		 * 
		 * Vector3 v, tempV; meshBuilder = new MeshBuilder();
		 * meshBuilder.begin(Usage.Position | Usage.Normal); for (int a = 0; a <
		 * VECTOR_COUNT; a++) { v = sphereVect[a];
		 * 
		 * ModelInstance ball = new ModelInstance(globeModel, v.x ORBIT_SCALE,
		 * v.y * ORBIT_SCALE, v.z * ORBIT_SCALE); ball.materials.get(0).set( new
		 * Material(ColorAttribute.createDiffuse(pointColor[a]))); //
		 * instances.add(ball); // try to create a mesh off the given vectors
		 * meshBuilder.vertex(v, v.nor(), Color.WHITE, null); }
		 * 
		 * globeMesh = meshBuilder.end();
		 * 
		 * modelBuilder.begin(); modelBuilder.part("globeMesh", globeMesh,
		 * GL20.GL_TRIANGLES, new
		 * Material(ColorAttribute.createDiffuse(Color.MAGENTA))); globeModel =
		 * modelBuilder.end(); instances.add(new ModelInstance(globeModel));
		 */

		/* xoppa's method */

		/* triangles */

		// MeshPartBuilder builder = mb.part("name", GL20.GL_TRIANGLES,
		// Usage.Position,
		// new Material(ColorAttribute.createDiffuse(Color.MAGENTA)));
		// short index1 = builder.vertex(sphereVect[0], null, null, null);
		// for (int a = 1; a < VECTOR_COUNT; a += 2) {
		// try {
		// short index2 = builder.vertex(sphereVect[a], null, null, null);
		// short index3 = builder.vertex(sphereVect[a + 1], null, null,
		// null);
		// builder.triangle(index1, index3, index2);
		// index1 = index3;
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }

		/*
		 * points
		 * 
		 * MeshPartBuilder builder = mb.part("name", GL20.GL_POINTS,
		 * Usage.Position, new
		 * Material(ColorAttribute.createDiffuse(Color.MAGENTA))); Vector3
		 * normal = new Vector3(); for (int a = 0; a < VECTOR_COUNT; a++) { v =
		 * sphereVect[a]; final short index = builder.vertex(v, null, null,
		 * null); builder.index(index); }
		 */
		Geometry ico = new Geometry();
		Vector3 v, tempV;
		ModelBuilder mb = new ModelBuilder();
		mb.begin();

		// Create mesh nodes
		for (int a = 0; a < ico.vertices.length; a = a + 9) {

			MeshPartBuilder builder = mb
					.part("v" + a,
							GL20.GL_TRIANGLES,
							Usage.Position,
							new Material(ColorAttribute
									.createDiffuse(Color.LIGHT_GRAY)));
			Vector3 v1 = new Vector3(ico.vertices[a], ico.vertices[a + 1],
					ico.vertices[a + 2]);
			Vector3 v2 = new Vector3(ico.vertices[a + 3], ico.vertices[a + 4],
					ico.vertices[a + 5]);
			Vector3 v3 = new Vector3(ico.vertices[a + 6], ico.vertices[a + 7],
					ico.vertices[a + 8]);

			builder.triangle(v1, v2, v3);
			final short index1 = builder.vertex(v1, null, null, null);
			final short index2 = builder.vertex(v2, null, null, null);
			final short index3 = builder.vertex(v3, null, null, null);
			builder.index(index1, index2, index3);

		}

		Model model = mb.end();
		System.out.println(model.meshParts.size + " nodes registered.");
		globeInstance = new ModelInstance(model);
		instances.add(globeInstance);

		// Color nodes
		Color col;
		for (int a = 0; a < globeInstance.model.meshParts.size; a++) {
			try {

				Material mate = globeInstance.materials.get(a);

				switch (a % 6) {
				case 1:
					col = Color.RED;
					break;
				case 2:
					col = Color.CYAN;
					break;
				case 3:
					col = Color.YELLOW;
					break;
				case 4:
					col = Color.BLUE;
					break;
				case 5:
					col = Color.WHITE;
					break;
				default:
					col = Color.ORANGE;
					break;
				}
				mate.set(ColorAttribute.createDiffuse(col));
				System.out.println("Assigning " + a);
			} catch (Exception e) {
				System.out.println("Can't assign color to mat number " + a);
			}

		}

		loading = false;

	}

	// ----- GAME LOOPS-----

	// updates
	public void updateGame() {
		// orbitEverything();

	}

	void orbitEverything() {
		try {

			for (int x = 1; x <= 4; x++) {
				Vector3 pos = instances.get(x).transform
						.getTranslation(Vector3.Zero);
				Vector3 posA = wrapPoint(pos, new Vector3(1, 2, 5), 5);

				instances.get(x).transform.translate(posA.sub(pos));
			}

		} catch (Exception e) {

		}
	}

	// render
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

		shapeRenderer.begin(ShapeType.Filled);
		Vector2 v;
		for (int a = 0; a < VECTORS_SQRT; a++) {
			for (int b = 0; b < VECTORS_SQRT; b++) {
				v = xzVect[a * VECTORS_SQRT + b];
				shapeRenderer.setColor(pointColor[a * VECTORS_SQRT + b]);
				shapeRenderer.rect(150 + (v.x * SQUARE_SIDE),
						150 + (v.y * SQUARE_SIDE), SQUARE_SIDE * 0.1f,
						SQUARE_SIDE * 0.1f);

			}
		}
		shapeRenderer.end();

		((AmniInputProcessor) multiplexer.getProcessors().get(1)).update();
		updateGame();
	}

	// UTILITY

	// translations
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

	Vector3 mapFromPlaneToSphere(Vector2 xzVector) {
		// Map uniformly onto cylinder along z axis of radius 1 and height from
		// -1 to 1
		// as expressed in cylindrical co-ordinates
		float x = xzVector.x, y = xzVector.y;
		float phi = (float) (y * 2 * Math.PI);
		float z = 2 * x - 1;
		float rho = 1;
		// Project that radially along z-axis to be on sphere
		rho = (float) Math.sqrt(1 - z * z);
		// Convert to euclidian space
		return new Vector3((float) (rho * Math.cos(y)),
				(float) (rho * Math.sin(y)), z);
	}

	Vector3 mapFromPlaneToSphere2(Vector2 xzVector) {
		// Let x, y be a point on your plane (0..1).
		float x = xzVector.x, y = xzVector.y;
		float z = -1 + 2 * x;
		float phi = (float) (2 * Math.PI * y);
		float theta = (float) Math.asin(z);
		return new Vector3((float) (Math.cos(theta) * Math.cos(phi)),
				(float) (Math.cos(theta) * Math.sin(phi)), z);

	}

	// UNUSED METHODS

	/* unused methods */
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
