package nl.imine.discord.voice.socket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.voice.socket.VoiceOpcode;
import nl.imine.discord.voice.socket.VoicePayload;
import nl.imine.discord.voice.socket.messages.data.VoiceReadyMessageData;

public class VoiceReadyMessage extends VoicePayload {

    private static final VoiceOpcode VOICE_OPCODE = VoiceOpcode.VOICE_READY;

    @JsonProperty("d")
    private VoiceReadyMessageData data;

    public VoiceReadyMessage() {
    }

    public VoiceReadyMessage(VoiceReadyMessageData data) {
        this.data = data;
    }

    public VoiceReadyMessageData getData() {
        return data;
    }

    public void setData(VoiceReadyMessageData data) {
        this.data = data;
    }

    @Override
    public VoiceOpcode getOpcode() {
        return VOICE_OPCODE;
    }
}
