package me.xcabbage.amniora;

import me.xcabbage.amniora.input.AmniInputProcessor;
import me.xcabbage.amniora.screen.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

public class GameAmn extends Game {

	MainMenuScreen mainMenuScreen;
	GameplayScreen gameplayScreen;

	@Override
	public void create() {

		// Input processing

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new AmniInputProcessor(this));

		Gdx.input.setInputProcessor(multiplexer);

		// Screen creation
		mainMenuScreen = new MainMenuScreen(this);
		gameplayScreen = new GameplayScreen(this);
		setScreen(mainMenuScreen);

	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		super.render();
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
