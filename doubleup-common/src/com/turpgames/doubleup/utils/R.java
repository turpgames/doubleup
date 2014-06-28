package com.turpgames.doubleup.utils;

import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.FontManager;
import com.turpgames.framework.v0.util.Game;

public final class R {

	public static final class game {
		public static final class textures {
			public static final String fb_default = "fb_default";
			public static final String facebook = "facebook";

			public static final class toolbar {
				public static final String back = "tb_back";
				public static final String settings = "tb_settings";
				public static final String musicPlay = "tb_music_play";
				public static final String musicStop = "tb_music_stop";
				public static final String soundOn = "tb_sound_on";
				public static final String soundOff = "tb_sound_off";
				public static final String vibrationOn = "tb_vibration_on";
				public static final String vibrationOff = "tb_vibration_off";
			}
		}
		public static final class forms {
			public static final String menu = "mainMenu";
		}
		public static final class screens {
			public static final String menu = "menu";
		}
	}

	public final static class sizes {
		public final static float maxScale = 0.2f;
		public final static float menuButtonSpacing = Game.scale(15);

		public final static int scoreImageSize = 64;

		public final static float tutorialbuttonSpacing = Game.scale(10);
		public final static float toolbarMargin = Game.scale(15);
		
		public final static float menuButtonSize = 64;
		public final static float libgdxLogoWidth = 200;
		public final static float libgdxLogoHeight = 33;

		public final static float menuButtonSizeToScreen = Game
				.scale(menuButtonSize);

		public final static float flagButtonSize = 128;
		public final static float langFlagButtonSizeToScreen = Game.scale(flagButtonSize);
	}
	public final static class durations {
		public final static float fadingDuration = 0.25f;
		public final static float blinkDuration = 1f;
		public final static float toastSlideDuration = 0.2f;
		public final static float toastDisplayDurationPerWord = 0.15f;
		public final static float toastDisplayDurationBuffer = 1.5f;
	}
	
	public final static class colors {
		public static final Color yellow = Color.fromHex("#f9b000ff");

		public static final Color ichiguWhite = Color.fromHex("#ffffffff");
		public static final Color ichiguBlack = Color.fromHex("#000000ff");
		public static final Color ichiguRed = Color.fromHex("#d0583bff");
		public static final Color ichiguGreen = Color.fromHex("#56bd89ff");
		public static final Color ichiguBlue = Color.fromHex("#3974c1ff");
		public static final Color ichiguCyan = Color.fromHex("#00f9b0ff");
		public static final Color ichiguMagenta = Color.fromHex("#f900b0ff");
		
		public static final Color buttonDefault = ichiguWhite;
		public static final Color buttonTouched = ichiguBlue;
	}

	public final static class textures {
		public final static String turpLogo = "turp_logo";
	}

	public final static class screens {
		public final static String game = "game";
		public final static String menu = "menu";
		public final static String scoreBoard = "scoreBoard";
		public final static String about = "about";
		public final static String result = "result";
	}

	public static final class fontSize {
		public static final float xSmall = FontManager.defaultFontSize * 0.5f;
		public static final float small = FontManager.defaultFontSize * 0.625f;
		public static final float medium = FontManager.defaultFontSize * 0.75f;
		public static final float large = FontManager.defaultFontSize * 1f;
		public static final float xLarge = FontManager.defaultFontSize * 1.25f;
	}

	public static final class settings {
		public static final class hiscores {
			public static final String score = "hiscore_score";
			public static final String blockSize = "hiscore_blocksize";
		}

		public static final String music = "music";
		public static final String sound = "sound";

		public static final String scoresToSend = "scores-to-send";
		public static final String facebookAnnounced = "facebook-announced";
		public static final String vibration = "vibration";
	}

	public static final class strings {
		public final static String yes = "yes";
		public final static String no = "no";

		public final static String hiScores = "hiScores";
		public final static String hiscoreResetConfirm = "hiscoreResetConfirm";
		public final static String resetHiscore = "resetHiscore";
		public final static String hiscoreInfo = "hiscoreInfo";

		public static final String logoutConfirm = "logoutConfirm";
		public final static String exitProgramConfirm = "exitProgramConfirm";
		
		public final static String announceFacebook = "announceFacebook";	
		public final static String ok = "ok";
		public final static String forceUpgrade = "forceUpgrade";
	}
	
	private R() {
	}
}