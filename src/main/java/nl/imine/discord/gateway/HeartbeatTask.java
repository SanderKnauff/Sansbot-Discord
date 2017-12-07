package nl.imine.discord.gateway;

import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.imine.discord.gateway.messages.HeartbeatMessage;

public class HeartbeatTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatTask.class);

	private final Session session;
	private final ObjectMapper objectMapper;
	private boolean acknowledged = true;
	private Integer sequence = null;

	public HeartbeatTask(Session session, ObjectMapper objectMapper) {
		this.session = session;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run() {
		if (acknowledged) {
			try {
				HeartbeatMessage heartbeatMessage = new HeartbeatMessage(sequence);
				String message = objectMapper.writeValueAsString(heartbeatMessage);
				logger.debug("Sending Heartbeat(GatewayOpcode {}) to Discord with Sequence number {}", heartbeatMessage.getOpcode(), heartbeatMessage.getSequenceNumber());
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

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
