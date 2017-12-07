package nl.imine.discord.gateway.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.event.Event;
import nl.imine.discord.event.gateway.EventType;

public class EventMessage extends GatewayPayload {

    private static final GatewayOpcode code = GatewayOpcode.EVENT;

    @JsonProperty("t")
    private EventType eventType;
    @JsonProperty("d")
    private Event payloadData;

    public EventMessage() {

    }

    public EventMessage(Integer sequenceNumber, EventType eventType, Event payloadData) {
        super(sequenceNumber);
        this.eventType = eventType;
        this.payloadData = payloadData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Event getPayloadData() {
        return payloadData;
    }

    public void setPayloadData(Event payloadData) {
        this.payloadData = payloadData;
    }

    @Override
    public GatewayOpcode getOpcode() {
        return code;
    }
}
