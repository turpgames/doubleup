package com.turpgames.doubleup.components.hiscore;

import com.turpgames.doubleup.utils.R;
import com.turpgames.entity.Player;
import com.turpgames.entity.Score;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Vector;

class HiScoreRow implements IDrawable {
	private final float rowWidth = 500f;
	private final float rowHeight = 40f;
	private float rankWidth = 0.1f;
	private float nameWidth = 0.6f;
	private float maxNumberWidth = 0.15f;

	private final Vector bottomLeft = new Vector(25, 100);
	private final Vector topRight = new Vector(525, 525);

	private final Text rank;
	private final Text playerName;
	private final Text maxNumber;
	private final Text score;

	public HiScoreRow(int rank, Score score, Player player) {
		this.rank = createText(rank + ".");

		this.playerName = createText(player.getName());
		this.maxNumber = createText(score.getExtraData());
		this.score = createText(score.getScore() + "");

		if (TurpClient.getPlayer() != null &&
				TurpClient.getPlayer().getId() == player.getId()) {
			this.rank.getColor().set(R.colors.yellow);
			this.playerName.getColor().set(R.colors.yellow);
			this.maxNumber.getColor().set(R.colors.yellow);
			this.score.getColor().set(R.colors.yellow);
		}

		setLocations(Math.min(10, rank - 1));
	}

	private void setLocations(int rowIndex) {
		float y = topRight.y - (rowIndex * rowHeight);
		float x = bottomLeft.x;
		rank.setLocation(x, y);
		playerName.setLocation(x + rowWidth * (rankWidth), y);
		maxNumber.setLocation(x + rowWidth * (rankWidth + nameWidth), y);
		score.setLocation(x + rowWidth * (rankWidth + nameWidth + maxNumberWidth), y);
	}

	private static Text createText(String content) {
		Text text = new Text();
		text.setText(content);
		text.setAlignment(Text.HAlignLeft, Text.VAlignBottom);
		text.setFontScale(0.5f);
		return text;
	}

	@Override
	public void draw() {
		rank.draw();
		playerName.draw();
		maxNumber.draw();
		score.draw();
	}
}
