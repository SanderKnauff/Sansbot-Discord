package nl.imine.discord.command;

import java.util.List;

import nl.imine.discord.logic.Member;
import nl.imine.discord.model.Message;

public interface Command {

	List<String> getTriggers();
	void handle(Member member, Message message);
}
