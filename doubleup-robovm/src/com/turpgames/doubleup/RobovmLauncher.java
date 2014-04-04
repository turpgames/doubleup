package com.turpgames.doubleup;

import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSMutableArray;
import org.robovm.apple.foundation.NSString;
import org.robovm.bindings.admob.GADAdSize;
import org.robovm.bindings.admob.GADAdSizeManager;
import org.robovm.bindings.admob.GADBannerView;
import org.robovm.bindings.admob.GADBannerViewDelegate;
import org.robovm.bindings.admob.GADInterstitial;
import org.robovm.bindings.admob.GADRequest;
import org.robovm.bindings.admob.GADRequestError;
import org.robovm.bindings.facebook.manager.FacebookManager;
import org.robovm.cocoatouch.foundation.NSAutoreleasePool;
import org.robovm.cocoatouch.foundation.NSDictionary;
import org.robovm.cocoatouch.foundation.NSObject;
import org.robovm.cocoatouch.foundation.NSURL;
import org.robovm.cocoatouch.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.turpgames.framework.v0.impl.ios.IOSProvider;
import com.turpgames.framework.v0.impl.libgdx.GdxGame;
import com.turpgames.framework.v0.util.Game;

public class RobovmLauncher extends IOSApplication.Delegate {
	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		config.orientationLandscape = false;
		config.orientationPortrait = true;

		Game.setEnvironmentProvider(new IOSProvider());

		return new IOSApplication(new GdxGame(), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, RobovmLauncher.class);
		pool.drain();
	}

	@Override
	public boolean openURL(UIApplication application, NSURL url, String sourceApplication, NSObject annotation) {
		return FacebookManager.getInstance().handleOpenUrl(url, sourceApplication);
	}

//	@Override
//	public boolean didFinishLaunching(UIApplication application, NSDictionary launchOptions) {
//		
//		
//		boolean res = super.didFinishLaunching(application, launchOptions);
//
//		GADBannerView banner = new GADBannerView(GADAdSizeManager.banner());
//		System.out.println(banner);
//		
//		banner.setAdUnitID("ca-app-pub-9430411208134016/7394627879");
//		
//		banner.setRootViewController(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
//		
//		GADRequest request = new GADRequest();
//		NSArray<NSString> testDevices = new NSArray<>(new NSString("db4b1b1f6e85bab414905c3cc8ad5977"));
//		request.setTestDevices(testDevices);
//		
//		banner.setDelegate(new GADBannerViewDelegate.Adapter(){
//			@Override
//			public void didReceiveAd (GADBannerView view) {
//			}
//
//			@Override
//			public void didFailToReceiveAd (GADBannerView view, GADRequestError error) {
//			}
//
//			@Override
//			public void willPresentScreen (GADBannerView view) {
//			}
//
//			@Override
//			public void willDismissScreen (GADBannerView view) {
//			}
//
//			@Override
//			public void didDismissScreen (GADBannerView view) {
//			}
//
//			@Override
//			public void willLeaveApplication (GADBannerView view) {
//			}
//		});
//		
//		banner.loadRequest(request);
//
//		return res;
//	}
//
//	private void interstetial() {
//		GADInterstitial ad = new GADInterstitial();
//		ad.setAdUnitID("ca-app-pub-9430411208134016/3931947478");
//		ad.setDelegate(new GADInterstitialDelegateImpl());
//
//
//		GADRequest request = new GADRequest();
//		NSArray<NSString> testDevices = new NSArray<>(new NSString("db4b1b1f6e85bab414905c3cc8ad5977"));
//		request.setTestDevices(testDevices);
//
//		ad.loadRequest(request);
//	}

	// @Override
	// public boolean didFinishLaunching(UIApplication application, NSDictionary
	// launchOptions) {
	// boolean res = super.didFinishLaunching(application, launchOptions);
	//
	// MPInterstitialAdController interstitial =
	// MPInterstitialAdController.getAdController("1fdf446cc1ec40888c810c6e6c86b6cb");
	//
	// MPInterstitialAdControllerDelegate delegate = new
	// MPInterstitialAdControllerDelegate.Adapter() {
	// @Override
	// public void didDisappear(MPInterstitialAdController interstitial) {
	// // If the ad disappears, load a new ad, so we can show it
	// // immediately the next time.
	// System.out.println("didDisappear");
	// // interstitial.loadAd();
	// }
	//
	// @Override
	// public void didExpire(MPInterstitialAdController interstitial) {
	// // If the ad did expire, load a new ad, so we can show it
	// // immediately the next time.
	// System.out.println("didExpire");
	// // interstitial.loadAd();
	// }
	//
	// @Override
	// public void didLoadAd(MPInterstitialAdController interstitial) {
	// // If the ad is ready, show it.
	// // It's best to call these methods manually and not in
	// // didLoadAd(). Use this only for testing purposes!
	// System.out.println("didLoadAd");
	// // if (interstitial.isReady())
	// //
	// interstitial.show(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
	// }
	//
	// @Override
	// public void didFailToLoadAd(MPInterstitialAdController interstitial) {
	// // If the ad did fail to load, load a new ad. Check the debug
	// // log to see why it didn't load.
	// System.out.println("didFailToLoadAd");
	// // interstitial.loadAd();
	// }
	// };
	// interstitial.setDelegate(delegate);
	//
	// try {
	// interstitial.loadAd();
	// if (interstitial.isReady())
	// interstitial.show(UIApplication.getSharedApplication().getKeyWindow().getRootViewController());
	// } catch (Throwable t) {
	// t.printStackTrace();
	// }
	// return res;
	// }
}