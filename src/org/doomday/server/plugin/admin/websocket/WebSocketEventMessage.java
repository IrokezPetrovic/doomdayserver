package org.doomday.server.plugin.admin.websocket;

public class WebSocketEventMessage {
	private final String key;	
	private final Object payload;
	public WebSocketEventMessage(String key, Object payload) {
		super();
		this.key = key;
		this.payload = payload;
	}
	public String getKey() {
		return key;
	}
	public Object getPayload() {
		return payload;
	}
	
	
	
	
}
