package nl.imine.discord.voice.socket.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.voice.socket.VoiceOpcode;
import nl.imine.discord.voice.socket.VoicePayload;
import nl.imine.discord.voice.socket.messages.data.IdentifyVoiceMessageData;

public class IdentifyVoiceMessage extends VoicePayload {


    private static final VoiceOpcode VOICE_OPCODE = VoiceOpcode.IDENTIFY;

    @JsonProperty("d")
    private IdentifyVoiceMessageData identifyVoiceMessageData;

    public IdentifyVoiceMessage() {

    }

    public IdentifyVoiceMessage(IdentifyVoiceMessageData identifyVoiceMessageData) {
        this.identifyVoiceMessageData = identifyVoiceMessageData;
    }

    public IdentifyVoiceMessageData getIdentifyVoiceMessageData() {
        return identifyVoiceMessageData;
    }

    public void setIdentifyVoiceMessageData(IdentifyVoiceMessageData identifyVoiceMessageData) {
        this.identifyVoiceMessageData = identifyVoiceMessageData;
    }

    @Override
    public VoiceOpcode getOpcode() {
        return VOICE_OPCODE;
    }
}
