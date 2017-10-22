package nl.imine.discord.gateway;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.event.EventType;
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
	private HeartbeatTask heartbeatTask;
	private String botToken;

	public WebSocketMessageHandler(Session session, String botToken) {
		this.session = session;
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
					logger.info("EventMessage({}): {}", eventMessage.getEventType(), eventMessage.getEventData().toJSONString());

					//TEMP
					if(EventType.MESSAGE_CREATE.equals(eventMessage.getEventType())) {
						if(eventMessage.getEventData().get("content").toString().equalsIgnoreCase("!WeVliegenErin")) {
							new Channel(String.valueOf(eventMessage.getEventData().get("channel_id")),
									null,
									null,
									0,
									null,
									null,
									null,
									false,
									null,
									0,
									1,
									null,
									null,
									null,
									null,
									null).sendMessage("PATAT!");
						}
					}
					//END TEMP
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