package nl.imine.discord.voice.socket.messages;

import nl.imine.discord.voice.socket.VoiceOpcode;
import nl.imine.discord.voice.socket.VoicePayload;

public class AckVoiceMessage extends VoicePayload {

    private static final VoiceOpcode code = VoiceOpcode.ACK;

    private long nonce;

    public AckVoiceMessage() {

    }

    public AckVoiceMessage(Integer nonce) {
        this.nonce = nonce;
    }

    public long getNonce() {
        return nonce;
    }

    @Override
    public VoiceOpcode getOpcode() {
        return code;
    }
}
