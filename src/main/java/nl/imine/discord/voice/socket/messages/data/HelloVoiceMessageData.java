package nl.imine.discord.voice.socket.messages.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloVoiceMessageData {

    @JsonProperty("heartbeat_interval")
    private int heartbeatInterval;

    public HelloVoiceMessageData() {
    }

    public HelloVoiceMessageData(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }
}
