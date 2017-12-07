package nl.imine.discord.event.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.event.Event;
import nl.imine.discord.model.Message;

public class MessageCreateEvent extends Event {

    @JsonProperty("d")
    private Message message;

    public MessageCreateEvent(){

    }

    public MessageCreateEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
