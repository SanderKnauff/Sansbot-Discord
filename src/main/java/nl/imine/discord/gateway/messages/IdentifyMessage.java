package nl.imine.discord.gateway.messages;

import org.json.simple.JSONObject;

import nl.imine.discord.model.ConnectionProperties;
import nl.imine.discord.model.UserStatus;

public class IdentifyMessage extends WebSocketMessage {

	private static Opcode opcode = Opcode.IDENTIFY;

	private final String token;
	private final ConnectionProperties connectionProperties;
	private final boolean compress = false;
	private final int largeThreshold = 249;
	private final UserStatus status;

	public IdentifyMessage(String token, ConnectionProperties connectionProperties, UserStatus status) {
		this.token = token;
		this.connectionProperties = connectionProperties;
		this.status = status;
	}

	@Override
	public JSONObject createMessage() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		jsonObject.put("properties", connectionProperties.toJSON());
		jsonObject.put("compress", compress);
//		jsonObject.put("shard", "[0, 1]");
		jsonObject.put("large_threshold", largeThreshold);
		jsonObject.put("presence", status.toJSON());
		return jsonObject;
	}

	public static Opcode getOpcode() {
		return opcode;
	}
}
