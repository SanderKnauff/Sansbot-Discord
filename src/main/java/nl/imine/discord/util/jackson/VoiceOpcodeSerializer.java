package nl.imine.discord.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nl.imine.discord.voice.socket.VoiceOpcode;

import java.io.IOException;

public class VoiceOpcodeSerializer extends StdSerializer<VoiceOpcode> {

    public VoiceOpcodeSerializer() {
        super(VoiceOpcode.class);
    }

    @Override
    public void serialize(VoiceOpcode value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getCode());
    }
}
