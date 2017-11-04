package nl.imine.discord.gateway.messages.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.imine.discord.model.ConnectionProperties;
import nl.imine.discord.model.UserStatus;

public class IdentifyMessageData {

    private String token;
    private ConnectionProperties properties;
    private boolean compress = false;
    @JsonProperty("large_threshold")
    private int largeThreshold = 249;
    private UserStatus presence;

    public IdentifyMessageData() {
    }

    public IdentifyMessageData(String token, ConnectionProperties properties, UserStatus presence) {
        this.token = token;
        this.properties = properties;
        this.presence = presence;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ConnectionProperties getProperties() {
        return properties;
    }

    public void setProperties(ConnectionProperties properties) {
        this.properties = properties;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public int getLargeThreshold() {
        return largeThreshold;
    }

    public void setLargeThreshold(int largeThreshold) {
        this.largeThreshold = largeThreshold;
    }

    public UserStatus getPresence() {
        return presence;
    }

    public void setPresence(UserStatus presence) {
        this.presence = presence;
    }
}
