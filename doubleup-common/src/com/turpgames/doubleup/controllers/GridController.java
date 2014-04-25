package com.turpgames.doubleup.controllers;

import com.badlogic.gdx.Input.Keys;
import com.turpgames.doubleup.components.ResetButton;
import com.turpgames.doubleup.entities.Cell;
import com.turpgames.doubleup.entities.Grid;
import com.turpgames.doubleup.view.IDoubleUpView;
import com.turpgames.framework.v0.IInputListener;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Vector;
import com.turpgames.utils.Util;

public abstract class GridController implements IInputListener {
	private final static float minMove = Game.scale(30f);
	
	protected final Grid grid;
	protected final IDoubleUpView view;
	protected final ResetButton resetButton;

	public GridController(IDoubleUpView view) {
		this.view = view;
		this.grid = new Grid();
		this.resetButton = new ResetButton(this);
		this.resetButton.deactivate();

		view.registerDrawable(grid, Game.LAYER_GAME);
		view.registerInputListener(this);
		view.registerDrawable(resetButton, Game.LAYER_GAME);
	}

	public void reset() {
		grid.reset();
		init();
	}

	public void activate() {
		resetButton.activate();
	}

	public void deactivate() {
		resetButton.deactivate();
	}

	public abstract void init();

	protected int rand() {
		return Util.Random.randInt(grid.getMatrixSize());
	}

	protected void putRandom() {
		putRandom(Util.Random.randInt(10) == 0 ? 2 : 1);
	}

	protected void putRandom(int value) {
		Cell cell;

		do {
			cell = grid.getCell(rand(), rand());
		} while (!cell.isEmpty());

		grid.putTile(cell.getRow().getRowIndex(), cell.getColIndex(), value);
	}

	private void moveDown() {
		if (!beforeMove())
			return;
		grid.moveDown();
		afterMove();
	}

	private void moveUp() {
		if (!beforeMove())
			return;
		grid.moveUp();
		afterMove();
	}

	private void moveLeft() {
		if (!beforeMove())
			return;
		grid.moveLeft();
		afterMove();
	}

	private void moveRight() {
		if (!beforeMove())
			return;
		grid.moveRight();
		afterMove();
	}

	protected abstract boolean beforeMove();

	protected abstract void afterMove();

	private boolean isTouched;
	private float dx;
	private float dy;

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		if (y < Game.getScreenHeight() * 0.8f) {
			isTouched = true;
			dx = x;
			dy = y;
		}
		return false;
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, int button) {
		if (isTouched) {
			isTouched = false;
			
			dx = x - dx;
			dy = y - dy;

			if (Math.abs(dx) < minMove && Math.abs(dy) < minMove)
				return false;

			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0)
					moveRight();
				else
					moveLeft();
			}
			if (Math.abs(dy) > Math.abs(dx)) {
				if (dy > 0)
					moveUp();
				else
					moveDown();
			}
		}
		return false;
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
