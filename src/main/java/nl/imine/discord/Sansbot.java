package nl.imine.discord;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import nl.imine.discord.command.CommandHandler;
import nl.imine.discord.command.PatatCommand;
import nl.imine.discord.event.Event;
import nl.imine.discord.event.EventDispatcher;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.gateway.Gateway;
import nl.imine.discord.service.ChannelService;
import nl.imine.discord.util.Rest;

public class Sansbot {

	private static final Logger logger = LoggerFactory.getLogger(Sansbot.class);
	public static Rest rest = null;

	private static EventDispatcher eventDispatcher;

	public static void main(String[] args) throws IOException {
		Properties properties = loadProperties();
		String botToken = properties.getProperty("discord.client.token");
		eventDispatcher = new EventDispatcher();
		rest = new Rest(botToken);
		ChannelService channelService = new ChannelService(rest);
		CommandHandler commandHandler = new CommandHandler(channelService, properties.getProperty("sansbot.command.prefix"));
		commandHandler.registerCommand(new PatatCommand(channelService));
		eventDispatcher.registerListener(commandHandler);
		try {
			new Gateway(rest, eventDispatcher).openWebSocket(botToken);
		} catch (URISyntaxException | ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Properties loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemResource("application.properties").openStream());
			logger.info("Loaded properties:");
			properties.forEach((k, v) -> logger.info("\t{}: {}", k, v));
		} catch (IOException e) {
			logger.error("Failed to load properties. Aborting ({}: {})", e.getClass().getSimpleName(), e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		return properties;
	}
}
