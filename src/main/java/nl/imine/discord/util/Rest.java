package nl.imine.discord.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rest {

	private static final Logger logger = LoggerFactory.getLogger(Rest.class);

	private JSONParser jsonParser = new JSONParser();

	private String botToken;

	public Rest(String botToken) {
		this.botToken = botToken;
	}

	public JSONObject getSync(String url, Map<String, String> parameters) throws IOException, ParseException {
		URL endpoint = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("accept", "text/json, application/json");
		connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
		connection.setRequestProperty("User-Agent", "DiscordBot");
		connection.setRequestProperty("Authorization", "Bot " + botToken);
		parameters.forEach(connection::setRequestProperty);

		logger.info("Request Properties:");
		connection.getRequestProperties().forEach((k, v) -> logger.info("\t" + k + ": " + String.join(" ", v)));

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String output;
		StringBuilder ret = new StringBuilder();
		while ((output = bufferedReader.readLine()) != null) {
			ret.append(output);
		}
		return (JSONObject) jsonParser.parse(ret.toString());
	}

	public String postSync(String url, Map<String, String> parameters, String data) throws IOException {
		URL endpoint = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("accept", "text/json, application/json");
		connection.setRequestProperty("User-Agent", "DiscordBot");
		connection.setRequestProperty("Authorization", "Bot " + botToken);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		parameters.forEach(connection::setRequestProperty);

		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(data.getBytes());
		outputStream.flush();

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String output;
		StringBuilder ret = new StringBuilder();
		while ((output = bufferedReader.readLine()) != null) {
			ret.append(output);
		}
		return ret.toString();
	}
}
