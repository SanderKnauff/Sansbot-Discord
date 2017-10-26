package nl.imine.discord.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.xml.internal.ws.api.message.Attachment;
import nl.imine.discord.logic.User;
import nl.imine.discord.util.jackson.LocalDateTimeDeserializer;
import nl.imine.discord.util.jackson.LocalDateTimeSerializer;

public class Message {

	private String id;
	@JsonProperty("channel_id")
	private String channelId;
	private User author;
	private String content;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime timestamp;
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonProperty("edited_timestamp")
	private LocalDateTime editedTimestamp;
	private boolean tts;
	@JsonProperty("mention_everyone")
	private boolean mentionEveryone;
	private Set<User> mentions;
	@JsonProperty("mention_roles")
	private Set<Role> mentionRoles;
	private Set<Attachment> attachments;
	private Set<Embed> embeds;
	private Set<Reaction> reactions;
	private String nonce;
	private boolean pinned;
	@JsonProperty("webhook_id")
	private String webhookId;
	private MessageType type;

	public Message() {

	}

	public Message(String id, String channelId, User author, String content, LocalDateTime timestamp, LocalDateTime editedTimestamp, boolean tts, boolean mentionEveryone, Set<User> mentions, Set<Role> mentionRoles, Set<Attachment> attachments, Set<Embed> embeds, Set<Reaction> reactions, String nonce, boolean pinned, String webhookId, MessageType type) {
		this.id = id;
		this.channelId = channelId;
		this.author = author;
		this.content = content;
		this.timestamp = timestamp;
		this.editedTimestamp = editedTimestamp;
		this.tts = tts;
		this.mentionEveryone = mentionEveryone;
		this.mentions = mentions;
		this.mentionRoles = mentionRoles;
		this.attachments = attachments;
		this.embeds = embeds;
		this.reactions = reactions;
		this.nonce = nonce;
		this.pinned = pinned;
		this.webhookId = webhookId;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public LocalDateTime getEditedTimestamp() {
		return editedTimestamp;
	}

	public void setEditedTimestamp(LocalDateTime editedTimestamp) {
		this.editedTimestamp = editedTimestamp;
	}

	public boolean isTts() {
		return tts;
	}

	public void setTts(boolean tts) {
		this.tts = tts;
	}

	public boolean isMentionEveryone() {
		return mentionEveryone;
	}

	public void setMentionEveryone(boolean mentionEveryone) {
		this.mentionEveryone = mentionEveryone;
	}

	public Set<User> getMentions() {
		return mentions;
	}

	public void setMentions(Set<User> mentions) {
		this.mentions = mentions;
	}

	public Set<Role> getMentionRoles() {
		return mentionRoles;
	}

	public void setMentionRoles(Set<Role> mentionRoles) {
		this.mentionRoles = mentionRoles;
	}

	public Set<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(Set<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Set<Embed> getEmbeds() {
		return embeds;
	}

	public void setEmbeds(Set<Embed> embeds) {
		this.embeds = embeds;
	}

	public Set<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(Set<Reaction> reactions) {
		this.reactions = reactions;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public String getWebhookId() {
		return webhookId;
	}

	public void setWebhookId(String webhookId) {
		this.webhookId = webhookId;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
}
