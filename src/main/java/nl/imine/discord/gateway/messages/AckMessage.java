package nl.imine.discord.gateway.messages;

public class AckMessage extends GatewayPayload {

    private static final Opcode code = Opcode.ACK;

    public AckMessage() {

    }

    public AckMessage(Integer sequenceNumber) {
        super(sequenceNumber);
    }

    @Override
    public Opcode getOpcode() {
        return code;
    }
}
