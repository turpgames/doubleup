package com.turpgames.doubleup.objects;

abstract class MoveCommand implements IMoveCommand {
	protected final Row[] rows;
	protected final Table table;

	private MoveCommand(Table table) {
		this.rows = table.rows;
		this.table = table;
	}

	@Override
	public int execute() {
		table.beginUpdate();
		boolean hasMoved = move();
		int score = add();
		if (score == 0) {
			return hasMoved ? 0 : -1;
		}
		move();
		table.endUpdate();
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
			for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
				for (int colIndex = rows.length - 1; colIndex >= 0; colIndex--) {
					hasMovement = rows[rowIndex].cells[colIndex].moveRight() || hasMovement;;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
				for (int colIndex = rows.length - 1; colIndex >= 0; colIndex--) {
					score += rows[rowIndex].cells[colIndex].addRight();
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
			for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
				for (int colIndex = 0; colIndex < rows.length; colIndex++) {
					hasMovement = rows[rowIndex].cells[colIndex].moveLeft() || hasMovement;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
				for (int colIndex = 0; colIndex < rows.length; colIndex++) {
					score += rows[rowIndex].cells[colIndex].addLeft();
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
			for (int j = 0; j < rows.length; j++) {
				for (int i = rows.length - 1; i >= 0; i--) {
					hasMovement = rows[i].cells[j].moveDown() || hasMovement;;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int j = 0; j < rows.length; j++) {
				for (int i = rows.length - 1; i >= 0; i--) {
					score += rows[i].cells[j].addDown();
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
			for (int j = 0; j < rows.length; j++) {
				for (int i = 0; i < rows.length; i++) {
					hasMovement = rows[i].cells[j].moveUp() || hasMovement;;
				}
			}
			return hasMovement;
		}

		@Override
		protected int add() {
			int score = 0;
			for (int j = 0; j < rows.length; j++) {
				for (int i = 0; i < rows.length; i++) {
					score += rows[i].cells[j].addUp();
				}
			}
			return score;
		}
	}
}
