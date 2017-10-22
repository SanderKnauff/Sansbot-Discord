package nl.imine.discord.model;

import java.nio.file.Path;

import org.json.simple.JSONObject;

public class Message implements  JSONAble<Message> {

	private String content;
	private String nonce;
	private boolean tts;
	private Path file;
	private Embed embed;

	public Message(String content, String nonce, boolean tts, Path file, Embed embed) {
		this.content = content;
		this.nonce = nonce;
		this.tts = tts;
		this.file = file;
		this.embed = embed;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public boolean isTts() {
		return tts;
	}

	public void setTts(boolean tts) {
		this.tts = tts;
	}

	public Path getFile() {
		return file;
	}

	public void setFile(Path file) {
		this.file = file;
	}

	public Embed getEmbed() {
		return embed;
	}

	public void setEmbed(Embed embed) {
		this.embed = embed;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("content", content);
		jsonObject.put("nonce", nonce);
		jsonObject.put("tts", tts);
//		jsonObject.put("file", );
//		jsonObject.put("embed", embed);
		return jsonObject;
	}
}
