package nl.imine.discord.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.imine.discord.Sansbot;
import nl.imine.discord.gateway.messages.HeartbeatMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(HeartbeatTask.class);

	private final Session session;
	private boolean acknowledged = true;
	private Integer sequence = null;

	public HeartbeatTask(Session session) {
		this.session = session;
	}

	@Override
	public void run() {
		if (acknowledged) {
			try {
				HeartbeatMessage heartbeatMessage = new HeartbeatMessage(sequence);
				String message = Sansbot.objectMapper().writeValueAsString(heartbeatMessage);
				logger.debug("Sending Heartbeat(Opcode {}) to Discord with Sequence number {}", heartbeatMessage.getOpcode(), heartbeatMessage.getSequenceNumber());
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
