package com.turpgames.doubleupex.objects2;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.doubleupex.objects.DoubleUpAudio;
import com.turpgames.doubleupex.objects.GlobalContext;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.Screen;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.utils.Util;

public class GridController implements IView, IInputListener {
	private final Grid grid;
	private final Screen screen;
	private final ResetButton resetButton;

	public GridController(Screen screen) {
		this.screen = screen;
		this.grid = new Grid();
		this.resetButton = new ResetButton(this);
	}

	public void init() {
		grid.init(6);
		grid.putBrick(1, 2);
		grid.putBrick(2, 1);
		grid.putBrick(3, 4);
		grid.putBrick(4, 3);
		grid.putBrick(1, 4);
		grid.putBrick(4, 1);
		grid.putTile(0, 0, 1);
		grid.putTile(5, 5, 1);
	}

	private void putRandom() {
		Cell cell;

		do {
			cell = grid.getCell(Util.Random.randInt(6), Util.Random.randInt(6));
		} while (!cell.isEmpty());

		grid.putTile(cell.getRow().getRowIndex(), cell.getColIndex(), Util.Random.randInt(5) == 0 ? 2 : 1);
	}

	private void moveDown() {
		beforeMove();
		grid.moveDown();
		afterMove();
	}

	private void moveUp() {
		beforeMove();
		grid.moveUp();
		afterMove();
	}

	private void moveLeft() {
		beforeMove();
		grid.moveLeft();
		afterMove();
	}

	private void moveRight() {
		beforeMove();
		grid.moveRight();
		afterMove();
	}

	private void beforeMove() {
		GlobalContext.hasMove = false;
		GlobalContext.moveScore = 0;
	}

	protected void afterMove() {
		if (!GlobalContext.hasMove) {
			DoubleUpAudio.playNoMoveSound();
			return;
		}

		if (GlobalContext.moveScore > 0) {

		} else {
			DoubleUpAudio.playNoScoreSound();
		}

		putRandom();

		if (grid.hasMove()) {
			
		} else {
			init();
		}
	}

	@Override
	public String getId() {
		return "mode1";
	}

	@Override
	public void activate() {
		screen.registerDrawable(grid, Game.LAYER_GAME);
		screen.registerDrawable(resetButton, Game.LAYER_GAME);
		screen.registerInputListener(resetButton);
		screen.registerInputListener(this);
	}

	@Override
	public boolean deactivate() {
		screen.unregisterDrawable(grid);
		screen.unregisterDrawable(resetButton);
		screen.unregisterInputListener(resetButton);
		screen.unregisterInputListener(this);
		return true;
	}

	@Override
	public void draw() {
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.DOWN) {
			moveDown();
		}
		if (keycode == Keys.UP) {
			moveUp();
		}
		if (keycode == Keys.LEFT) {
			moveLeft();
		}
		if (keycode == Keys.RIGHT) {
			moveRight();
		}
		return true;
	}

	@Override
	public boolean fling(float vx, float vy, int button) {
		if (Math.abs(vx) > Math.abs(vy)) {
			if (vx > 0)
				moveRight();
			else
				moveLeft();
		}
		if (Math.abs(vy) > Math.abs(vx)) {
			if (vy > 0)
				moveDown();
			else
				moveUp();
		}
		return true;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(float x, float y, int pointer) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float dx, float dy) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector p1Start, Vector p2Start, Vector p1End, Vector p2End) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(float x, float y) {
		return false;
	}

	@Override
	public boolean scrolled(float amount) {
		return false;
	}
}
