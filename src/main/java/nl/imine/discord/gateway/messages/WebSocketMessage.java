package nl.imine.discord.gateway.messages;

import org.json.simple.JSONObject;

public abstract class WebSocketMessage {

	public abstract JSONObject createMessage();
}
