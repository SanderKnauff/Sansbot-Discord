package nl.imine.discord.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConnectionProperties {

    @JsonProperty("$os")
    private String os;
    @JsonProperty("$browser")
    private String browser;
    @JsonProperty("$device")
    private String device;

    public ConnectionProperties(String os, String browser, String device) {
        this.os = os;
        this.browser = browser;
        this.device = device;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
