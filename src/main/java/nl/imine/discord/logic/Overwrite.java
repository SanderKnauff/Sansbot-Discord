package nl.imine.discord.logic;

public class Overwrite {

	private String id;
	private OverwriteType type;
	private int allow;
	private int deny;

	public Overwrite(String id, OverwriteType type, int allow, int deny) {
		this.id = id;
		this.type = type;
		this.allow = allow;
		this.deny = deny;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OverwriteType getType() {
		return type;
	}

	public void setType(OverwriteType type) {
		this.type = type;
	}

	public int getAllow() {
		return allow;
	}

	public void setAllow(int allow) {
		this.allow = allow;
	}

	public int getDeny() {
		return deny;
	}

	public void setDeny(int deny) {
		this.deny = deny;
	}

	public enum OverwriteType {
		ROLE, MEMBER;
	}
}
