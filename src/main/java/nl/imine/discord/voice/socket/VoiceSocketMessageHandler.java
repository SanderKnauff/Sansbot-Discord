package nl.imine.discord.voice.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.voice.socket.messages.AckVoiceMessage;
import nl.imine.discord.voice.socket.messages.HelloVoiceMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class VoiceSocketMessageHandler {

    private static Logger logger = LoggerFactory.getLogger(VoiceSocketMessageHandler.class);
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    private final Session session;
    private final ObjectMapper objectMapper;
    private final EventDispatcher eventDispatcher;
    private HeartbeatVoiceTask heartbeatVoiceTask;
    private String botToken;

    public VoiceSocketMessageHandler(Session session, ObjectMapper objectMapper, EventDispatcher eventDispatcher, String botToken) {
        this.session = session;
        this.objectMapper = objectMapper;
        this.eventDispatcher = eventDispatcher;
        this.heartbeatVoiceTask = new HeartbeatVoiceTask(session, objectMapper);
        this.botToken = botToken;
    }

    public void handleMessage(VoicePayload payload) {
        logger.trace("voiceOpcode: {}", payload.getOpcode());
        VoiceOpcode voiceOpcode = payload.getOpcode();
        if (voiceOpcode != null) {
            switch (voiceOpcode) {
                case HELLO:
                    HelloVoiceMessage helloMessage = (HelloVoiceMessage) payload;
                    executorService.scheduleAtFixedRate(heartbeatVoiceTask, 0, (long) (helloMessage.getData().getHeartbeatInterval() * 0.75), TimeUnit.MILLISECONDS);
                    break;
                case ACK:
                    heartbeatVoiceTask.acknowledge(((AckVoiceMessage)payload).getNonce());
                    break;
            }
        } else {
            logger.warn("Received unknown voiceOpcode ({})", voiceOpcode);
        }
    }
}
