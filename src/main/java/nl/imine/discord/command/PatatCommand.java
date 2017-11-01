package nl.imine.discord.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nl.imine.discord.logic.User;
import nl.imine.discord.model.Message;
import nl.imine.discord.service.ChannelService;

public class PatatCommand implements Command {

	private final ChannelService channelService;
	private final List<String> triggers;

	public PatatCommand(ChannelService channelService) {
		this.channelService = channelService;
		this.triggers = new ArrayList<>();
		this.triggers.add("WeVliegenErIn");
	}

	@Override
	public List<String> getTriggers() {
		return triggers;
	}

	@Override
	public void handle(User user, Message message) {
		Message reply = new Message();
//		Path imagePath = Paths.get("WeVliegenErIn.jpg");
		reply.setContent("PATAT!");
//		try {
//			reply.setFile(Files.readAllBytes(imagePath));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		channelService.createMessage(message.getChannelId(), reply);
	}
}
