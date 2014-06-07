package com.turpgames.doubleup.client;

import com.turpgames.doubleup.entity2.Actions;
import com.turpgames.framework.v0.IHttpResponse;
import com.turpgames.framework.v0.IHttpResponseListener;
import com.turpgames.framework.v0.impl.HttpRequest;
import com.turpgames.framework.v0.social.ICallback;
import com.turpgames.framework.v0.util.Game;

public abstract class DoubleUpAction implements IDoubleUpAction {
	private final static String baseUrl;

	static {
		String server = Game.getParam("server");
		baseUrl = Game.getParam(server + "-server");
	}

	private final String url;
	private final boolean async;

	protected DoubleUpAction(String action, boolean async) {
		this.url = String.format("%s?%s=%s", baseUrl, Actions.action, action);
		this.async = async;
	}

	@Override
	public void execute(final ICallback callback) {
		try {
			HttpRequest.Builder builder = HttpRequest.newPostRequestBuilder()
					.setAsync(async)
					.setTimeout(5000)
					.setUrl(url);

			setContent(builder);

			builder.build()
					.send(new IHttpResponseListener() {
						@Override
						public void requestCancelled() {
							callback.onFail(null);
						}

						@Override
						public void onHttpResponseReceived(IHttpResponse response) {
							if (response.getStatus() == 200) {
								onSuccess(response, callback);
							} else {
								callback.onFail(null);
							}
						}

						@Override
						public void onError(Throwable t) {
							callback.onFail(t);
						}
					});
		} catch (Throwable t) {
			callback.onFail(t);
		}
	}

	protected abstract void setContent(HttpRequest.Builder builder);

	protected abstract void onSuccess(IHttpResponse response, ICallback callback);
}
