package nl.imine.discord.voice.socket;

import nl.imine.discord.voice.socket.messages.*;

public enum VoiceOpcode {


    IDENTIFY(0, IdentifyVoiceMessage.class),
    VOICE_READY(2, VoiceReadyMessage.class),
    HEARTBEAT(3, HeartbeatVoiceMessage.class),
    ACK(6, AckVoiceMessage.class),
    HELLO(8, HelloVoiceMessage.class);

    private final int code;
    private final Class type;

    VoiceOpcode(int code, Class type) {
        this.code = code;
        this.type = type;
    }

    public static VoiceOpcode getOpcode(int code) {
        for (VoiceOpcode voiceOpcode : values()) {
            if (code == voiceOpcode.getCode()) {
                return voiceOpcode;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public Class getType() {
        return type;
    }
}
