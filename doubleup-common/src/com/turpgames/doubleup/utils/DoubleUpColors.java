package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Color;

public class DoubleUpColors {
	public static Color getColor(long value) {
		switch ((int) value) {
		case 0:
			return color0;
		case 1:
			return color1;
		case 2:
			return color2;
		case 4:
			return color4;
		case 8:
			return color8;
		case 16:
			return color16;
		case 32:
			return color32;
		case 64:
			return color64;
		case 128:
			return color128;
		case 256:
			return color256;
		case 512:
			return color512;
		case 1024:
			return color1024;
		case 2048:
			return color2048;
		case 4096:
			return color4096;
		case 8192:
			return color8192;
		case 8192 * 2:
			return color8192_2;
		default:
			return color8192_4;
		}
	}

	public final static Color brickColor = Color.fromHex("#000000ff");
	public final static Color color0 = Color.fromHex("#00000000");
	public final static Color color1 = Color.fromHex("#fb9d49FF");
	public final static Color color2 = Color.fromHex("#f4d040FF");
	public final static Color color4 = Color.fromHex("#aed361FF");
	public final static Color color8 = Color.fromHex("#71c055FF");
	public final static Color color16 = Color.fromHex("#71c6a5FF");
	public final static Color color32 = Color.fromHex("#40b8eaFF");
	public final static Color color64 = Color.fromHex("#039fd6FF");
	public final static Color color128 = Color.fromHex("#5b52a3FF");
	public final static Color color256 = Color.fromHex("#9a6db0FF");
	public final static Color color512 = Color.fromHex("#d1499bFF");
	public final static Color color1024 = Color.fromHex("#f15f90FF");
	public final static Color color2048 = Color.fromHex("#ed1e24FF");
	public final static Color color4096 = Color.fromHex("#C0C0C0FF");
	public final static Color color8192 = Color.fromHex("#808080FF");
	public final static Color color8192_2 = Color.fromHex("#404040FF");
	public final static Color color8192_4 = Color.fromHex("#000000FF");
}
