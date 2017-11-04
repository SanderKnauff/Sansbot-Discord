package nl.imine.discord.gateway.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.gateway.messages.data.HelloMessageData;

public class HelloMessage extends GatewayPayload {

    private static final Opcode opcode = Opcode.HELLO;

    @JsonProperty("d")
    private HelloMessageData data;

    public HelloMessage() {
    }

    public HelloMessage(Integer sequenceNumber, HelloMessageData data) {
        super(sequenceNumber);
        this.data = data;
    }

    public HelloMessageData getData() {
        return data;
    }

    public void setData(HelloMessageData data) {
        this.data = data;
    }

    @Override
    public Opcode getOpcode() {
        return opcode;
    }

}
