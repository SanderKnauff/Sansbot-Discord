package nl.imine.discord.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nl.imine.discord.gateway.messages.Opcode;

import java.io.IOException;

public class OpcodeSerializer extends StdSerializer<Opcode> {

    public OpcodeSerializer() {
        super(Opcode.class);
    }

    @Override
    public void serialize(Opcode value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getCode());
    }
}
