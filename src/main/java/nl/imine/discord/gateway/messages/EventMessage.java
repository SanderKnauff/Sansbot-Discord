package nl.imine.discord.gateway.messages;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.event.EventType;

public class EventMessage extends WebSocketMessage {

	private static Logger logger = LoggerFactory.getLogger(EventMessage.class);

	private final EventType eventType;
	private final JSONObject eventData;

	public EventMessage(EventType eventType, JSONObject eventData) {
		this.eventType = eventType;
		this.eventData = eventData;
	}

	public EventType getEventType() {
		return eventType;
	}

	public JSONObject getEventData() {
		return eventData;
	}

	@Override
	public JSONObject createMessage() {
		return null;
	}

	public static EventMessage fromJSON(JSONObject jsonObject) {
		EventType t = null;
		try {
			t = EventType.valueOf(jsonObject.get("t").toString());
		} catch (Exception e) {
			logger.warn("Unknown Event type: {}", jsonObject.get("t").toString());
		}
		return new EventMessage(t, (JSONObject) jsonObject.get("d"));
	}
}
