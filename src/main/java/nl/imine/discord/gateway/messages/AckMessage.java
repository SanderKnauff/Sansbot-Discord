package nl.imine.discord.gateway.messages;

import org.json.simple.JSONObject;

public class AckMessage extends WebSocketMessage {

	private static final Opcode code = Opcode.ACK;

	public AckMessage() {
	}

	@Override
	public JSONObject createMessage() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("op", code.getCode());
		return jsonObject;
	}

	public static AckMessage fromJSON(JSONObject jsonObject) {
		return new AckMessage();
	}
}
