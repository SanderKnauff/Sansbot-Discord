package nl.imine.discord.event.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.event.Event;
import nl.imine.discord.model.VoiceServer;
import nl.imine.discord.model.VoiceState;

public class VoiceServerUpdateEvent extends Event {

    @JsonProperty("d")
    private VoiceServer voiceServer;

    public VoiceServerUpdateEvent() {
    }

    public VoiceServerUpdateEvent(VoiceServer voiceServer) {
        this.voiceServer = voiceServer;
    }

    public VoiceServer getVoiceServer() {
        return voiceServer;
    }

    public void setVoiceServer(VoiceServer voiceServer) {
        this.voiceServer = voiceServer;
    }
}
