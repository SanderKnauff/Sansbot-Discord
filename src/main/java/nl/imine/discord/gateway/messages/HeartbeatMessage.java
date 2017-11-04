package nl.imine.discord.gateway.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HeartbeatMessage extends GatewayPayload {

    private static final Opcode code = Opcode.HEARTBEAT;

    public HeartbeatMessage() {

    }

    public HeartbeatMessage(Integer sequenceNumber) {
        super(sequenceNumber);
    }

    @Override
    public Opcode getOpcode() {
        return code;
    }

    @Override
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonProperty("d")
    public Integer getSequenceNumber() {
        return super.getSequenceNumber();
    }
}
