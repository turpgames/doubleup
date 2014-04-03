package com.turpgames.doubleupex.objects;

import com.turpgames.doubleupex.utils.DoubleUpSettings;
import com.turpgames.doubleupex.utils.DoubleUpStateManager;
import com.turpgames.doubleupex.utils.R;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public abstract class Table implements IDrawable {
	public final static float size = 512f;

	protected final int matrixSize;
	protected final Row[] rows;

	protected int score;
	private final ResetButton resetButton;

	private final ScoreArea scoreArea;
	private final ScoreArea hiscoreArea;
	private final ScoreArea hiscoreBlockArea;

	private final TableState state;
	private boolean addRandonNumberAfterMove;
	private int targetNumber;
	private boolean completed;

	public Table(int size) {
		GlobalContext.matrixSize = size;
		matrixSize = size;

		state = new TableState();

		this.rows = new Row[matrixSize];

		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row(this, i);
		}

		float x = (Game.getVirtualWidth() - Table.size) / 2;
		float y = (Game.getVirtualHeight() - Table.size) / 2;

		resetButton = new ResetButton(this);
		resetButton.deactivate();

		scoreArea = new ScoreArea("Score");
		scoreArea.setLocation(x + 4, y - 75);

		hiscoreArea = new ScoreArea("Hi");
		hiscoreArea.setLocation(
				(Game.getVirtualWidth() - hiscoreArea.getWidth()) / 2, y - 75);

		hiscoreBlockArea = new ScoreArea("Max");
		hiscoreBlockArea.setLocation(
				Game.getVirtualWidth() - hiscoreBlockArea.getWidth() - 4,
				y - 75);
	}

	public int getScore() {
		return score;
	}

	public int getMatrixSize() {
		return matrixSize;
	}

	public void deactivate() {
		resetButton.deactivate();
	}

	public void activate() {
		resetButton.activate();
	}

	public boolean isAddRandonNumberAfterMove() {
		return addRandonNumberAfterMove;
	}

	public void setAddRandonNumberAfterMove(boolean addRandonNumberAfterMove) {
		this.addRandonNumberAfterMove = addRandonNumberAfterMove;
	}

	public int getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(int targetNumber) {
		this.targetNumber = targetNumber;
	}

	public void init() {
		score = 0;
		completed = false;
		GlobalContext.reset(this);

		for (int i = 0; i < rows.length; i++) {
			rows[i].empty();
		}

		initLevel();

		// setRandomCell();
		// setRandomCell();

		// for (int i = 0; i < matrixSize * matrixSize - 4;i ++)
		// setRandomCell((int)Math.pow(2, i));

		initScoreTexts();
	}

	protected abstract void initLevel();

	protected void initScoreTexts() {
		updateScoreText();
		hiscoreArea.setScore(DoubleUpSettings.getHiScore());
		hiscoreBlockArea.setScore(DoubleUpSettings.getMaxNumber());
	}

	private int rand(int max) {
		return Util.Random.randInt(max);
	}

	private boolean hasEmptyCell() {
		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (cell.isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	private void setRandomCell() {
		setRandomCell(rand(5) == 1 ? 2 : 1);
	}

	protected void setCellValue(int rowIndex, int colIndex, int value) {
		Tile tile = new Tile();

		Cell cell = getCell(rowIndex, colIndex);
		tile.popInCell(cell, value);

		cell.setTile(tile);
		Tile.executeCommands();
	}

	protected void setAsBrick(int rowIndex, int colIndex) {
		Tile tile = new Tile();
		tile.setBrick(true);

		Cell cell = getCell(rowIndex, colIndex);
		tile.fitToCell(cell);

		cell.setTile(tile);
	}

	private void setRandomCell(int value) {
		if (!addRandonNumberAfterMove)
			return;
		Cell cell;
		do {
			cell = getCell(rand(matrixSize), rand(matrixSize));
		} while (!cell.isEmpty());

		final Tile tile = new Tile();
		tile.popInCell(cell, value);

		cell.setTile(tile);
		Tile.executeCommands();
	}

	public Cell getCell(int rowIndex, int colIndex) {
		return rows[rowIndex].getCell(colIndex);
	}

	public int getCellValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}

	public void move(MoveDirection direction) {
		if (completed)
			return;
		GlobalContext.resetMove();

		switch (direction) {
		case Down:
			moveDown();
			break;
		case Left:
			moveLeft();
			break;
		case Right:
			moveRight();
			break;
		case Up:
			moveUp();
			break;
		}

		Tile.executeCommands();

		moveEnd();
	}

	void moveEnd() {
		if (!GlobalContext.hasMove) {
			DoubleUpAudio.playNoMoveSound();
			return;
		}

		if (!addRandonNumberAfterMove) {
			if (getActiveTileCount() == 1 && getMaxNumber() == targetNumber) {
				Dialog dlg = new Dialog();
				dlg.addButton("ok", "Ok");
				dlg.open("Congratulations!");
				completed = true;
			}
			return;
		}

		if (GlobalContext.moveScore > 0) {
			this.score += GlobalContext.moveScore;
			updateScoreText();
		} else {
			DoubleUpAudio.playNoScoreSound();
		}

		setRandomCell();

		if (hasMove()) {
			DoubleUpStateManager.saveTableState(this);
		} else {
			DoubleUpStateManager.deleteTableState(this);
			endGame();
		}
	}

	private void endGame() {
		GlobalContext.finalScore = this.score;
		GlobalContext.max = 0;

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (cell.getValue() > GlobalContext.max)
					GlobalContext.max = cell.getValue();
				cell.getTile().setValue(0);
				cell.empty();
			}
		}

		DoubleUpAudio.playGameOverSound();
		Game.getInputManager().activate();

		ScreenManager.instance.switchTo(R.screens.result, false);
	}

	private int getMaxNumber() {
		int max = 0;
		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (!cell.isEmpty() && cell.getValue() > max) {
					max = cell.getValue();
				}
			}
		}
		return max;
	}

	private int getActiveTileCount() {
		int count = 0;
		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				if (!cell.isEmpty() && cell.getValue() > 0) {
					count++;
				}
			}
		}
		return count;
	}

	private void moveRight() {
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = matrixSize - 1; colIndex >= 0; colIndex--) {
				getCell(rowIndex, colIndex).moveRight();
			}
		}
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = matrixSize - 1; colIndex >= 0; colIndex--) {
				getCell(rowIndex, colIndex).addRight();
			}
		}
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = matrixSize - 1; colIndex >= 0; colIndex--) {
				getCell(rowIndex, colIndex).moveRight();
			}
		}
	}

	private void moveLeft() {
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
				getCell(rowIndex, colIndex).moveLeft();
			}
		}
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
				getCell(rowIndex, colIndex).addLeft();
			}
		}
		for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
			for (int colIndex = 0; colIndex < matrixSize; colIndex++) {
				getCell(rowIndex, colIndex).moveLeft();
			}
		}
	}

	private void moveDown() {
		for (int j = 0; j < matrixSize; j++) {
			for (int i = matrixSize - 1; i >= 0; i--) {
				getCell(i, j).moveDown();
			}
		}
		for (int j = 0; j < matrixSize; j++) {
			for (int i = matrixSize - 1; i >= 0; i--) {
				getCell(i, j).addDown();
			}
		}
		for (int j = 0; j < matrixSize; j++) {
			for (int i = matrixSize - 1; i >= 0; i--) {
				getCell(i, j).moveDown();
			}
		}
	}

	private void moveUp() {
		for (int j = 0; j < matrixSize; j++) {
			for (int i = 0; i < matrixSize; i++) {
				getCell(i, j).moveUp();
			}
		}
		for (int j = 0; j < matrixSize; j++) {
			for (int i = 0; i < matrixSize; i++) {
				getCell(i, j).addUp();
			}
		}
		for (int j = 0; j < matrixSize; j++) {
			for (int i = 0; i < matrixSize; i++) {
				getCell(i, j).moveUp();
			}
		}
	}

	private boolean hasMove() {
		if (hasEmptyCell())
			return true;

		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < rows.length; j++) {
				if ((i > 0 && getCellValue(i, j) == getCellValue(i - 1, j))
						|| (i < rows.length - 1 && getCellValue(i, j) == getCellValue(
								i + 1, j))
						|| (j < rows.length - 1 && getCellValue(i, j) == getCellValue(
								i, j + 1))
						|| (j > 0 && getCellValue(i, j) == getCellValue(i,
								j - 1)))
					return true;
			}
		}

		return false;
	}

	private void updateScoreText() {
		scoreArea.setScore(score);
	}

	@Override
	public void draw() {
		resetButton.draw();

		// scoreArea.draw();
		// hiscoreArea.draw();
		// hiscoreBlockArea.draw();

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				cell.draw();
			}
		}
	}

	public TableState getState() {
		RowState[] rowStates = new RowState[rows.length];
		for (int i = 0; i < rows.length; i++)
			rowStates[i] = rows[i].getState();

		state.setRows(rowStates);
		state.setMatrixSize(matrixSize);
		state.setScore(score);

		return state;
	}

	public void loadState(TableState state) {
		score = state.getScore();

		for (int i = 0; i < rows.length; i++)
			rows[i].loadState(state.getRows()[i]);

		initScoreTexts();
	}
}
