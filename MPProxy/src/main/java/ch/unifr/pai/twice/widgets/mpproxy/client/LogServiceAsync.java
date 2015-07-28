package ch.unifr.pai.twice.widgets.mpproxy.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LogServiceAsync {
	void log(String result, AsyncCallback<Void> callback);

}
