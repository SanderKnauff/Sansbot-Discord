package nl.imine.discord.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoiceState {

    @JsonProperty("guild_id")
    private String guildId;
    @JsonProperty("channel_id")
    private String channelId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("self_video")
    private boolean selfVideo;
    @JsonProperty("self_mute")
    private boolean selfMute;
    @JsonProperty("self_deaf")
    private boolean selfDeaf;
    private boolean mute;
    private boolean deaf;
    private boolean supress;

    public VoiceState() {
    }

    public VoiceState(String guildId, String channelId, String userId, String sessionId, boolean selfVideo, boolean selfMute, boolean selfDeaf, boolean mute, boolean deaf, boolean supress) {
        this.guildId = guildId;
        this.channelId = channelId;
        this.userId = userId;
        this.sessionId = sessionId;
        this.selfVideo = selfVideo;
        this.selfMute = selfMute;
        this.selfDeaf = selfDeaf;
        this.mute = mute;
        this.deaf = deaf;
        this.supress = supress;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public boolean isSelfVideo() {
        return selfVideo;
    }

    public void setSelfVideo(boolean selfVideo) {
        this.selfVideo = selfVideo;
    }

    public boolean isSelfMute() {
        return selfMute;
    }

    public void setSelfMute(boolean selfMute) {
        this.selfMute = selfMute;
    }

    public boolean isSelfDeaf() {
        return selfDeaf;
    }

    public void setSelfDeaf(boolean selfDeaf) {
        this.selfDeaf = selfDeaf;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public void setDeaf(boolean deaf) {
        this.deaf = deaf;
    }

    public boolean isSupress() {
        return supress;
    }

    public void setSupress(boolean supress) {
        this.supress = supress;
    }

    @Override
    public String toString() {
        return "VoiceState{" +
                "guildId='" + guildId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", selfVideo=" + selfVideo +
                ", selfMute=" + selfMute +
                ", selfDeaf=" + selfDeaf +
                ", mute=" + mute +
                ", deaf=" + deaf +
                ", supress=" + supress +
                '}';
    }
}
