package org.doomday.server.web.admin.websocket;

public class WebSocketEventMessage {
	private final String source;
	private final String type;
	private final Object payload;
	public WebSocketEventMessage(String source, String type, Object payload) {
		super();
		this.source = source;
		this.type = type;
		this.payload = payload;
	}
	public String getSource() {
		return source;
	}
	public String getType() {
		return type;
	}
	public Object getPayload() {
		return payload;
	}
	
	
	
	
}
