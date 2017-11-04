package nl.imine.discord.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import nl.imine.discord.model.Game;

import java.io.IOException;

public class GameTypeSerializer extends StdSerializer<Game.GameType> {

    public GameTypeSerializer() {
        super(Game.GameType.class);
    }

    @Override
    public void serialize(Game.GameType value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.ordinal());
    }
}
