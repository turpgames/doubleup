package com.turpgames.doubleup.components.hiscore;

import com.turpgames.framework.v0.IView;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.service.message.GetHiScoresResponse;

class HiScoreView implements IView {

	private final String id;
	private final Text title;
	private final Text subTitle;
	private final HiScoreTable table;

	public HiScoreView(String titleText, String subTitleText) {
		title = new Text();
		title.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		title.setPadY(150f);
		title.setText(titleText);
		
		subTitle = new Text();
		subTitle.setAlignment(Text.HAlignCenter, Text.VAlignTop);
		subTitle.setPadY(200f);
		subTitle.setFontScale(0.5f);
		subTitle.setText(subTitleText);

		this.id = titleText + subTitleText;
		
		table = new HiScoreTable();
	}
	
	public void bindData(GetHiScoresResponse data) {
		table.bindData(data);
	}

	@Override
	public void draw() {
		title.draw();
		subTitle.draw();
		table.draw();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void activate() {

	}

	@Override
	public boolean deactivate() {
		return true;
	}
}
