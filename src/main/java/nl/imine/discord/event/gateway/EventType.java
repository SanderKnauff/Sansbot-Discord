package nl.imine.discord.event.gateway;

public enum EventType {

	GUILD_CREATE(GuildCreatedEvent.class),
	MESSAGE_CREATE(MessageCreateEvent.class),
	MESSAGE_DELETE(null),
	MESSAGE_UPDATE(null),
	MESSAGE_REACTION_ADD(null),
	READY(null),
	TYPING_START(null),
	PRESENCE_UPDATE(null),
	VOICE_STATE_UPDATE(VoiceStateUpdateEvent.class),
	VOICE_SERVER_UPDATE(VoiceServerUpdateEvent.class);

	private Class eventClass;

	EventType(Class eventClass) {
		this.eventClass = eventClass;
	}

	public Class getEventClass() {
		return eventClass;
	}

	public static EventType getFromName(String name) {
		EventType returnType = null;
		for (EventType eventType : values()) {
			if (eventType.name().equals(name)) {
				returnType = eventType;
				break;
			}
		}
		return returnType;
	}

}
