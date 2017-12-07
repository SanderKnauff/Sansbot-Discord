package nl.imine.discord.event.voice;

import nl.imine.discord.event.Event;
import org.eclipse.jetty.websocket.api.Session;

public class VoiceSocketConnectedEvent extends Event {

    private final Session session;

    public VoiceSocketConnectedEvent(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
