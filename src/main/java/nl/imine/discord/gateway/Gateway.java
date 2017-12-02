package nl.imine.discord.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.util.Rest;
import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Property;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;

@Component
public class Gateway {

    private static final Logger logger = LoggerFactory.getLogger(Gateway.class);

    private final Rest rest;
    private final ObjectMapper objectMapper;
    private final EventDispatcher eventDispatcher;
    private final String botToken;

    public Gateway(Rest rest, ObjectMapper objectMapper, EventDispatcher eventDispatcher, @Property("discord.client.token") String botToken) {
        this.rest = rest;
        this.objectMapper = objectMapper;
        this.eventDispatcher = eventDispatcher;
        this.botToken = botToken;
    }

    @PostConstruct
    public void postConstruct() {
        try {
            this.openWebSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openWebSocket() throws Exception {
        JsonNode ret = rest.getSync("https://discordapp.com/api/gateway", new HashMap<>());
        String url = ret.get("url").asText() + "/?v=6&encoding=json";
        logger.info("Received Gateway url: {}", url);

        SslContextFactory ssl = new SslContextFactory();
        WebSocketClient webSocket = new WebSocketClient(ssl);

        webSocket.getPolicy().setMaxBinaryMessageSize(Integer.MAX_VALUE);
        webSocket.getPolicy().setMaxTextMessageSize(Integer.MAX_VALUE);
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        request.setHeader("Authorization", "bot " + botToken);
        webSocket.start();
        GatewaySocket gatewaySocket = new GatewaySocket(objectMapper, eventDispatcher, botToken);
        webSocket.connect(gatewaySocket, new URI(url), request);

        logger.info("WebSocket is connected: {}", gatewaySocket.isConnected());
    }
}