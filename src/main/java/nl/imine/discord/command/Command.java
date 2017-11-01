package nl.imine.discord.command;

import java.util.List;

import nl.imine.discord.logic.User;
import nl.imine.discord.model.Message;

public interface Command {

	List<String> getTriggers();
	void handle(User user, Message message);
}
