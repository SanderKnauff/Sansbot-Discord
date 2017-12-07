package nl.imine.discord.voice.socket.messages.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdentifyVoiceMessageData {

    @JsonProperty("server_id")
    private String serverId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("session_id")
    private String sessionId;
    private String token;

    public IdentifyVoiceMessageData() {
    }

    public IdentifyVoiceMessageData(String serverId, String userId, String sessionId, String token) {
        this.serverId = serverId;
        this.userId = userId;
        this.sessionId = sessionId;
        this.token = token;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
