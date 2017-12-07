package nl.imine.discord.gateway.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.gateway.messages.data.VoiceStateUpdateData;

public class VoiceStateUpdateMessage extends GatewayPayload {

    private static final GatewayOpcode code = GatewayOpcode.VOICE_STATE_UPDATE;

    @JsonProperty("d")
    private VoiceStateUpdateData voiceStateUpdateData;

    public VoiceStateUpdateMessage() {

    }

    public VoiceStateUpdateMessage(Integer sequenceNumber, VoiceStateUpdateData voiceStateUpdateData) {
        super(sequenceNumber);
        this.voiceStateUpdateData = voiceStateUpdateData;
    }

    public static GatewayOpcode getCode() {
        return code;
    }

    public VoiceStateUpdateData getVoiceStateUpdateData() {
        return voiceStateUpdateData;
    }

    public void setVoiceStateUpdateMessage(VoiceStateUpdateData voiceStateUpdateData) {
        this.voiceStateUpdateData = voiceStateUpdateData;
    }

    @Override
    public GatewayOpcode getOpcode() {
        return code;
    }
}
