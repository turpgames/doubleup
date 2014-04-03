package com.turpgames.doubleup.view;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.IInputListener;

public interface IDoubleUpView {
	void registerInputListener(IInputListener listener);

	void unregisterInputListener(IInputListener listener);

	void registerDrawable(IDrawable drawable, int layer);

	void unregisterDrawable(IDrawable drawable);
}
