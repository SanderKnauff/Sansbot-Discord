package nl.imine.discord.gateway.messages;

public enum Opcode {

	EVENT(0),
	HEARTBEAT(1),
	IDENTIFY(2),
	HELLO(10),
	ACK(11);

	private int code;

	Opcode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static Opcode getOpcode(int code) {
		for (Opcode opcode : values()) {
			if (code == opcode.getCode()) {
				return opcode;
			}
		}
		return null;
	}
}
