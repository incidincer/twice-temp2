package ch.unifr.pai.twice.multipointer.commons.client.events;


import ch.unifr.pai.twice.comm.serverPush.client.DiscardingRemoteEvent;
import ch.unifr.pai.twice.comm.serverPush.client.RemoteEventHandler;
import ch.unifr.pai.twice.multipointer.commons.client.events.RemoteKeyUpEvent.Handler;

public abstract class RemoteKeyUpEvent extends DiscardingRemoteEvent<Handler> {
	
	public static final Type<Handler> TYPE = new Type<Handler>();
	public static interface Handler extends RemoteEventHandler<RemoteKeyUpEvent> {
	}
	
	public Integer keyCode;

}
