package com.turpgames.doubleup;

import org.robovm.apple.coregraphics.CGPoint;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSURL;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.uikit.UIWindow;
import org.robovm.bindings.admob.GADAdSize;
import org.robovm.bindings.admob.GADAdSizeManager;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADErrorCode;
import org.robovm.bindings.admob.GADInterstitial;
import org.robovm.bindings.admob.GADInterstitialDelegateAdapter;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.bindings.facebook.manager.FacebookManager;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.turpgames.framework.v0.impl.ios.IOSProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.util.Debug;
import com.turpgames.framework.v0.util.Game;

public class RobovmLauncher extends IOSApplication.Delegate {
	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		config.orientationLandscape = false;
		config.orientationPortrait = true;
		config.allowIpod = true;

		Game.setEnvironmentProvider(new IOSProvider());

		return new IOSApplication(new GdxGame(), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.close();
	}

	@Override
	public boolean openURL(UIApplication application, NSURL url, String sourceApplication, NSObject annotation) {
		return FacebookManager.getInstance().handleOpenUrl(url, sourceApplication);
	}

	@Override
	public boolean didFinishLaunching(UIApplication application, NSDictionary launchOptions) {
		System.out.println("didFinishLaunching...");

		boolean res = super.didFinishLaunching(application, launchOptions);

		return res;
	}

	private void banner(UIApplication application) {
		GADBannerView bannerView = new GADBannerView(GADAdSizeManager.smartBannerPortrait());
		bannerView.setAdUnitID("ca-app-pub-9430411208134016/5157355074");

		UIViewController rootViewController = application.getKeyWindow().getRootViewController();
		bannerView.setRootViewController(rootViewController);
		rootViewController.getView().addSubview(bannerView);

		bannerView.loadRequest(GADRequest.request());
		application.getKeyWindow().setRootViewController(rootViewController);
		application.getKeyWindow().addSubview(rootViewController.getView());
		application.getKeyWindow().makeKeyAndVisible();
	}

	private void interstitial() {
		GADInterstitial interstitial = new GADInterstitial();

		interstitial.setAdUnitID("ca-app-pub-9430411208134016/3931947478");

		interstitial.setDelegate(new GADInterstitialDelegateAdapter() {
			@Override
			public void didReceiveAd(GADInterstitial ad) {
				System.out.println("didReceiveAd");
			}

			@Override
			public void didFailToReceiveAd(GADInterstitial ad, GADErrorCode error) {
				System.out.println("didFailToReceiveAd");
			}

			@Override
			public void willPresentScreen(GADInterstitial ad) {
				System.out.println("willPresentScreen");
			}

			@Override
			public void willDismissScreen(GADInterstitial ad) {
				System.out.println("willDismissScreen");
			}

			@Override
			public void didDismissScreen(GADInterstitial ad) {
				System.out.println("didDismissScreen");
			}

			@Override
			public void willLeaveApplication(GADInterstitial ad) {
				System.out.println("willLeaveApplication");
			}
		});

		System.out.println("loading request...");
		interstitial.loadRequest(GADRequest.request());
		// interstitial.present(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
	}
}