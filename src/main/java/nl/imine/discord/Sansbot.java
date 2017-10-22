package nl.imine.discord;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.gateway.Gateway;
import nl.imine.discord.util.Rest;

public class Sansbot {

	private static final Logger logger = LoggerFactory.getLogger(Sansbot.class);
	public static Rest rest = null;

	public static void main(String[] args) throws IOException {
		Properties properties = loadProperties();
		String botToken = properties.getProperty("discord.client.token");
		rest = new Rest(botToken);
		try {
			new Gateway(rest).openWebSocket(botToken);
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