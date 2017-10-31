package nl.imine.discord.logic;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.Sansbot;
import nl.imine.discord.model.Message;

public class Channel {

	private static Logger logger = LoggerFactory.getLogger(Channel.class);

	private String id;
	private ChannelType type;
	private String guildId;
	private int position;
	private List<Overwrite> overwrites;
	private String name;
	private String topic;
	private boolean nsfw;
	private String lastMessageId;
	private int bitrate;
	private int userLimit;
	private List<User> receipients;
	private String icon;
	private String ownerId;
	private String applicationId;
	private String parentId;

	public Channel(String id, ChannelType type, String guildId, int position, List<Overwrite> overwrites, String name, String topic, boolean nsfw, String lastMessageId, int bitrate, int userLimit, List<User> receipients, String icon, String ownerId, String applicationId, String parentId) {
		this.id = id;
		this.type = type;
		this.guildId = guildId;
		this.position = position;
		this.overwrites = overwrites;
		this.name = name;
		this.topic = topic;
		this.nsfw = nsfw;
		this.lastMessageId = lastMessageId;
		this.bitrate = bitrate;
		this.userLimit = userLimit;
		this.receipients = receipients;
		this.icon = icon;
		this.ownerId = ownerId;
		this.applicationId = applicationId;
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ChannelType getType() {
		return type;
	}

	public void setType(ChannelType type) {
		this.type = type;
	}

	public String getGuildId() {
		return guildId;
	}

	public void setGuildId(String guildId) {
		this.guildId = guildId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<Overwrite> getOverwrites() {
		return overwrites;
	}

	public void setOverwrites(List<Overwrite> overwrites) {
		this.overwrites = overwrites;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public boolean isNsfw() {
		return nsfw;
	}

	public void setNsfw(boolean nsfw) {
		this.nsfw = nsfw;
	}

	public String getLastMessageId() {
		return lastMessageId;
	}

	public void setLastMessageId(String lastMessageId) {
		this.lastMessageId = lastMessageId;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public int getUserLimit() {
		return userLimit;
	}

	public void setUserLimit(int userLimit) {
		this.userLimit = userLimit;
	}

	public List<User> getReceipients() {
		return receipients;
	}

	public void setReceipients(List<User> receipients) {
		this.receipients = receipients;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public enum ChannelType {
		GUILD_TEXT, DM, GUILD_VOICE, GROUP_DM, GUILD_CATEGORY
	}
}
