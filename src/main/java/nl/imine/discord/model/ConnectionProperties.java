package nl.imine.discord.model;

import org.json.simple.JSONObject;

public class ConnectionProperties implements JSONAble<ConnectionProperties> {

	private String os;
	private String browser;
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

	@Override
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("$os", os);
		jsonObject.put("$browser", browser);
		jsonObject.put("$device", device);
		return jsonObject;
	}
}
