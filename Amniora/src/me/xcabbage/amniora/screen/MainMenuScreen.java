/** The MainMenuScreen.java class responsible for displaying and handling the main menu 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 1. 10. 2013 10:08:59
 */

package me.xcabbage.amniora.screen;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.assets.Assets;
import me.xcabbage.amniora.input.AmniInputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;

/**
 * @author David
 * 
 */
public class MainMenuScreen implements Screen {
	public final float MAX_ALPHA = .38f;
	public final int buttonCount = 4;

	public int activeButton = -1;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite S_background, S_planet;
	private TextureRegion R_background, R_planet;
	BitmapFont F_debug, F_buttons, F_buttonsHighlight, F_buttonsOutline0,
			F_buttonsOutline1, F_buttonsOutline2, F_buttonsOutline3;
	public Shader shader;
	GameAmn game;
	private boolean fontHighlight;
	public float[] buttonHighlight = { 0f, 0f, 0f, 0f };

	public MainMenuScreen(final GameAmn gam) {
		// Initialization
		game = gam;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		System.out.println("setting current screen to 1");
		game.currentScreen = 1;
		// Camera & Batch work
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		camera.setToOrtho(false, w, h);
		camera.update();

		// Initialize Assets.java and load its contents
		Assets.init();

		// load fonts
		F_debug = Assets.F_debug;
		F_buttons = Assets.F_buttons;
		F_buttonsHighlight = Assets.F_buttonsHighlight;
		F_buttonsOutline0 = Assets.F_buttonsOutline0;
		F_buttonsOutline1 = Assets.F_buttonsOutline1;
		F_buttonsOutline2 = Assets.F_buttonsOutline2;
		F_buttonsOutline3 = Assets.F_buttonsOutline3;

		// load textures, spritesheets, sprites

		R_background = Assets.R_background;
		R_planet = Assets.R_planet;

		S_background = Assets.S_background;
		S_planet = Assets.S_planet;

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		S_background.draw(batch);
		S_planet.draw(batch);
		F_debug.draw(batch,
				"X: " + Gdx.input.getX() + ", Y: " + (Gdx.input.getY()) + ".",
				5, 20);

		F_buttonsOutline3.setColor(1, 1, 1, buttonHighlight[0]);
		F_buttonsOutline3.draw(batch, "Play", 124, 397);
		F_buttonsOutline1.setColor(1, 1, 1, buttonHighlight[1]);
		F_buttonsOutline1.draw(batch, "Stats", 162, 310);
		F_buttonsOutline2.setColor(1, 1, 1, buttonHighlight[2]);
		F_buttonsOutline2.draw(batch, "Settings", 238, 240);
		F_buttonsOutline0.setColor(1, 1, 1, buttonHighlight[3]);
		F_buttonsOutline0.draw(batch, "Exit", 376, 166);

		F_buttons.draw(batch, "Play", 124, 396);
		F_buttons.draw(batch, "Stats", 162, 310);
		F_buttons.draw(batch, "Settings", 238, 240);
		F_buttons.draw(batch, "Exit", 376, 166);

		// Button highlight handling

		game.standardProcessor.resolveButtons(Gdx.input.getX(),
				Gdx.input.getY());
		for (int a = 0; a < 4; a++)
			if (game.mainMenuScreen.buttonHighlight[a] >= 0.005f)

				game.mainMenuScreen.buttonHighlight[a] = game.mainMenuScreen.buttonHighlight[a] - 0.005f;
			else
				game.mainMenuScreen.buttonHighlight[a] = 0f;

		batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {

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

	}

}
