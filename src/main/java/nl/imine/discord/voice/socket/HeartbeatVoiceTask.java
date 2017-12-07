package nl.imine.discord.voice.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.discord.voice.socket.messages.HeartbeatVoiceMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class HeartbeatVoiceTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatVoiceTask.class);
    private static final Random random = new Random();

    private final Session session;
    private final ObjectMapper objectMapper;
    private long lastNonce;
    private boolean acknowledged = true;

    public HeartbeatVoiceTask(Session session, ObjectMapper objectMapper) {
        this.session = session;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run() {
        if (acknowledged) {
            try {
                lastNonce = random.nextLong();
                HeartbeatVoiceMessage heartbeatMessage = new HeartbeatVoiceMessage(new Random().nextLong());
                String message = objectMapper.writeValueAsString(heartbeatMessage);
                logger.debug("Sending Heartbeat(GatewayOpcode {}) to Discord Voice socket. Message: {}", heartbeatMessage.getOpcode(), message);
                session.getRemote().sendStringByFuture(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            acknowledged = false;
        } else {
            if (session.isOpen()) {
                session.close(1002, "Previous Heartbeat was not acknowledged");
            }
        }
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void acknowledge(long nonce) {
        if(nonce == lastNonce) {
            this.acknowledged = acknowledged;
        }
    }

}
