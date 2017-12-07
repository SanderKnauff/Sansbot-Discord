package nl.imine.discord.voice.socket.messages.data;

import java.util.List;

public class VoiceReadyMessageData {

    private String ssrc;
    private int port;
    private List<String> modes;
    private String ip;

    public VoiceReadyMessageData() {
    }

    public VoiceReadyMessageData(String ssrc, int port, List<String> modes) {

    }

    public VoiceReadyMessageData(String ssrc, int port, List<String> modes, String ip) {
        this.ssrc = ssrc;
        this.port = port;
        this.modes = modes;
        this.ip = ip;
    }

    public String getSsrc() {
        return ssrc;
    }

    public void setSsrc(String ssrc) {
        this.ssrc = ssrc;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
