package nl.imine.discord.gateway;

import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewaySocket extends WebSocketAdapter {

	private static Logger logger = LoggerFactory.getLogger(GatewaySocket.class);

	private final CountDownLatch closeLatch;
	private WebSocketMessageHandler webSocketMessageHandler;
	private Session session;
	private String botToken;

	public GatewaySocket(String botToken) {
		this.closeLatch = new CountDownLatch(1);
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
		this.webSocketMessageHandler = new WebSocketMessageHandler(session, botToken);
	}

	@Override
	public void onWebSocketText(String message) {
		logger.trace("Received Message: {}", message);
		try {
			webSocketMessageHandler.handleMessage((JSONObject) new JSONParser().parse(message));
		} catch (ParseException e) {
			logger.warn("Received non-json message: {}", message);
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
}
