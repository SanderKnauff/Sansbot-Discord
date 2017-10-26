package nl.imine.discord.event.gateway;

public enum EventType {

	GUILD_CREATE(null),
	MESSAGE_CREATE(MessageCreateEvent.class),
	MESSAGE_DELETE(null),
	MESSAGE_REACTION_ADD(null),
	READY(null),
	TYPING_START(null),
	PRESENCE_UPDATE(null);

	private Class eventClass;

	EventType(Class eventClass) {
		this.eventClass = eventClass;
	}

	public Class getEventClass() {
		return eventClass;
	}

}
