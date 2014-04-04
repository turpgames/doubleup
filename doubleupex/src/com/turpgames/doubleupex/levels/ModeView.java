package com.turpgames.doubleupex.levels;

import java.util.ArrayList;
import java.util.List;

import com.turpgames.framework.v0.IView;

public class ModeView implements IView {
	private final ModeInfo modeInfo;
	private final List<LevelItem> levelItems;

	public ModeView() {
		modeInfo = new ModeInfo();
		levelItems = new ArrayList<LevelItem>();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				levelItems.add(new LevelItem(null, i, j));
			}
		}
	}

	@Override
	public void draw() {
		for (LevelItem levelItem : levelItems)
			levelItem.draw();
	}

	@Override
	public String getId() {
		return modeInfo.getModeName();
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deactivate() {
		// TODO Auto-generated method stub
		return false;
	}

}
