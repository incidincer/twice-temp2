package ch.unifr.pai.twice.widgets.mpproxy.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("logger")
public interface LogService extends RemoteService {
	public void log(String result);
}
