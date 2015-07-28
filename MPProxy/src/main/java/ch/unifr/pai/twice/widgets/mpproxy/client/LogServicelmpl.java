package ch.unifr.pai.twice.widgets.mpproxy.client;

import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@WebServlet("/miceproxy/logger")
public class LogServicelmpl extends RemoteServiceServlet implements LogService {

	private static final long serialVersionUID = 1L;
	Logger experimentLog = Logger.getLogger("experiment");

	@Override
	public void log(String result) {

		experimentLog.info(result);

	}

}
