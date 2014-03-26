package com.turpgames.doubleup.view;

import com.turpgames.framework.v0.IDrawable;

public interface IDoubleUpViewListener extends IDrawable {
	public static final IDoubleUpViewListener NULL = new IDoubleUpViewListener() {
		@Override
		public void draw() {

		}

		@Override
		public void onScreenActivated() {

		}

		@Override
		public boolean onScreenDeactivated() {
			return true;
		}
	};

	void onScreenActivated();

	boolean onScreenDeactivated();
}
