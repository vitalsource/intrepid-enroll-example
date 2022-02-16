package com.intrepidlearning.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootApplication
public class Demo {

	private static final String APIKEY = null;
	private static final String URL = null;
	private static final String PRODUCT_CODE = "example-product-code";

	private static final Logger log = LoggerFactory.getLogger(Demo.class);

	public static void main(String[] args) {
		SpringApplication.run(Demo.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.defaultHeader("Authorization", "Bearer " + APIKEY)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			if(APIKEY == null){
				throw new RuntimeException("APIKEY needs to set");
			}

			if(URL == null){
				throw new RuntimeException("URL needs to be set");
			}


			ObjectMapper mapper = new ObjectMapper();

			String suffix = String.valueOf((int)(Math.random() * 1000));

			// create a new user
			UserWrite userWrite = new UserWrite();
			userWrite.email = "jane.doe" + suffix + "@example.com";
			userWrite.firstName = "Jane";
			userWrite.lastName = "Doe";
			userWrite.externalId = "example-external-id" + suffix;
			userWrite.username = "jane.doe" + suffix;

			log.info(String.format("Creating user %s", userWrite.username));
			ResponseEntity<User> response = restTemplate.postForEntity(URL + "/users", userWrite, User.class);

			log.info(String.format("'%d' response status, %n%s",
					response.getStatusCode().value(),
					mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody())));

			// create a user that already exists (username conflict)
			try{
				response = restTemplate.postForEntity(URL + "/users", userWrite, User.class);
			}catch (RestClientException e){
				log.info(e.getMessage());
			}


			// create a user that already exists (externalId conflict)
			userWrite.username = "does.not.exist";
			try {
				response = restTemplate.postForEntity(URL + "/users", userWrite, User.class);
			}catch (RestClientException e){
				log.info(e.getMessage());
			}


			// enroll the learner
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL).path("/openapi/classes/" + PRODUCT_CODE + "/users/" + userWrite.externalId);
			UriComponents components = builder.build(true);
			restTemplate.put(components.toUri(), null);

		};
	}

}
