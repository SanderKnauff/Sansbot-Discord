package nl.imine.discord.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private String id;
	private String username;
	private String discriminator;
	private String avatar;
	private boolean bot;
	@JsonProperty("mfa_enabled")
	private boolean mfaEnabled;
	private boolean verified;
	private String email;

	public User() {
	}

	public User(String id, String username, String discriminator, String avatar, boolean bot, boolean mfaEnabled, boolean verified, String email) {
		this.id = id;
		this.username = username;
		this.discriminator = discriminator;
		this.avatar = avatar;
		this.bot = bot;
		this.mfaEnabled = mfaEnabled;
		this.verified = verified;
		this.email = email;
	}

	public String getMention(){
		return String.format("<@%s>", id);
	}

	public String getMentionUsername() {
		return String.format("<@!%s>", id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(String discriminator) {
		this.discriminator = discriminator;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isBot() {
		return bot;
	}

	public void setBot(boolean bot) {
		this.bot = bot;
	}

	public boolean isMfaEnabled() {
		return mfaEnabled;
	}

	public void setMfaEnabled(boolean mfaEnabled) {
		this.mfaEnabled = mfaEnabled;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
