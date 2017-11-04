package nl.imine.discord.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.imine.discord.Sansbot;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.gateway.messages.*;
import nl.imine.discord.gateway.messages.data.IdentifyMessageData;
import nl.imine.discord.model.ConnectionProperties;
import nl.imine.discord.model.Game;
import nl.imine.discord.model.UserStatus;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebSocketMessageHandler {

    private static Logger logger = LoggerFactory.getLogger(WebSocketMessageHandler.class);
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

    private final Session session;
    private final EventDispatcher eventDispatcher;
    private HeartbeatTask heartbeatTask;
    private String botToken;

    public WebSocketMessageHandler(Session session, EventDispatcher eventDispatcher, String botToken) {
        this.session = session;
        this.eventDispatcher = eventDispatcher;
        this.heartbeatTask = new HeartbeatTask(session);
        this.botToken = botToken;
    }

    public void handleMessage(GatewayPayload payload) {
        logger.trace("Opcode: {}", payload.getOpcode());
        Opcode opcode = payload.getOpcode();
        if (opcode != null) {
            Integer sequenceNumber = payload.getSequenceNumber();
            if (sequenceNumber != null) {
                heartbeatTask.setSequence(sequenceNumber);
            }
            switch (opcode) {
                case EVENT:
                    EventMessage eventMessage = (EventMessage) payload;
                    if (eventMessage.getEventType().getEventClass() != null) {
                        eventDispatcher.callEvent(((EventMessage) payload).getPayloadData());
                    }
                    logger.info("EventMessage({}): {}", eventMessage.getEventType(), eventMessage);
                    break;
                case HELLO:
                    HelloMessage helloMessage = (HelloMessage) payload;
                    executorService.scheduleAtFixedRate(heartbeatTask, 0, helloMessage.getData().getHeartbeatInterval(), TimeUnit.MILLISECONDS);
                    IdentifyMessage identifyMessage = new IdentifyMessage(
                            sequenceNumber,
                            new IdentifyMessageData(
                                    botToken,
                                    new ConnectionProperties(System.getProperty("os.name"), "Sansbot", "Sansbot"),
                                    new UserStatus(0,
                                            new Game("met zijn stokje", Game.GameType.GAME, null),
                                            "online",
                                            false
                                    ))
                    );

                    try {
                        String text = Sansbot.objectMapper().writeValueAsString(identifyMessage);
                        logger.info("Identifying: {}", text);
                        session.getRemote().sendStringByFuture(text);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
                case ACK:
                    heartbeatTask.setAcknowledged(true);
                    break;
            }
        } else {
            logger.warn("Received unknown opcode ({})", opcode);
        }
    }
}
