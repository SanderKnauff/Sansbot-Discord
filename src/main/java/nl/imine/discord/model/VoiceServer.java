package nl.imine.discord.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoiceServer {

    @JsonProperty("guild_id")
    private String guildId;
    private String token;
    private String endpoint;

    public VoiceServer() {
    }

    public VoiceServer(String guildId, String token, String endpoint) {
        this.guildId = guildId;
        this.token = token;
        this.endpoint = endpoint;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "VoiceServer{" +
                "guildId='" + guildId + '\'' +
                ", token='" + token + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
