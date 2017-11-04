package nl.imine.discord.gateway.messages;

public enum Opcode {

    EVENT(0, EventMessage.class),
    HEARTBEAT(1, HeartbeatMessage.class),
    IDENTIFY(2, IdentifyMessage.class),
    VOICE_STATUS_UPDATE(4, null),
    HELLO(10, HelloMessage.class),
    ACK(11, AckMessage.class);

    private final int code;
    private final Class type;

    Opcode(int code, Class type) {
        this.code = code;
        this.type = type;
    }

    public static Opcode getOpcode(int code) {
        for (Opcode opcode : values()) {
            if (code == opcode.getCode()) {
                return opcode;
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
