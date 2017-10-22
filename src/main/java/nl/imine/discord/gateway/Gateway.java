package nl.imine.discord.gateway;

import java.net.URI;
import java.util.HashMap;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.util.Rest;

public class Gateway {

	private static final Logger logger = LoggerFactory.getLogger(Gateway.class);

	private Rest rest;

	public Gateway(Rest rest) {
		this.rest = rest;
	}

	public void openWebSocket(String botToken) throws Exception {
		JSONObject ret = rest.getSync("https://discordapp.com/api/gateway", new HashMap<>());
		String url = (String) ret.get("url") + "/?v=6&encoding=json";
		logger.info("Received Gateway url: {}", url);

		SslContextFactory ssl = new SslContextFactory();
		WebSocketClient webSocket = new WebSocketClient(ssl);

		webSocket.getPolicy().setMaxBinaryMessageSize(Integer.MAX_VALUE);
		webSocket.getPolicy().setMaxTextMessageSize(Integer.MAX_VALUE);
		ClientUpgradeRequest request = new ClientUpgradeRequest();
		request.setHeader("Authorization", "bot " + botToken);
		webSocket.start();
		GatewaySocket gatewaySocket = new GatewaySocket(botToken);
		webSocket.connect(gatewaySocket, new URI(url), request);

		logger.info("WebSocket is connected: {}", gatewaySocket.isConnected());
	}
}