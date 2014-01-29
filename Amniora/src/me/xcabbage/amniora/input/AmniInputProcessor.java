package me.xcabbage.amniora.input;

import me.xcabbage.amniora.GameAmn;
import me.xcabbage.amniora.screen.GameplayScreen;
import me.xcabbage.amniora.screen.MainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;

public class AmniInputProcessor implements InputProcessor {
	GameAmn game;
	public MainMenuScreen activeScreen;

	int buttonPixels[][] = { { 116, 290, 236, 320, }, { 160, 370, 302, 408, },
			{ 236, 448, 436, 482, }, { 370, 518, 468, 556 }

	};

	public AmniInputProcessor(final GameAmn gam) {
		game = gam;

	}

	@Override
	public boolean keyDown(int keycode) {

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

		if (game.currentScreen == 0) {
			for (int a = 0; a < 4; a++) {
				if (hoveringOverButton(a, screenX, screenY)) {

					switch (a) {
					case 0:
						System.out.println("Initializing Play");

						game.setScreen(new GameplayScreen(game));
						game.currentScreen = 1;
						game.mainMenuScreen.dispose();

						break;

					case 1:
						System.out.println("Opening stats");
						break;
					case 2:
						System.out.println("Initializing Play");
						break;
					case 3:
						System.out.println();
						System.out.println("Shutting down.");
						Gdx.app.exit();
						break;
					default:
						System.out.println("Unknown button pressed. Wth.");

					}
					return true;
				}
			}
			System.out.println("No button was pressed.");
		}
		return false;
	}

	public boolean hoveringOverButton(int button, int screenX, int screenY) {
		if (buttonPixels[button][0] <= screenX
				&& buttonPixels[button][2] >= screenX)
			if (buttonPixels[button][1] <= screenY
					&& buttonPixels[button][3] >= screenY) {

				return true;
			}
		return false;
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

		return false;
	}

	public void resolveButtons(int screenX, int screenY) {

		try {
			if (game.currentScreen == 0) {
				for (int a = 0; a < 4; a++) {
					if (hoveringOverButton(a, screenX, screenY)
							|| a == activeScreen.activeButton) {

						if (game.mainMenuScreen.buttonHighlight[a] <= game.mainMenuScreen.MAX_ALPHA - 0.03f) {
							game.mainMenuScreen.buttonHighlight[a] = game.mainMenuScreen.buttonHighlight[a] + 0.03f;

						} else
							game.mainMenuScreen.buttonHighlight[a] = game.mainMenuScreen.MAX_ALPHA;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean scrolled(int amount) {
		try {

			if (activeScreen.activeButton == -1) {
				activeScreen.activeButton = 0;
				return true;
			} else if (activeScreen.activeButton == activeScreen.buttonCount - 1
					&& amount == 1)
				activeScreen.activeButton = 0;
			else if (activeScreen.activeButton == 0 && amount == -1)
				activeScreen.activeButton = activeScreen.buttonCount - 1;
			else {
				activeScreen.activeButton = activeScreen.activeButton + amount;
				return true;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String toString() {
		return "sup";
	}
}
