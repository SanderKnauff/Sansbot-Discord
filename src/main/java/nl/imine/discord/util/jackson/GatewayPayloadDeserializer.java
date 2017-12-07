package nl.imine.discord.util.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import nl.imine.discord.event.Event;
import nl.imine.discord.event.gateway.EventType;
import nl.imine.discord.gateway.messages.EventMessage;
import nl.imine.discord.gateway.messages.GatewayOpcode;
import nl.imine.discord.gateway.messages.GatewayPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GatewayPayloadDeserializer extends StdDeserializer<GatewayPayload> {

    private static Logger logger = LoggerFactory.getLogger(GatewayPayloadDeserializer.class);

    public GatewayPayloadDeserializer() {
        super(GatewayPayload.class);
    }

    @Override
    public GatewayPayload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);
        deserializationContext.setAttribute("t", jsonNode.get("t").asText());
        GatewayOpcode gatewayOpcode = null;
        for (GatewayOpcode code : GatewayOpcode.values()) {
            if (code.getCode() == jsonNode.get("op").asInt()) {
                gatewayOpcode = code;
            }
        }
        logger.info("{}", jsonNode.toString());
        if (GatewayOpcode.EVENT.equals(gatewayOpcode)) {
            EventType eventType = EventType.getFromName(jsonNode.get("t").asText());
            Event d = null;
            if (eventType != null && eventType.getEventClass() != null) {
                d = (Event) codec.treeToValue(jsonNode, eventType.getEventClass());
            }
            return new EventMessage(jsonNode.get("s").asInt(),
                    eventType,
                    d);
        }
        return (GatewayPayload) codec
                .readValue(
                        jsonNode.traverse(),
                        gatewayOpcode.getType());
    }
}
