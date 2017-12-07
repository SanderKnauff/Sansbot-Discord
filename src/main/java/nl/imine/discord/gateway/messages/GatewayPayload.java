package nl.imine.discord.gateway.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class GatewayPayload {

    private Integer sequenceNumber;

    public GatewayPayload() {

    }

    public GatewayPayload(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @JsonProperty("op")
    public abstract GatewayOpcode getOpcode();

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

}
