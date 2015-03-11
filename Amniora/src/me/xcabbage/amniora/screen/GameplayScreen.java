package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.GameInstance;
import me.xcabbage.amniora.assets.Assets;
import me.xcabbage.amniora.input.AmniInputProcessor;
import me.xcabbage.amniora.util.Geometry;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Array;

public class GameplayScreen implements Screen {

	private static final boolean GRADUAL_COLORING = true;
	private static final boolean CONSOLE_ENABLED = true;

	// // / Screen logic
	InputMultiplexer multiplexer, cMultiplexer;

	public GameAmn game;
	public GameInstance instance;
	public boolean loading;
	public int width, height;
	Stage stage;

	// // / Graphical framework
	private PerspectiveCamera camera;
	private Environment environment;
	public CameraInputController camController;
	public AssetManager assets;

	// // / Builders
	ModelBuilder modelBuilder;
	MeshBuilder meshBuilder;

	// // / Graphical objects

	// // 3D Render
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	private Texture texture;
	private ModelBatch modelBatch;
	public Mesh mesh;
	public ModelInstance moving;
	public static Texture tile_texture_1;
	Pixmap globeMap;

	Mesh globeMesh;
	Model globeModel;
	public ModelInstance globeInstance;

	Color[] col1 = new Color[6];
	public Vector2[] xzVect;
	public Vector3[] sphereVect;
	public Color[] pointColor;

	// // 2D Render
	// / Batch
	private SpriteBatch spriteBatch;
	// / Fonts
	BitmapFont F_debug, F_history, F_history2, F_buttons, F_buttonsHighlight,
			F_buttonsOutline0, F_buttonsOutline1, F_buttonsOutline2,
			F_buttonsOutline3;

	// / Console
	Sprite console_sprite;
	Texture console_texture;
	TextField console_textfield;
	public static String[] console_history = new String[12];
	private TextFieldStyle console_textfield_style;
	// // /DEBUG!
	Pixmap tempMap;
	public static Texture[] tileTextures;

	// // / LOADING - CREATION
	public GameplayScreen(final GameAmn gam) {
		Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);

		game = gam;

		// fonts

		F_debug = Assets.F_debug;
		F_history = new BitmapFont(Gdx.files.internal("fonts/trebuchet.fnt"),
				Gdx.files.internal("fonts/trebuchet.png"), false);
		F_history.setScale(0.5f);
		F_history.setColor(Color.CYAN);
		F_history2 = new BitmapFont(Gdx.files.internal("fonts/trebuchet.fnt"),
				Gdx.files.internal("fonts/trebuchet.png"), false);
		F_history2.setScale(0.5f);
		F_history2.setColor(Color.ORANGE);
		F_buttons = Assets.F_buttons;
		F_buttonsHighlight = Assets.F_buttonsHighlight;
		F_buttonsOutline0 = Assets.F_buttonsOutline0;
		F_buttonsOutline1 = Assets.F_buttonsOutline1;
		F_buttonsOutline2 = Assets.F_buttonsOutline2;
		F_buttonsOutline3 = Assets.F_buttonsOutline3;

		modelBatch = new ModelBatch();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Camera init

		camera = new PerspectiveCamera(67, w, h);
		camera.position.set(1.3f, 0, 1.3f);
		camera.lookAt(0, 0, 0);
		camera.near = 0.1f;
		camera.far = 100f;

		camera.update();

		// controls

		multiplexer = new InputMultiplexer();
		camController = new CameraInputController(camera);
		multiplexer.addProcessor(new AmniInputProcessor(gam));
		multiplexer.addProcessor(camController);

		((AmniInputProcessor) multiplexer.getProcessors().get(0)).setCamera(
				camera, (CameraInputController) multiplexer.getProcessors()
						.get(1));

		// Lighting init
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, 1f));

		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));
		environment.add(new DirectionalLight().set(-0.8f, -0.8f, -0.8f, 1f,
				0.8f, 0.2f));

		// OTHER ASSETS

		// numbered tiles creation
		tileTextures = new Texture[80];

		for (int a = 0; a < 80; a++) {
			tempMap = new Pixmap(Gdx.files.internal("data/texCount22.png"));
			pixmapDrawString(tempMap, a + "", F_debug, 20, 20, 14);
			tileTextures[a] = new Texture(tempMap);
		}

		// proceed
		loading = true;

	}

	public void doneLoading() {

		// DEBUG
		if (CONSOLE_ENABLED) {
			createConsole();
		}

		// Game data system
		instance = new GameInstance(this);
		instance.initBattlefield();

		Geometry ico = new Geometry();
		ModelBuilder mb = new ModelBuilder();
		mb.begin();

		// Create mesh nodes
		for (int a = 0; a < ico.vertices.length; a = a + 9) {

			MeshPartBuilder builder = mb.part("v" + a, GL20.GL_TRIANGLES,
					Usage.Position | Usage.TextureCoordinates, new Material(
							ColorAttribute.createDiffuse(Color.LIGHT_GRAY)));

			builder.setUVRange(0, 0, 1, 1);
			Vector3 v1 = new Vector3(ico.vertices[a], ico.vertices[a + 1],
					ico.vertices[a + 2]);
			Vector3 v2 = new Vector3(ico.vertices[a + 3], ico.vertices[a + 4],
					ico.vertices[a + 5]);
			Vector3 v3 = new Vector3(ico.vertices[a + 6], ico.vertices[a + 7],
					ico.vertices[a + 8]);

			builder.triangle(v1, v2, v3);

			final short index1 = builder.vertex(v1, null, null, new Vector2(0,
					1));
			final short index2 = builder.vertex(v2, null, null, new Vector2(0,
					0));
			final short index3 = builder.vertex(v3, null, null, new Vector2(1,
					0));
			builder.index(index1, index2, index3);
			System.out.println(builder.getAttributes().size() + " | "
					+ builder.getAttributes());
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
				if (!GRADUAL_COLORING) {
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
				} else {
					col = Color.ORANGE.cpy();
					if (GameAmn.PRINT_STATUS) {
						System.out.println(a + "....");
						System.out.println("orig: " + col);
					}
					if (GameAmn.PRINT_STATUS) {
						System.out.println(a + " / 80");
						System.out.println((float) a / 80 + " | " + (float) a
								/ 80 + " | " + (float) a / 80 + " | " + 1f);
					}
					col.set(0, 0, (float) a / 80, 1f);
					if (GameAmn.PRINT_STATUS) {
						System.out.println("new: " + col);
					}
				}
				mate.set(ColorAttribute.createDiffuse(col));
				if (GameAmn.PRINT_STATUS) {
					System.out.println("Assigning " + a);
				}
			} catch (Exception e) {
				System.out.println("Can't assign color to mat number " + a);
			}

		}

		loading = false;

	}

	// // LOADING - DEBUG
	// / console
	void createConsole() {
		for (int a = 0; a < console_history.length; a++) {
			console_history[a] = "<blank>";
		}
		stage = new Stage();
		spriteBatch = new SpriteBatch();
		console_texture = new Texture(Gdx.files.internal("data/console.png"));
		console_sprite = new Sprite(console_texture);

		float console_scale = 1f;
		console_sprite.setScale(console_scale);
		console_sprite.setBounds(
				Gdx.graphics.getWidth()
						- (console_sprite.getWidth() * console_scale),
				Gdx.graphics.getHeight()
						- (console_sprite.getHeight() * console_scale),
				(console_scale * console_sprite.getWidth()),
				(console_scale * console_sprite.getHeight()));

		console_textfield_style = new TextFieldStyle();

		console_textfield_style.font = F_debug;
		console_textfield_style.fontColor = Color.WHITE;
		console_textfield = new TextField("Enter commands...",
				console_textfield_style);

		console_textfield.setBounds(console_sprite.getX() + 10,
				console_sprite.getY() - 5, 250, 50);
		TextFieldListener console_textfield_listener = new TextFieldListener() {

			@Override
			public void keyTyped(TextField textField, char key) {

				switch (key) {
				case ';':
					textField.setText("");
					break;
				case '\\':
					stage.setKeyboardFocus(null);
					AmniInputProcessor.consoleActive = false;
					textField.setText("Enter commands...");
				case '\n':
				case '\r':
					GameAmn.sendConsole(textField.getText());
					textField.setText("");
					break;

				}
			}

		};

		console_textfield.setTextFieldListener(console_textfield_listener);
		AmniInputProcessor.setStage(stage, console_textfield);
		stage.unfocusAll();
		stage.addActor(console_textfield);

		multiplexer.addProcessor(stage);

		AmniInputProcessor.consoleEnabled = true;

	}

	// ----- GAME LOOPS-----

	// updates
	public void updateGame() {
		// orbitEverything();
		try {
			instance.updateBattlefield();

		} catch (Exception e) {
			GameAmn.error(e.getStackTrace());
		}

	}

	// render
	@Override
	public void render(float delta) {

		if (loading) {
			doneLoading();
		}

		// Clear the screen
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Update based on input
		if (camController != null)
			camController.update();

		((AmniInputProcessor) multiplexer.getProcessors().get(0)).update();
		updateGame();

		// 3D Render
		modelBatch.begin(camera);
		modelBatch.render(instances, environment);
		modelBatch.end();

		// 2D Render

		// Console
		if (CONSOLE_ENABLED && !loading) {
			try {
				spriteBatch.begin();
				console_sprite.draw(spriteBatch);
				F_debug.draw(spriteBatch, "X: " + Gdx.input.getX() + ", Y: "
						+ (Gdx.input.getY()) + ".", 5, 20);

				// history
				int a = 0, step = 15;

				for (String entry : console_history) {
					if (a % 2 == 1) {
						F_history.draw(spriteBatch, entry,
								console_sprite.getX() + 10,
								console_sprite.getY() + 50 + (step * a));
					} else {
						F_history2.draw(spriteBatch, ">  " + entry,
								console_sprite.getX() + 30,
								console_sprite.getY() + 50 + (step * a));
					}
					a++;
				}

				spriteBatch.end();
				stage.act();
				stage.draw();

			} catch (Exception e) {

			}

		}

	}

	// UTILITY
	// Draw text to pixmap
	public void pixmapDrawString(Pixmap source, String text, BitmapFont font,
			int x, int y, int spacing) {
		BitmapFontData data = font.getData();

		for (int a = 0; a < text.length(); a++) {
			Glyph tempGlyph = data.getGlyph(text.charAt(a));

			Pixmap fontPixmap = new Pixmap(
					Gdx.files.internal(data.imagePaths[0]));
			source.drawPixmap(fontPixmap, x + (a * spacing), y, tempGlyph.srcX,
					tempGlyph.srcY, tempGlyph.width, tempGlyph.height);
		}

		if (GameAmn.PRINT_STATUS) {
			System.out.println("Drawing, lol");
		}
		// Gdx.files.internal(data.getImagePath(tempGlyph.id))

	}

	// UNUSED METHODS

	/* unused methods */
	@Override
	public void resize(int width, int height) {

		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		this.width = width;
		this.height = height;

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
		// TODO properly dispose of everything
		texture.dispose();
	}

}