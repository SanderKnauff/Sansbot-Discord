package nl.imine.discord.event.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.event.Event;
import nl.imine.discord.model.Guild;
import nl.imine.discord.model.VoiceState;

public class VoiceStateUpdateEvent extends Event {

    @JsonProperty("d")
    private VoiceState voiceState;

    public VoiceStateUpdateEvent() {
    }

    public VoiceStateUpdateEvent(VoiceState voiceState) {
        this.voiceState = voiceState;
    }

    public VoiceState getVoiceState() {
        return voiceState;
    }

    public void setVoiceState(VoiceState voiceState) {
        this.voiceState = voiceState;
    }
}
