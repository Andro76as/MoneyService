package com.example.project;

import com.example.project.model.Amount;
import com.example.project.model.ConfirmOperationRequest;
import com.example.project.model.TransferRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Paths;
import java.util.Objects;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectApplicationTests {

	private static final String HOST = "http://localhost:";
	private static final String ENDPOINT_TRANSFER = "/transfer";
	private static final String ENDPOINT_CONFIRM = "/confirmOperation";
	private static final int PORT = 5500;
	public static final String OPERATION_ID = "1";
	private static final String CODE = "1234";
	public static final TransferRequest TRANSFER_REQUEST = new TransferRequest("5555555555555556",
			"12/24", "123", "5555555555555555", new Amount(5000, "EUR"));
	public static final ConfirmOperationRequest CONFIRM_OPERATION_REQUEST = new ConfirmOperationRequest(
			OPERATION_ID, CODE);
	@Autowired
	TestRestTemplate restTemplate;

	@Container
	public static final GenericContainer<?> container = new GenericContainer<>(new ImageFromDockerfile()
			.withFileFromPath(".", Paths.get("target"))
			.withDockerfileFromBuilder(builder -> builder
					.from("openjdk:18")
					.expose(PORT)
					.add("project-0.0.1-SNAPSHOT.jar", "app.jar")
					.entryPoint("java", "-jar", "/app.jar")
					.build()))
			.withExposedPorts(PORT);


	@Test
	void contextLoadsTransfer() throws JSONException {
		ResponseEntity<Object> forTransfer = restTemplate.postForEntity(HOST + container.getMappedPort(PORT) +
				ENDPOINT_TRANSFER, TRANSFER_REQUEST, Object.class);
		String expected = new JSONObject(Objects.requireNonNull(forTransfer.getBody()).toString())
				.get("operationId").toString();
		Assertions.assertEquals(expected, OPERATION_ID);
	}

	@Test
	void contextLoadsConfirmOperation() throws JSONException {
		ResponseEntity<Object> forConfirmOperation = restTemplate.postForEntity(HOST + container.getMappedPort(PORT) +
				ENDPOINT_CONFIRM, CONFIRM_OPERATION_REQUEST, Object.class);
		String expected = new JSONObject(Objects.requireNonNull(forConfirmOperation.getBody()).toString())
				.get("operationId").toString();
		Assertions.assertEquals(expected, OPERATION_ID);
	}

}
