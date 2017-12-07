package nl.imine.discord.gateway.messages;

public class AckMessage extends GatewayPayload {

    private static final GatewayOpcode code = GatewayOpcode.ACK;

    public AckMessage() {

    }

    public AckMessage(Integer sequenceNumber) {
        super(sequenceNumber);
    }

    @Override
    public GatewayOpcode getOpcode() {
        return code;
    }
}
