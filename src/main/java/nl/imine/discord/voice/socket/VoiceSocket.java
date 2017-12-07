package nl.imine.discord.voice.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.voice.socket.messages.IdentifyVoiceMessage;
import nl.imine.discord.voice.socket.messages.data.IdentifyVoiceMessageData;
import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Property;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

@Component
public class VoiceSocket {

    private static final Logger logger = LoggerFactory.getLogger(VoiceSocket.class);

    private final ObjectMapper objectMapper;
    private final EventDispatcher eventDispatcher;
    private final String botToken;

    private String voiceToken;
    private String sessionId;
    private String endpoint;
    private String guildId;
    private String userId;

    private VoiceSocketAdapter voiceSocketAdapter;

    public VoiceSocket(ObjectMapper objectMapper, EventDispatcher eventDispatcher, @Property("discord.client.token") String botToken) {
        this.objectMapper = objectMapper;
        this.eventDispatcher = eventDispatcher;
        this.botToken = botToken;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        if(endpoint != null && voiceSocketAdapter == null) {
            openWebSocket(sessionId, endpoint);
        }
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        if(sessionId != null && voiceSocketAdapter == null) {
            openWebSocket(sessionId, endpoint);
        }
    }

    public void openWebSocket(String sessionId, String endpoint) {
        try {
            String url = "ws://" + endpoint + "/?v=3&encoding=json";
            logger.info("Received VoiceSocket url: {}", url);

            SslContextFactory ssl = new SslContextFactory();
            WebSocketClient webSocket = new WebSocketClient(ssl);

            webSocket.getPolicy().setMaxBinaryMessageSize(Integer.MAX_VALUE);
            webSocket.getPolicy().setMaxTextMessageSize(Integer.MAX_VALUE);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            request.setHeader("Authorization", "bot " + botToken);
            webSocket.start();
            voiceSocketAdapter = new VoiceSocketAdapter(objectMapper, eventDispatcher, botToken);
            webSocket.connect(voiceSocketAdapter, new URI(url), request);

            logger.info("WebSocket is connected: {}", voiceSocketAdapter.isConnected());
        } catch (Exception e) {
            logger.error("Failed to connect to voiceSocket: ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    public void sendVoiceIdentifyMessage() {
        try {
            IdentifyVoiceMessageData identifyVoiceMessageData = new IdentifyVoiceMessageData(guildId, userId, sessionId, voiceToken);
            String payload = objectMapper.writeValueAsString(new IdentifyVoiceMessage(identifyVoiceMessageData));
            logger.debug("Send identify message: {}", payload);
            voiceSocketAdapter.getSession().getRemote().sendStringByFuture(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }    }


    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVoiceToken(String voiceToken) {
        this.voiceToken = voiceToken;
    }
}