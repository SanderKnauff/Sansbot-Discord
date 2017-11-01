package nl.imine.discord.gateway;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.imine.discord.event.Event;
import nl.imine.discord.event.EventDispatcher;

import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.event.gateway.EventType;
import nl.imine.discord.gateway.messages.EventMessage;
import nl.imine.discord.gateway.messages.HelloMessage;
import nl.imine.discord.gateway.messages.IdentifyMessage;
import nl.imine.discord.gateway.messages.Opcode;
import nl.imine.discord.logic.Channel;
import nl.imine.discord.model.ConnectionProperties;
import nl.imine.discord.model.Game;
import nl.imine.discord.model.UserStatus;

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

	public void handleMessage(JSONObject message) {
		Integer opcodeInt = Integer.valueOf(String.valueOf(message.get("op")));
		logger.trace("Opcode: {}", opcodeInt);
		Opcode opcode = Opcode.getOpcode(opcodeInt);
		if (opcode != null) {
			Object sequenceObject = message.get("s");
			if (sequenceObject != null) {
				heartbeatTask.setSequence(Integer.valueOf(String.valueOf(sequenceObject)));
			}
			switch (opcode) {
				case EVENT:
					EventMessage eventMessage = EventMessage.fromJSON(message);
					try {
						if (eventMessage.getEventType().getEventClass() != null) {
							ObjectMapper objectMapper = new ObjectMapper();
							objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							eventDispatcher.callEvent((Event) objectMapper.readValue(message.toJSONString(), eventMessage.getEventType().getEventClass()));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					logger.info("EventMessage({}): {}", eventMessage.getEventType(), eventMessage.getEventData().toJSONString());
					break;
				case HELLO:
					HelloMessage helloMessage = HelloMessage.fromJSON((JSONObject) message.get("d"));
					executorService.scheduleAtFixedRate(heartbeatTask, 0, helloMessage.getHeartbeatInterval(), TimeUnit.MILLISECONDS);
					IdentifyMessage identifyMessage = new IdentifyMessage(botToken,
							new ConnectionProperties(System.getProperty("os.name"), "Sansbot", "Sansbot"),
							new UserStatus(0,
									new Game("met zijn stokje", Game.GameType.GAME, null),
									"online",
									false
							)
					);
					JSONObject identifyPayload = new JSONObject();
					identifyPayload.put("op", identifyMessage.getOpcode().getCode());
					identifyPayload.put("d", identifyMessage.createMessage());
					JSONObject identifyMessageMessage = identifyMessage.createMessage();
					logger.trace("Identifying: {}", identifyMessageMessage);
					session.getRemote().sendStringByFuture(identifyPayload.toJSONString());
					break;
				case ACK:
					heartbeatTask.setAcknowledged(true);
					break;
			}
		} else {
			logger.warn("Received unknown opcode ({})", opcodeInt);
		}
	}
}
