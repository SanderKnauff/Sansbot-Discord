package nl.imine.discord.gateway.messages.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloMessageData {

    @JsonProperty("heartbeat_interval")
    private int heartbeatInterval;
    @JsonProperty("_trace")
    private String[] trace;

    public HelloMessageData() {
    }

    public HelloMessageData(int heartbeatInterval, String[] trace) {
        this.heartbeatInterval = heartbeatInterval;
        this.trace = trace;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public String[] getTrace() {
        return trace;
    }

    public void setTrace(String[] trace) {
        this.trace = trace;
    }
}
