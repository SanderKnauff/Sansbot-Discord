package nl.imine.discord.command;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.event.EventHandler;
import nl.imine.discord.event.Listener;
import nl.imine.discord.event.gateway.MessageCreateEvent;
import nl.imine.discord.logic.User;
import nl.imine.discord.model.Message;
import nl.imine.discord.service.ChannelService;

public class CommandHandler implements Listener {

	private final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

	private final Set<Command> commands;
	private final ChannelService channelService;
	private final String commandPrefix;

	public CommandHandler(ChannelService channelService, String commandPrefix) {
		this.commands = new HashSet<>();
		this.channelService = channelService;
		this.commandPrefix = commandPrefix;
	}

	@EventHandler
	public void handleMessageCreateEvent(MessageCreateEvent messageCreateEvent) {
		Message message = messageCreateEvent.getMessage();
		User user = message.getAuthor();

		if(message.getContent().startsWith(commandPrefix)) {
			String input = message.getContent().substring(1);
			for(Command command : commands) {
				if(command.getTriggers().stream().anyMatch(trigger -> input.split(" ")[0].equalsIgnoreCase(trigger))) {
					command.handle(user, message);
					return;
				}
			}
			Message reply = new Message();
			reply.setContent(String.format("%s, uuhhh... Nee ik denk niet dat ik begrijp wat je van mij wilt...", user.getMention()));
			channelService.createMessage(message.getChannelId(), reply);
			channelService.deleteMessage(message.getChannelId(), message.getId());
			logger.info("User ({}) attempted to run unknown command: {}", message.getAuthor().getUsername(), message.getContent());
		}
	}

	public boolean registerCommand(Command command) {
		return commands.add(command);
	}

}
