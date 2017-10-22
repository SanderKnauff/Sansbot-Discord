package nl.imine.discord.model;

import org.json.simple.JSONObject;

public class UserStatus implements JSONAble<UserStatus> {

	private int since;
	private Game game;
	private String status;
	private boolean afk;

	public UserStatus(int since, Game game, String status, boolean afk) {
		this.since = since;
		this.game = game;
		this.status = status;
		this.afk = afk;
	}

	public int getSince() {
		return since;
	}

	public void setSince(int since) {
		this.since = since;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAfk() {
		return afk;
	}

	public void setAfk(boolean afk) {
		this.afk = afk;
	}

	@Override
	public JSONObject toJSON() {
		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("since", since);
		jsonObject.put("game", game.toJSON());
		jsonObject.put("status", status);
		jsonObject.put("afk", afk);
		return jsonObject;
	}
}
