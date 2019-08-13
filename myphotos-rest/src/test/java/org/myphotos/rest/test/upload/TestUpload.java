package org.myphotos.rest.test.upload;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.myphotos.rest.Constants;

public class TestUpload {

	private static final Long profileId = 157L;
	private static final String accessToken = "test";
	
	public static void main(String[] args) {
		makeUploadRequest(
				String.format("http://api.myphotos.com/v1/profile/%s/avatar", profileId),
				System.getProperty("user.home") + "/Pictures/john_wick_2.jpg"
		);
		makeUploadRequest(
				String.format("http://api.myphotos.com/v1/profile/%s/photo", profileId),
				System.getProperty("user.home") + "/Pictures/John-Wick-Keanu-Reeves.jpg"
				);
	}
	
	private static void makeUploadRequest(String apiUrl, String filePath) {
		Client client = ClientBuilder.newBuilder()
				.register(MultiPartFeature.class)
				.build();
		Entity<?> entity = createUploadEntity(filePath);
		Response response = client
				.target(apiUrl)
				.request(MediaType.APPLICATION_JSON)
				.header(Constants.ACCESS_TOKEN_HEADER, accessToken)
				.post(entity);
		
		System.out.println("Request: " + apiUrl);
		System.out.println("Status: " + response.getStatus());
		System.out.println("Content: " + response.readEntity(String.class));
	}

	private static Entity<?> createUploadEntity(String filePath) {
		MultiPart multiPart = new MultiPart();
		multiPart.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);
		FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", new File(filePath), new MediaType("image", "jpeg"));
		multiPart.bodyPart(fileDataBodyPart);
		
		return Entity.entity(multiPart, multiPart.getMediaType());
	}
}
