package com.turpgames.doubleup;

import org.robovm.apple.foundation.NSObject;
import org.robovm.bindings.admob.GADErrorCode;
import org.robovm.bindings.admob.GADInterstitial;
import org.robovm.bindings.admob.GADInterstitialDelegate;
import org.robovm.objc.ObjCObject;

public class GADInterstitialDelegateImpl extends NSObject implements GADInterstitialDelegate {
	@Override
	public void willPresentScreen(GADInterstitial ad) {
		System.out.println("willPresentScreen");
	}
	
	@Override
	public void willLeaveApplication(GADInterstitial ad) {
		System.out.println("willLeaveApplication");
	}
	
	@Override
	public void willDismissScreen(GADInterstitial ad) {
		System.out.println("willDismissScreen");
	}
	
	@Override
	public void didReceiveAd(GADInterstitial ad) {
		System.out.println("didReceiveAd");
		ad.present(null);
	}
	
	@Override
	public void didFailToReceiveAd(GADInterstitial ad, GADErrorCode error) {
		System.out.println("didFailToReceiveAd");
	}
	
	@Override
	public void didDismissScreen(GADInterstitial ad) {
		System.out.println("didDismissScreen");
	}
}