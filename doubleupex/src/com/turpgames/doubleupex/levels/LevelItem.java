package com.turpgames.doubleupex.levels;

import com.turpgames.doubleup.utils.Textures;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.component.TextButton;
import com.turpgames.framework.v0.impl.GameObject;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.ShapeDrawer;
import com.turpgames.framework.v0.util.TextureDrawer;

public class LevelItem implements IDrawable {
	private final static int itemCount = 5;
	private final static float itemSpacing = 15f;
	private final static float itemSize = (Game.getVirtualWidth() - (itemCount + 1) * itemSpacing) / itemCount;
	private final static float dy = (Game.getVirtualHeight() - ((itemCount + 1) * itemSpacing + itemSize * itemCount)) / 2;

	private final LevelInfo levelInfo;
	private final LevelButton levelButton;

	public LevelItem(LevelInfo levelInfo, int row, int col) {
		this.levelInfo = levelInfo;
		this.levelButton = new LevelButton(row, col);
	}

	@Override
	public void draw() {
		levelButton.draw();
	}

	private class LevelButton extends TextButton {
		public LevelButton(int row, int col) {
			super(Color.fromHex("#08f"), Color.fromHex("#4cf"));

			setText("" + (row * itemCount + col));
			
			setWidth(itemSize);
			setHeight(itemSize);
			
			getLocation().set(
					(col + 1) * itemSpacing + col * itemSize,
					Game.getVirtualHeight() - dy - (row + 1) * itemSpacing - (row + 1) * itemSize);
			getColor().set(Color.white());
		}

		@Override
		public void draw() {
			TextureDrawer.draw(Textures.tile, this);
			super.draw();
		}
		
		@Override
		public boolean ignoreViewport() {
			return false;
		}
	}
}
