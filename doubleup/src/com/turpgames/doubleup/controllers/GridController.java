package com.turpgames.doubleup.controllers;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.doubleup.components.ResetButton;
import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.utils.DoubleUpAudio;
import com.turpgames.doubleup.utils.GlobalContext;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.utils.Util;

public abstract class GridController implements IView, IInputListener {
	protected final Grid grid;
	protected final IDoubleUpView view;
	protected final ResetButton resetButton;

	public GridController(IDoubleUpView view) {
		this.view = view;
		this.grid = new Grid();
		this.resetButton = new ResetButton(this);
	}

	public void reset() {
		grid.reset();
	}

	public abstract void init();

	// {
	// grid.init(6);
	// grid.putBrick(1, 2);
	// grid.putBrick(2, 1);
	// grid.putBrick(3, 4);
	// grid.putBrick(4, 3);
	// grid.putBrick(1, 4);
	// grid.putBrick(4, 1);
	// grid.putTile(0, 0, 1);
	// grid.putTile(5, 5, 1);
	// }

	protected int rand() {
		return Util.Random.randInt(grid.getMatrixSize());
	}

	protected void putRandom() {
		Cell cell;

		do {
			cell = grid.getCell(rand(), rand());
		} while (!cell.isEmpty());

		grid.putTile(
				cell.getRow().getRowIndex(),
				cell.getColIndex(),
				Util.Random.randInt(5) == 0 ? 2 : 1);
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

	protected void beforeMove() {
		GlobalContext.hasMove = false;
		GlobalContext.moveScore = 0;
	}

	protected void afterMove() {
		if (!GlobalContext.hasMove) {
			DoubleUpAudio.playNoMoveSound();
			return;
		}

		if (GlobalContext.moveScore > 0) {
			// TODO:
		} else {
			DoubleUpAudio.playNoScoreSound();
		}

		putRandom();

		if (grid.hasMove()) {
			// TODO:
		} else {
			onGameOver();
		}
	}

	protected void onGameOver() {
		// TODO:
	}

	@Override
	public void activate() {
		view.registerDrawable(grid, Game.LAYER_GAME);
		view.registerDrawable(resetButton, Game.LAYER_GAME);
		view.registerInputListener(resetButton);
		view.registerInputListener(this);
	}

	@Override
	public boolean deactivate() {
		view.unregisterDrawable(grid);
		view.unregisterDrawable(resetButton);
		view.unregisterInputListener(resetButton);
		view.unregisterInputListener(this);
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
	public boolean pinch(Vector p1Start, Vector p2Start, Vector p1End,
			Vector p2End) {
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
