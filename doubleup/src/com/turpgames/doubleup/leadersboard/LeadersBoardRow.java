package com.turpgames.doubleup.leadersboard;

import com.turpgames.doubleup.entity.Player;
import com.turpgames.doubleup.entity.Score;
import com.turpgames.doubleup.utils.Facebook;
import com.turpgames.doubleup.utils.R;
import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Vector;

public class LeadersBoardRow implements IDrawable {
	private final float rowWidth = 500f;
	private final float rowHeight = 40f;
	private float rankWidth = 0.05f;
	private float nameWidth = 0.6f;
	private float maxScoreWidth = 0.2f;

	// LeadersBoard (25, 100) x (525, 525) karesine çizilecek
	private final Vector bottomLeft = new Vector(25, 100);
	private final Vector topRight = new Vector(525, 525);

	private final Text rank;
	private final Text playerName;
	private final Text maxScore;
	private final Text score;

	public LeadersBoardRow(int rank, Score score, Player player) {
		this.rank = createText(rank + ".");

		this.playerName = createText(player.getUsername());
		this.maxScore = createText(score.getMaxNumber() + "");
		this.score = createText(score.getScore() + "");

		if (Facebook.getUser().getSocialId().equals(player.getFacebookId())) {
			this.rank.getColor().set(R.colors.turpYellow);
			this.playerName.getColor().set(R.colors.turpYellow);
			this.maxScore.getColor().set(R.colors.turpYellow);
			this.score.getColor().set(R.colors.turpYellow);
		}

		setLocations(Math.min(10, rank - 1));
	}

	private void setLocations(int rowIndex) {
		float y = topRight.y - (rowIndex * rowHeight);
		float x = bottomLeft.x;
		rank.setLocation(x, y);
		playerName.setLocation(x + rowWidth * (rankWidth), y);
		maxScore.setLocation(x + rowWidth * (rankWidth + nameWidth), y);
		score.setLocation(x + rowWidth * (rankWidth + nameWidth + maxScoreWidth), y);
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
		maxScore.draw();
		score.draw();
	}
}
