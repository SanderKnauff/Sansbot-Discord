package nl.imine.discord.util.multipart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class MultiPart {

	private String contentType;
	private String name;
	private Map<String, String> headers;
	private byte[] data;

	public MultiPart(String contentType, String name, Map<String, String> headers, byte[] data) {
		this.contentType = contentType;
		this.name = name;
		this.headers = headers;
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] toMultiPartData(String boundry) {
		StringBuilder builder = new StringBuilder();
		builder.append("--").append(boundry).append("\r\n");
		builder.append("Content-Disposition: form-data; name=\"").append(name).append("\"");
		headers.forEach((k, v) -> {
			builder.append("; ").append(k).append("=\"").append(v).append("\"");
		});
		builder.append("\r\n");
		if (contentType != null && !contentType.isEmpty()) {
			builder.append("Content-Type: ").append(contentType).append("\r\n");
		}
		builder.append("\r\n");

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			byteArrayOutputStream.write(builder.toString().getBytes());
			byteArrayOutputStream.write(data);
			byteArrayOutputStream.write("\r\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return byteArrayOutputStream.toByteArray();
	}
}
