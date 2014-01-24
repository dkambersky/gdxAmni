/** The MainMenuScreen.java class responsible for 
 *
 * @author xCabbage [github.com/xcabbage]
 *
 * @info for the Amniora project [github.com/xcabbage/amniora]
 *      created 1. 10. 2013 10:08:59
 */

package me.xcabbage.amniora.screen;

import java.awt.Font;

import me.xcabbage.amniora.GameAmn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * @author David
 * 
 */
public class MainMenuScreen implements Screen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	BitmapFont font;
	GameAmn game;

	public MainMenuScreen(final GameAmn gam) {
		game = gam;

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		font = new BitmapFont();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		camera.setToOrtho(false, w, h);
		camera.update();
		texture = new Texture(Gdx.files.internal("menuSmaller.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		sprite = new Sprite(texture);
		sprite.setScale(.5f);
		sprite.setOrigin(0, 0);
		sprite.setPosition(0, -330);

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		sprite.draw(batch);
		font.draw(batch, "sup", 150, 50);

		batch.end();

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			game.setScreen(new GameplayScreen(game));
		dispose();

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
		texture.dispose();
		batch.dispose();
		font.dispose();

	}

}
