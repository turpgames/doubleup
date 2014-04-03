package com.turpgames.doubleupex.levels;

import com.turpgames.doubleupex.objects.Table;

public class Level1 extends Table {
	public Level1() {
		super(4);
	}

	@Override
	protected void initLevel() {
//		setAsBrick(2, 1);
//		setAsBrick(1, 2);
		setCellValue(2, 0, 1);
		setCellValue(1, 3, 1);
		setAddRandonNumberAfterMove(true);
		setTargetNumber(128);
	}
}
