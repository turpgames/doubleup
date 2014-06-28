package com.turpgames.doubleup.components.hiscore;

import com.turpgames.framework.v0.client.IServiceCallback;
import com.turpgames.framework.v0.client.TurpClient;
import com.turpgames.service.message.GetHiScoresRequest;
import com.turpgames.service.message.GetHiScoresResponse;

public class HiScoreManager {
	public static void getHiScores(final int days, final int mode, final IServiceCallback<GetHiScoresResponse> callback) {
		GetHiScoresResponse resp = HiScoreCache.getHiScores(days, mode, false);
		if (resp != null) {
			callback.onSuccess(resp);
			return;
		}

		TurpClient.getHiScores(days, GetHiScoresRequest.General, mode, true, 0, 10, new IServiceCallback<GetHiScoresResponse>() {
			@Override
			public void onSuccess(GetHiScoresResponse response) {
				HiScoreCache.putHiScores(days, mode, response);
				callback.onSuccess(response);
			}

			@Override
			public void onFail(Throwable t) {
				GetHiScoresResponse resp = HiScoreCache.getHiScores(days, mode, true);
				if (resp == null) {
					callback.onFail(t);
				} else {
					callback.onSuccess(resp);
				}
			}
		});
	}
}
