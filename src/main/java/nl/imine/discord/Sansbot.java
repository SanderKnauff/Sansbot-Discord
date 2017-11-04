package nl.imine.discord;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.imine.discord.gateway.messages.GatewayPayload;
import nl.imine.discord.util.jackson.GameTypeSerializer;
import nl.imine.discord.util.jackson.GatewayPayloadDeserializer;
import nl.imine.discord.util.jackson.OpcodeSerializer;
import nl.imine.vaccine.Vaccine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Sansbot {

    private static final Logger logger = LoggerFactory.getLogger(Sansbot.class);
    private static ObjectMapper objectMapper = null;

    public static void main(String[] args) throws IOException {
        Vaccine vaccine = new Vaccine();
        vaccine.inject(loadProperties(), "nl.imine.discord");
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

    public static ObjectMapper objectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(new OpcodeSerializer());
            simpleModule.addSerializer(new GameTypeSerializer());
            simpleModule.addDeserializer(GatewayPayload.class, new GatewayPayloadDeserializer());
            objectMapper.registerModule(simpleModule);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
        return objectMapper;

    }
}
