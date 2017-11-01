package nl.imine.discord.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.imine.discord.logic.Channel;
import nl.imine.discord.model.Message;
import nl.imine.discord.util.Rest;
import nl.imine.discord.util.multipart.MultiPart;

public class ChannelService {

	private Logger logger = LoggerFactory.getLogger(ChannelService.class);

	private Rest rest;

	public ChannelService(Rest rest) {
		this.rest = rest;
	}

	public void createMessage(Channel channel, Message message) {
		createMessage(channel.getId(), message);
	}

	public void createMessage(String channel, Message message) {
		try {
			String url = String.format("https://discordapp.com/api/channels/%s/messages", channel);
			byte[] data;
			HashMap<String, String> parameters = new HashMap<>();
			if (message.getFile().length != 0) {
				String boundary = createMultipartBoundaryString();
				parameters.put("Content-Type", "multipart/form-data; boundary=" + boundary);

				ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(message.getFile()));
				Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
				String result = null;
				if (readers.hasNext()) {
					ImageReader read = readers.next();
					result = read.getFormatName();
				}

				List<Byte> requestData = new ArrayList<>();
				ByteArrayOutputStream multipartData = new ByteArrayOutputStream();
				multipartData.write(new MultiPart("", "content", new HashMap<>(), message.getContent().getBytes()).toMultiPartData(boundary));
				multipartData.write(new MultiPart("", "tts", new HashMap<>(), "false".getBytes()).toMultiPartData(boundary));
				HashMap<String, String> headers = new HashMap<>();
				headers.put("filename", "image." + result);
				StringBuilder imageData = new StringBuilder();
				multipartData.write(new MultiPart("image/" + result, "file", headers, message.getFile()).toMultiPartData(boundary));
				multipartData.write("--".getBytes());
				multipartData.write(boundary.getBytes());
				multipartData.write("--\r\n".getBytes());
				data = multipartData.toByteArray();
				logger.info("Boundary: {}", boundary);
			} else {
				parameters.put("Content-Type", "application/json");
				data = new ObjectMapper().writeValueAsString(message).getBytes();
			}
			logger.debug("Send message to url: {}. Content: \n{}", url, data);
			logger.info(rest.postSync(url, parameters, data));
		} catch (IOException e) {
			logger.warn("Failed to send message ({}: {})", e.getClass().getSimpleName(), e.getMessage());
		}
	}

	public void deleteMessage(Channel channel, Message message) {
		deleteMessage(channel.getId(), message.getId());
	}

	public void deleteMessage(String channel, String message) {
		try {
			String url = String.format("https://discordapp.com/api/channels/%s/messages/%s", channel, message);
			logger.debug("Delete message {} from channel {}", message, channel);
			rest.deleteSync(url, new HashMap<>());
		} catch (IOException | ParseException e) {
			logger.warn("Failed to send message ({}: {})", e.getClass().getSimpleName(), e.getMessage());
		}
	}

	private String createMultipartBoundaryString() {
		Random random = new Random();
		char[] characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		StringBuilder boundry = new StringBuilder();
		boundry.append("----WebKitFormBoundary");
		for(int i = 0; i < 16; i++) {
			boundry.append(characters[random.nextInt(characters.length)]);
		}
		return boundry.toString();
	}
}
