package nl.imine.discord.voice.socket;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class VoicePayload {

    public VoicePayload() {

    }

    @JsonProperty("op")
    public abstract VoiceOpcode getOpcode();

}
