package nl.imine.discord.util.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import nl.imine.discord.gateway.messages.GatewayPayload;
import nl.imine.discord.voice.socket.VoiceOpcode;
import nl.imine.discord.voice.socket.VoicePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class VoiceSocketPayloadDeserializer extends StdDeserializer<VoicePayload> {

    private static Logger logger = LoggerFactory.getLogger(VoiceSocketPayloadDeserializer.class);

    public VoiceSocketPayloadDeserializer() {
        super(GatewayPayload.class);
    }

    @Override
    public VoicePayload deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode jsonNode = codec.readTree(jsonParser);
        VoiceOpcode voiceOpcode = null;
        for (VoiceOpcode code : VoiceOpcode.values()) {
            if (code.getCode() == jsonNode.get("op").asInt()) {
                voiceOpcode = code;
            }
        }
        logger.info("{}", jsonNode.toString());
        return (VoicePayload) codec
                .readValue(
                        jsonNode.traverse(),
                        voiceOpcode.getType());
    }
}
