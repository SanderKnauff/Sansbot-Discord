package nl.imine.discord.gateway.messages;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class HelloMessage extends WebSocketMessage {

	private static Opcode opcode = Opcode.HELLO;

	private final int heartbeatInterval;
	private final String[] trace;

	public HelloMessage(int heartbeatInterval, String[] trace) {
		this.heartbeatInterval = heartbeatInterval;
		this.trace = trace;
	}

	public static Opcode getOpcode() {
		return opcode;
	}

	public static void setOpcode(Opcode opcode) {
		HelloMessage.opcode = opcode;
	}

	public int getHeartbeatInterval() {
		return heartbeatInterval;
	}

	public String[] getTrace() {
		return trace;
	}

	@Override
	public JSONObject createMessage() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("heartbeat_interval", heartbeatInterval);
		jsonObject.put("_trace", trace);
		return jsonObject;
	}

	public static HelloMessage fromJSON(JSONObject jsonObject) {
		JSONArray traceArray = (JSONArray) jsonObject.get("_trace");
		String[] trace = new String[traceArray.size()];
		for (int i = 0; i < traceArray.size(); i++) {
			trace[i] = (String) traceArray.get(i);
		};
		return new HelloMessage(Integer.valueOf(String.valueOf(jsonObject.get("heartbeat_interval"))),
				trace);
	}
}
