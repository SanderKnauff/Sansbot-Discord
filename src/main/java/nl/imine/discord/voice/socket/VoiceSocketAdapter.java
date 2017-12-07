package nl.imine.discord.voice.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.event.voice.VoiceSocketConnectedEvent;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class VoiceSocketAdapter extends WebSocketAdapter {

    private static Logger logger = LoggerFactory.getLogger(VoiceSocketAdapter.class);

    private final ObjectMapper objectMapper;
    private final CountDownLatch closeLatch;
    private final EventDispatcher eventDispatcher;
    private VoiceSocketMessageHandler voiceSocketMessageHandler;
    private Session session;
    private String botToken;

    public VoiceSocketAdapter(ObjectMapper objectMapper, EventDispatcher eventDispatcher, String botToken) {
        this.closeLatch = new CountDownLatch(1);
        this.objectMapper = objectMapper;
        this.eventDispatcher = eventDispatcher;
        this.botToken = botToken;
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        logger.warn("Connection closed: {} - {}", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @Override
    public void onWebSocketConnect(Session session) {
        logger.info("Connected: {}", session);
        this.session = session;
        this.voiceSocketMessageHandler = new VoiceSocketMessageHandler(session, objectMapper, eventDispatcher, botToken);
        eventDispatcher.callEvent(new VoiceSocketConnectedEvent(session));
    }

    @Override
    public void onWebSocketText(String message) {
        logger.info("Received Message: {}", message);
        try {
            VoicePayload voicePayload = objectMapper.readValue(message, VoicePayload.class);
            voiceSocketMessageHandler.handleMessage(voicePayload);
        } catch (IOException e) {
            logger.warn("Json parsing failed: ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    @Override
    public void onWebSocketBinary(byte[] payload, int offset, int len) {
        logger.warn("Received Binary: {}, {}, {}", len, offset, payload);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        logger.warn("Websocket error: ({}: {})", cause.getClass().getSimpleName(), cause.getMessage());
    }

    @Override
    public boolean isConnected() {
        return super.isConnected();
    }

    @Override
    public Session getSession() {
        return session;
    }

}
