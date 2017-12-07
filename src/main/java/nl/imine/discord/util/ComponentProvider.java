package nl.imine.discord.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import nl.imine.discord.gateway.messages.GatewayPayload;
import nl.imine.discord.util.jackson.*;
import nl.imine.discord.voice.socket.VoicePayload;
import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Provided;

@Component
public class ComponentProvider {

	public ComponentProvider() {

	}

	@Provided
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(new OpcodeSerializer());
		simpleModule.addSerializer(new VoiceOpcodeSerializer());
		simpleModule.addSerializer(new GameTypeSerializer());
		simpleModule.addDeserializer(GatewayPayload.class, new GatewayPayloadDeserializer());
		simpleModule.addDeserializer(VoicePayload.class, new VoiceSocketPayloadDeserializer());
		objectMapper.registerModule(simpleModule);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper;
	}
}
