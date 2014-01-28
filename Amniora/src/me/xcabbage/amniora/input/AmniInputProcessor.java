package me.xcabbage.amniora.input;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.screen.GameplayScreen;

import com.badlogic.gdx.InputProcessor;

public class AmniInputProcessor implements InputProcessor {
	GameAmn game;

	public AmniInputProcessor(final GameAmn gam) {
		game = gam;

	}

	@Override
	public boolean keyDown(int keycode) {
		System.out.println(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("Clicked at (" + screenX + "," + screenY + ").");

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public String toString() {
		return "sup";
	}
}
