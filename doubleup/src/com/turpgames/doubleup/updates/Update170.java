package com.turpgames.doubleup.updates;

import com.turpgames.framework.v0.IUpdateProcess;
import com.turpgames.framework.v0.util.Setting;
import com.turpgames.framework.v0.util.Version;
import com.turpgames.framework.v0.util.Setting.StringSetting;
import com.turpgames.utils.Util;

public class Update170 implements IUpdateProcess {
	private final static Version version = new Version("1.7.0");
	
	public final static StringSetting anonymousId = Setting.stringKey("anonymous-user-id", "");
	public final static StringSetting guestId = Setting.stringKey("guest-user-id", "");
	public final static StringSetting guestName = Setting.stringKey("guest-name", "");

	@Override
	public void execute() {
		String guestIdStr = guestId.get();
		
		if (!Util.Strings.isNullOrWhitespace(guestIdStr))
			return;
		
		String anonymousIdStr = anonymousId.get();

		if (Util.Strings.isNullOrWhitespace(anonymousIdStr))
			return;
		
		guestId.set(anonymousIdStr);
		guestName.set("Player " + anonymousIdStr);
	}

	@Override
	public Version getVersion() {
		return version;
	}
}
