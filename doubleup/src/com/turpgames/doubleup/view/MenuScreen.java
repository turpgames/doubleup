package com.turpgames.doubleup.view;

import com.turpgames.framework.v0.IGameExitListener;
import com.turpgames.framework.v0.component.LanguageMenu;
import com.turpgames.framework.v0.forms.xml.Dialog;
import com.turpgames.framework.v0.forms.xml.Form;
import com.turpgames.framework.v0.impl.FormScreen;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.framework.v0.util.Version;
import com.turpgames.doubleup.objects.display.DoubleUpDialog;
import com.turpgames.doubleup.objects.display.DoubleUpLanguageMenu;
import com.turpgames.doubleup.objects.display.DoubleUpToolbar;
import com.turpgames.doubleup.objects.display.FacebookLoginButton;
import com.turpgames.doubleup.utils.DoubleUp;
import com.turpgames.doubleup.utils.R;

public class MenuScreen extends FormScreen implements IGameExitListener {
	private Dialog exitConfirm;
	private LanguageMenu languageBar;
	private FacebookLoginButton loginButton;
	private boolean isFirstActivate;

	@Override
	public void init() {
		super.init();

		isFirstActivate = true;

		languageBar = new DoubleUpLanguageMenu();
		languageBar.setListener(new LanguageMenu.ILanguageMenuListener() {
			@Override
			public void onLanguageMenuShown() {
				getCurrentForm().disable();
				DoubleUpToolbar.getInstance().disable();
				loginButton.deactivate();
			}

			@Override
			public void onLanguageMenuHidden() {
				getCurrentForm().enable();
				DoubleUpToolbar.getInstance().enable();
				ensureTopLeftButtons(getCurrentForm());
			}
		});

		Game.exitListener = this;
		
		loginButton = new FacebookLoginButton();

		registerDrawable(loginButton, Game.LAYER_SCREEN);
		registerDrawable(DoubleUpGame.getToolbar(), Game.LAYER_SCREEN);

		exitConfirm = new DoubleUpDialog();
		exitConfirm.setListener(new Dialog.IDialogListener() {
			@Override
			public void onDialogButtonClicked(String id) {
				if (R.strings.yes.equals(id)) {
					Game.exitListener = null;
					Game.exit();
				}
			}

			@Override
			public void onDialogClosed() {

			}
		});

		setForm(R.game.forms.menu, false);
	}

	@Override
	public boolean onGameExit() {
		exitConfirm.open(DoubleUp.getString(R.strings.exitProgramConfirm));
		return false;
	}

	private void announceFacebook() {
//		Version v120 = new Version("1.2.0");
//		if (Game.getVersion().compareTo(v120) < 0)
//			return;
//		
//		boolean facebookAnnounced = DoubleUpSettings.isFacebookAnnounced();
//		if (facebookAnnounced)
//			return;
//		
//		Dialog dlg = new Dialog();
//		dlg.addButton("yes", R.strings.yes);
//		dlg.addButton("no", R.strings.no);
//		dlg.setFontScale(R.fontSize.medium);
//		dlg.setListener(new Dialog.IDialogListener() {			
//			@Override
//			public void onDialogClosed() {
//
//			}
//			
//			@Override
//			public void onDialogButtonClicked(String id) {
//				if (!"yes".equals(id))
//					return;
//				Facebook.login(new ICallback() {
//					@Override
//					public void onSuccess() {
//						loginButton.update();
//					}
//					
//					@Override
//					public void onFail(Throwable t) {
//						loginButton.update();
//					}
//				});
//			}
//		});
//		dlg.open(DoubleUp.getString(R.strings.announceFacebook));
	}
	
	private void forceUpgrade() {
		if (!Game.isAndroid())
			return;
		
		if (!"1.1.3".equals(Game.getVersion().toString()))
			return;
		
		Dialog dlg = new Dialog();
		dlg.addButton("ok", R.strings.ok);
		dlg.setListener(new Dialog.IDialogListener() {			
			@Override
			public void onDialogClosed() {
				Game.exitListener = null;
				Game.exit();
			}
			
			@Override
			public void onDialogButtonClicked(String id) {
				Game.openUrl(DoubleUp.getStoreUrl());
				Game.exitListener = null;
				Game.exit();
			}
		});
		dlg.open(DoubleUp.getString(R.strings.forceUpgrade));
	}

	private void loginWithFacebook() {
		if (Facebook.canLogin() && !Facebook.isLoggedIn()) {
			Facebook.login(new ICallback() {
				@Override
				public void onSuccess() {
					loginButton.update();
				}

				@Override
				public void onFail(Throwable t) {
					loginButton.update();
				}
			});
		}
	}

	@Override
	protected boolean onBeforeDeactivate() {
		languageBar.deactivate();
		loginButton.deactivate();
		return super.onBeforeDeactivate();
	}

	@Override
	protected void onAfterActivate() {
		super.onAfterActivate();

		languageBar.activate();

		ensureTopLeftButtons(getCurrentForm());

		if (isFirstActivate) {
			isFirstActivate = false;
			announceFacebook();
			forceUpgrade();
			loginWithFacebook();
		}
	}
	
	@Override
	protected void onFormActivated(Form activatedForm) {
		ensureTopLeftButtons(activatedForm);
		super.onFormActivated(activatedForm);
	}

	private void ensureTopLeftButtons(Form currentForm) {
		if (currentForm.getId().equals(R.game.forms.menu)) {
			DoubleUpToolbar.getInstance().deactivateBackButton();
			loginButton.activate();
		}
	}
}