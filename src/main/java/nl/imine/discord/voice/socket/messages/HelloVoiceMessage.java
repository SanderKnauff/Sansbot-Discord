package nl.imine.discord.voice.socket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.voice.socket.VoiceOpcode;
import nl.imine.discord.voice.socket.VoicePayload;
import nl.imine.discord.voice.socket.messages.data.HelloVoiceMessageData;

public class HelloVoiceMessage extends VoicePayload {

    private static final VoiceOpcode VOICE_OPCODE = VoiceOpcode.HELLO;

    @JsonProperty("d")
    private HelloVoiceMessageData data;

    public HelloVoiceMessage() {
    }

    public HelloVoiceMessage(HelloVoiceMessageData data) {
        this.data = data;
    }

    public HelloVoiceMessageData getData() {
        return data;
    }

    public void setData(HelloVoiceMessageData data) {
        this.data = data;
    }

    @Override
    public VoiceOpcode getOpcode() {
        return VOICE_OPCODE;
    }
}
