package com.turpgames.doubleup.objects;

abstract class MoveCommand implements IMoveCommand {
	private final static int martixSize = Table.matrixSize;
	
	protected final Table table;

	private MoveCommand(Table table) {
		this.table = table;
	}

	@Override
	public int execute() {
//		table.beginUpdate();
		boolean hasMoved = move();
		int score = add();
		if (score == 0) {
			return hasMoved ? 0 : -1;
		}
		move();
//		table.endUpdate();
		return score;
	}

	protected abstract boolean move();

	protected abstract int add();

	public static IMoveCommand create(MoveDirection direction, Table table) {
		switch (direction) {
		case Down:
			return new MoveDownCommand(table);
		case Left:
			return new MoveLeftCommand(table);
		case Up:
			return new MoveUpCommand(table);
		case Right:
		default:
			return new MoveRightCommand(table);
		}
	}

	private static class MoveRightCommand extends MoveCommand {
		private MoveRightCommand(Table table) {
			super(table);
		}

		@Override
		protected boolean move() {
			boolean hasMovement = false;
			for (int rowIndex = 0; rowIndex < martixSize; rowIndex++) {
				for (int colIndex = martixSize - 1; colIndex >= 0; colIndex--) {
					hasMovement = table.getCell(rowIndex, colIndex).moveRight() || hasMovement;;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int rowIndex = 0; rowIndex < martixSize; rowIndex++) {
				for (int colIndex = martixSize - 1; colIndex >= 0; colIndex--) {
					score += table.getCell(rowIndex, colIndex).addRight();
				}
			}
			return score;
		}
	}

	private static class MoveLeftCommand extends MoveCommand {
		private MoveLeftCommand(Table table) {
			super(table);
		}

		@Override
		protected boolean move() {
			boolean hasMovement = false;
			for (int rowIndex = 0; rowIndex < martixSize; rowIndex++) {
				for (int colIndex = 0; colIndex < martixSize; colIndex++) {
					hasMovement = table.getCell(rowIndex, colIndex).moveLeft() || hasMovement;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int rowIndex = 0; rowIndex < martixSize; rowIndex++) {
				for (int colIndex = 0; colIndex < martixSize; colIndex++) {
					score += table.getCell(rowIndex, colIndex).addLeft();
				}
			}
			return score;
		}
	}

	private static class MoveDownCommand extends MoveCommand {
		private MoveDownCommand(Table table) {
			super(table);
		}

		@Override
		protected boolean move() {
			boolean hasMovement = false;
			for (int j = 0; j < martixSize; j++) {
				for (int i = martixSize - 1; i >= 0; i--) {
					hasMovement = table.getCell(i, j).moveDown() || hasMovement;;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int j = 0; j < martixSize; j++) {
				for (int i = martixSize - 1; i >= 0; i--) {
					score += table.getCell(i, j).addDown();
				}
			}
			return score;
		}
	}

	private static class MoveUpCommand extends MoveCommand {
		private MoveUpCommand(Table table) {
			super(table);
		}

		@Override
		protected boolean move() {
			boolean hasMovement = false;
			for (int j = 0; j < martixSize; j++) {
				for (int i = 0; i < martixSize; i++) {
					hasMovement = table.getCell(i, j).moveUp() || hasMovement;;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int j = 0; j < martixSize; j++) {
				for (int i = 0; i < martixSize; i++) {
					score += table.getCell(i, j).addUp();
				}
			}
			return score;
		}
	}
}
