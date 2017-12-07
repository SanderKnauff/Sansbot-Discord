package nl.imine.discord.voice.socket.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.voice.socket.VoiceOpcode;
import nl.imine.discord.voice.socket.VoicePayload;

public class HeartbeatVoiceMessage extends VoicePayload {

    private static final VoiceOpcode code = VoiceOpcode.HEARTBEAT;

    private long intervalNonce;

    public HeartbeatVoiceMessage() {

    }

    public HeartbeatVoiceMessage(long intervalNonce) {
        this.intervalNonce = intervalNonce;
    }


    @Override
    public VoiceOpcode getOpcode() {
        return code;
    }

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty("d")
    public Long getIntervalNonce() {
        return this.intervalNonce;
    }
}
