package nl.imine.discord.gateway;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.gateway.messages.GatewayPayload;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class GatewaySocketAdapter extends WebSocketAdapter {

    private static Logger logger = LoggerFactory.getLogger(GatewaySocketAdapter.class);

    private final ObjectMapper objectMapper;
    private final CountDownLatch closeLatch;
    private final EventDispatcher eventDispatcher;
    private WebSocketMessageHandler webSocketMessageHandler;
    private Session session;
    private String botToken;

    public GatewaySocketAdapter(ObjectMapper objectMapper, EventDispatcher eventDispatcher, String botToken) {
        this.closeLatch = new CountDownLatch(1);
        this.objectMapper = objectMapper;
        this.eventDispatcher = eventDispatcher;
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
        this.webSocketMessageHandler = new WebSocketMessageHandler(session, objectMapper, eventDispatcher, botToken);
    }

    @Override
    public void onWebSocketText(String message) {
        logger.trace("Received Message: {}", message);
        try {
            GatewayPayload gatewayPayload = objectMapper.readValue(message, GatewayPayload.class);
            webSocketMessageHandler.handleMessage(gatewayPayload);
        } catch (JsonParseException e) {
            logger.warn("Json parsing failed: ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public Session getSession() {
        return session;
    }

}
