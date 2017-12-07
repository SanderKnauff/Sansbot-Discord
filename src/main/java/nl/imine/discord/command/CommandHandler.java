package nl.imine.discord.command;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import nl.imine.discord.logic.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.event.EventDispatcher;
import nl.imine.discord.event.EventHandler;
import nl.imine.discord.event.Listener;
import nl.imine.discord.event.gateway.MessageCreateEvent;
import nl.imine.discord.model.Message;
import nl.imine.discord.service.ChannelService;
import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Property;

@Component
public class CommandHandler implements Listener {

	private final Logger logger = LoggerFactory.getLogger(CommandHandler.class);

	private final Set<Command> commands;
	private final ChannelService channelService;
	private final EventDispatcher eventDispatcher;
	private final String commandPrefix;

	public CommandHandler(EventDispatcher eventDispatcher, ChannelService channelService, @Property("sansbot.command.prefix") String commandPrefix) {
		this.commands = new HashSet<>();
		this.eventDispatcher = eventDispatcher;
		this.channelService = channelService;
		this.commandPrefix = commandPrefix;
	}

	@PostConstruct
	public void onPostConstruct() {
		eventDispatcher.registerListener(this);
	}

	@EventHandler
	public void handleMessageCreateEvent(MessageCreateEvent messageCreateEvent) {
		Message message = messageCreateEvent.getMessage();
		Member member = message.getAuthor();

		if(message.getContent().startsWith(commandPrefix)) {
			String input = message.getContent().substring(1);
			for(Command command : commands) {
				if(command.getTriggers().stream().anyMatch(trigger -> input.split(" ")[0].equalsIgnoreCase(trigger))) {
					command.handle(member, message);
					return;
				}
			}
			Message reply = new Message();
			reply.setContent(String.format("%s, uuhhh... Nee ik denk niet dat ik begrijp wat je van mij wilt...", member.getMention()));
			channelService.createMessage(message.getChannelId(), reply);
			channelService.deleteMessage(message.getChannelId(), message.getId());
			logger.info("Member ({}) attempted to run unknown command: {}", message.getAuthor().getUsername(), message.getContent());
		}
	}

	public boolean registerCommand(Command command) {
		return commands.add(command);
	}

}
