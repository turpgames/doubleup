package com.turpgames.doubleup.objects;

import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.TextureDrawer;
import com.turpgames.utils.Util;

public class Table extends GameObject {
	public final static int matrixSize = 4;
	public final static float size = 512f;

	private final Row[] rows;

	private final IMoveCommand moveUp;
	private final IMoveCommand moveDown;
	private final IMoveCommand moveLeft;
	private final IMoveCommand moveRight;

	private int score;
	private Text scoreText;
	private final Dialog gameOverDialog;
	private final ResetButton resetButton;

	public Table() {
		this.rows = new Row[4];

		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row(this, i);
		}

		setRandomCell();
		setRandomCell();

//		 setRandomCell(1);
//		 setRandomCell(2);
//		 setRandomCell(4);
//		 setRandomCell(8);
//		 setRandomCell(16);
//		 setRandomCell(32);
//		 setRandomCell(64);
//		 setRandomCell(128);
//		 setRandomCell(256);
//		 setRandomCell(512);
//		 setRandomCell(1024);
//		 setRandomCell(2048);
//		 setRandomCell(4096);
//		 setRandomCell(8192);

		moveUp = MoveCommand.create(MoveDirection.Up, this);
		moveDown = MoveCommand.create(MoveDirection.Down, this);
		moveLeft = MoveCommand.create(MoveDirection.Left, this);
		moveRight = MoveCommand.create(MoveDirection.Right, this);

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
		scoreText.setLocation(x + 4, y - 30);
		scoreText.setFontScale(0.6f);
		updateScoreText();
	}

	private int rand() {
		return Util.Random.randInt(rows.length);
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
		setRandomCell(Util.Random.randInt() % 5 == 1 ? 2 : 1);
	}

	private void setRandomCell(int value) {
		Cell cell;
		do {
			cell = getCell(rand(), rand());
		} while (!cell.isEmpty());

		Tile tile = new Tile();
		tile.setValue(value);
		tile.moveToCell(cell);
	}
	
	Cell getCell(int rowIndex, int colIndex) {
		return rows[rowIndex].getCell(colIndex);
	}
	
	int getCellValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}

	public void move(MoveDirection direction) {
		int moveScore = 0;
		switch (direction) {
		case Down:
			moveScore = moveDown.execute();
			break;
		case Left:
			moveScore = moveLeft.execute();
			break;
		case Right:
			moveScore = moveRight.execute();
			break;
		case Up:
			moveScore = moveUp.execute();
			break;
		}

		boolean anythingMoved = moveScore > -1;

		if (!anythingMoved) {
			SoundEffects.addEffect(Game.getResourceManager().getSound("nomove"));
			return;	
		}

		if (moveScore > 0) {
			score += moveScore;
			updateScoreText();
		}
		else {
			SoundEffects.addEffect(Game.getResourceManager().getSound("noscore"));
		}

		if (hasEmptyCell()) {
			setRandomCell();

			if (!hasMove()) {
				SoundEffects.addEffect(Game.getResourceManager().getSound("gameover"));
				gameOverDialog.open("Game Over!");
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

		TextureDrawer.draw(Textures.tiles, this);

		for (Row row : rows) {
			for (Cell cell : row.getCells()) {
				cell.draw();
			}
		}
		
	}
}
