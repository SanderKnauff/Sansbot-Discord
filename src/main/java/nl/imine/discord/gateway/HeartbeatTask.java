package nl.imine.discord.gateway;

import org.eclipse.jetty.websocket.api.Session;

import nl.imine.discord.gateway.messages.HeartbeatMessage;

public class HeartbeatTask implements Runnable {

	private final Session session;
	private boolean acknowledged = true;
	private int sequence = -1;

	public HeartbeatTask(Session session) {
		this.session = session;
	}

	@Override
	public void run() {
		if (acknowledged) {
			session.getRemote().sendStringByFuture(new HeartbeatMessage(sequence).createMessage().toJSONString());
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

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
