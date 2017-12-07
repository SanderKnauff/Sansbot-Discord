package nl.imine.discord.event.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.event.Event;
import nl.imine.discord.model.Guild;

public class GuildCreatedEvent extends Event {

    @JsonProperty("d")
    private Guild guild;

    public GuildCreatedEvent() {

    }

    public GuildCreatedEvent(Guild guild) {
        this.guild = guild;
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
