package nl.imine.discord;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import nl.imine.discord.event.Event;
import nl.imine.discord.event.EventDispatcher;
import nl.imine.vaccine.Vaccine;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.imine.discord.gateway.Gateway;
import nl.imine.discord.util.Rest;

public class Sansbot {

	private static final Logger logger = LoggerFactory.getLogger(Sansbot.class);

	public static void main(String[] args) throws IOException {
		Properties properties = loadProperties();
		Vaccine vaccine = new Vaccine();
		vaccine.inject(properties, "nl.imine.discord");
		Gateway gateway = (Gateway) vaccine.getInjected(Gateway.class);
		try {
			gateway.openWebSocket();
		} catch (Exception e) {
			logger.warn("Websocket Connection failed | Reason: ({}: {})", e.getClass().getSimpleName(), e.getMessage());
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
