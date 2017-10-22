package nl.imine.discord.gateway.messages;

import org.json.simple.JSONObject;

public class HeartbeatMessage extends WebSocketMessage {

	private static final Opcode code = Opcode.HEARTBEAT;

	//d key
	private final int sequence;

	public HeartbeatMessage(int sequence) {
		this.sequence = sequence;
	}

	public static Opcode getCode() {
		return code;
	}

	public int getSequence() {
		return sequence;
	}

	@Override
	public JSONObject createMessage() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("op", code.getCode());
		jsonObject.put("d", (sequence != -1 ? sequence : "null"));
		return jsonObject;
	}

}
