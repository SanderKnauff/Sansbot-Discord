package nl.imine.discord.gateway.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.gateway.messages.data.IdentifyMessageData;

public class IdentifyMessage extends GatewayPayload {

    private static final GatewayOpcode GATEWAY_OPCODE = GatewayOpcode.IDENTIFY;

    @JsonProperty("d")
    private IdentifyMessageData identifyMessageData;

    public IdentifyMessage() {

    }

    public IdentifyMessage(Integer sequenceNumber, IdentifyMessageData identifyMessageData) {
        super(sequenceNumber);
        this.identifyMessageData = identifyMessageData;
    }

    public IdentifyMessageData getIdentifyMessageData() {
        return identifyMessageData;
    }

    public void setIdentifyMessageData(IdentifyMessageData identifyMessageData) {
        this.identifyMessageData = identifyMessageData;
    }

    @Override
    public GatewayOpcode getOpcode() {
        return GATEWAY_OPCODE;
    }
}
