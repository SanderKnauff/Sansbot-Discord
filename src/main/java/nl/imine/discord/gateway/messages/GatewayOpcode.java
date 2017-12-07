package nl.imine.discord.gateway.messages;

public enum GatewayOpcode {

    EVENT(0, EventMessage.class),
    HEARTBEAT(1, HeartbeatMessage.class),
    IDENTIFY(2, IdentifyMessage.class),
    VOICE_STATE_UPDATE(4, VoiceStateUpdateMessage.class),
    HELLO(10, HelloMessage.class),
    ACK(11, AckMessage.class);

    private final int code;
    private final Class type;

    GatewayOpcode(int code, Class type) {
        this.code = code;
        this.type = type;
    }

    public static GatewayOpcode getOpcode(int code) {
        for (GatewayOpcode gatewayOpcode : values()) {
            if (code == gatewayOpcode.getCode()) {
                return gatewayOpcode;
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
