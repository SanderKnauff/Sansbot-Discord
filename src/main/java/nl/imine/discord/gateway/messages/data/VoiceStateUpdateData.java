package nl.imine.discord.gateway.messages.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoiceStateUpdateData {

    @JsonProperty("guild_id")
    private final String guildId;
    @JsonProperty("channel_id")
    private final String channelId;
    @JsonProperty("self_mute")
    private final boolean selfMute;
    @JsonProperty("self_deaf")
    private final boolean selfDeaf;

    public VoiceStateUpdateData(String guildId, String channelId, boolean selfMute, boolean selfDeaf) {
        this.guildId = guildId;
        this.channelId = channelId;
        this.selfMute = selfMute;
        this.selfDeaf = selfDeaf;
    }

    public String getGuildId() {
        return guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public boolean isSelfMute() {
        return selfMute;
    }

    public boolean isSelfDeaf() {
        return selfDeaf;
    }
}
