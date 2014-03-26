package com.turpgames.doubleup.objects;

import com.turpgames.doubleup.utils.DoubleUpSettings;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.ScreenManager;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.utils.Util;

public class Table extends GameObject {
	public final static int matrixSize = 4;
	public final static float size = 512f;

	private final Row[] rows;

	private int score;
	private Text scoreText;
	private Text hiscoreText;
	private Text hiscoreBlockText;
	private final Dialog gameOverDialog;
	private final ResetButton resetButton;

	public Table() {
		this.rows = new Row[4];

		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row(this, i);
		}

//		setRandomCell();
//		setRandomCell();

		 setRandomCell(1);
		 setRandomCell(2);
		 setRandomCell(4);
		 setRandomCell(8);
		 setRandomCell(16);
		 setRandomCell(32);
		 setRandomCell(64);
		 setRandomCell(128);
		 setRandomCell(256);
		 setRandomCell(512);
		 setRandomCell(1024);
		 setRandomCell(2048);
		 setRandomCell(4096);
		 setRandomCell(8192);

		setWidth(size);
		setHeight(size);
		float x = (Game.getVirtualWidth() - Table.size) / 2;
		float y = (Game.getVirtualHeight() - Table.size) / 2;
		getLocation().set(x, y);

		this.getColor().set(Color.fromHex("#f0f0ff20"));

		gameOverDialog = new Dialog();
		gameOverDialog.addButton("ok", "Ok");
		gameOverDialog.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogClosed() {
				reset();
			}

			@Override
			public void onDialogButtonClicked(String id) {
				reset();
			}
		});

		resetButton = new ResetButton(this);

		scoreText = new Text();
		scoreText.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
		scoreText.setLocation(x + 4, y - 35);
		scoreText.setFontScale(1.0f);
		updateScoreText();
		
		hiscoreText = new Text();
		hiscoreText.setAlignment(Text.HAlignLeft, Text.VAlignTop);
		hiscoreText.setLocation(x + 4, 35 - y);
		hiscoreText.setFontScale(1.0f);
		hiscoreText.setText("HI: " + DoubleUpSettings.getHiScore());
		
		hiscoreBlockText = new Text();
		hiscoreBlockText.setAlignment(Text.HAlignRight, Text.VAlignTop);
		hiscoreBlockText.setLocation(- x - 4, 35 - y);
		hiscoreBlockText.setFontScale(1.0f);
		hiscoreBlockText.setText("MAX: " + DoubleUpSettings.getMaxNumber());
		
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
		setRandomCell(rand(5) == 1 ? 2 : 1);
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

	int getCellValue(int rowIndex, int colIndex) {
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
	}

	private void updateScoreText() {
		scoreText.setText("Score: " + score);
	}

	@Override
	public void draw() {
		resetButton.draw();
		scoreText.draw();
		hiscoreText.draw();
		hiscoreBlockText.draw();

		TextureDrawer.draw(Textures.tiles, this);

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				cell.draw();
			}
		}
	}
}
