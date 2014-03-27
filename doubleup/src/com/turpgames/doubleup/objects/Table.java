package com.turpgames.doubleup.objects;

import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public class Table implements IDrawable {
	public final static int matrixSize = 6;
	public final static float size = 512f;

	private final Row[] rows;

	private long score;
	private final ResetButton resetButton;

	private final ScoreArea scoreArea;
	private final ScoreArea hiscoreArea;
	private final ScoreArea hiscoreBlockArea;

	public Table() {
		this.rows = new Row[matrixSize];

		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row(this, i);
		}

		setRandomCell();
		setRandomCell();

//		for (int i = 0; i < matrixSize * matrixSize - 2;i ++)
//			setRandomCell((long)Math.pow(2, i));

		float x = (Game.getVirtualWidth() - Table.size) / 2;
		float y = (Game.getVirtualHeight() - Table.size) / 2;

		resetButton = new ResetButton(this);

		scoreArea = new ScoreArea("Score");
		scoreArea.setLocation(x + 4, y + size + 5);
		updateScoreText();

		hiscoreArea = new ScoreArea("Hi");
		hiscoreArea.setLocation(x + 4, y - 55);
		
		hiscoreBlockArea = new ScoreArea("Max");
		hiscoreBlockArea.setLocation(x + 4, y - 115);

		GlobalContext.table = this;
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
//		setRandomCell(rand(5) == 1 ? 2 : 1);							// klasik

		setRandomCell((int)Math.pow(2, rand(GlobalContext.maxPower))); 	// cash machine
	}

	private void setRandomCell(int value) {
		Cell cell;
		do {
			cell = getCell(rand(matrixSize), rand(matrixSize));
		} while (!cell.isEmpty());

		final Tile tile = new Tile();
		tile.setValue(value);
		tile.popInCell(cell);

		cell.setTile(tile);
		Tile.executeCommands();
	}

	Cell getCell(int rowIndex, int colIndex) {
		return rows[rowIndex].getCell(colIndex);
	}

	long getCellValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}

	public void move(MoveDirection direction) {		
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

		if (GlobalContext.moveScore > 0) {
			this.score += GlobalContext.moveScore;
			updateScoreText();
		}
		else {
			DoubleUpAudio.playNoScoreSound();
		}

		if (hasEmptyCell()) {
			setRandomCell();

			if (!hasMove()) {
				GlobalContext.finalScore = this.score;
				GlobalContext.max = 0;
				for (Row row : rows) {
					for (Cell cell : row.getCells()) {
						if (cell.getValue() > GlobalContext.max)
							GlobalContext.max = cell.getValue();
					}
				}

				DoubleUpAudio.playGameOverSound();
				Game.getInputManager().activate();
				
				ScreenManager.instance.switchTo(R.screens.result, false);
			}
		}
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
				if ((i > 0 && getCellValue(i, j) == getCellValue(i - 1, j)) ||
						(i < rows.length - 1 && getCellValue(i, j) == getCellValue(i + 1, j)) ||
						(j < rows.length - 1 && getCellValue(i, j) == getCellValue(i, j + 1)) ||
						(j > 0 && getCellValue(i, j) == getCellValue(i, j - 1)))
					return true;
			}
		}

		return false;
	}

	public void reset() {
		for (int i = 0; i < rows.length; i++) {
			rows[i].reset();
		}

		setRandomCell();
		setRandomCell();

		score = 0;
		updateScoreText();
		hiscoreArea.setScore(DoubleUpSettings.getHiScore());
		hiscoreBlockArea.setScore(DoubleUpSettings.getMaxNumber());
	}

	private void updateScoreText() {
		scoreArea.setScore(score);
	}

	@Override
	public void draw() {
		resetButton.draw();
		scoreArea.draw();
		hiscoreArea.draw();
		hiscoreBlockArea.draw();

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				cell.draw();
			}
		}
	}
}
