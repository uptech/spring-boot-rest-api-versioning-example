package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

		@LocalServerPort
		private int port;

		@Autowired
		private TestRestTemplate restTemplate;

		@Test
		public void testRoot() throws Exception {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", "application/vnd.example.v2+json");
			headers.set("Content-Type", "application/vnd.example.v2+json");
            String bodyTesting = "{ \"test\": \"phil\" }";
            HttpEntity<String> entity = new HttpEntity<String>(bodyTesting, headers);

            ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:" + port, HttpMethod.POST, entity, String.class);

            assertEquals(HttpStatus.OK, exchange.getStatusCode());
            assertEquals("application/vnd.example.v8+json", exchange.getHeaders().get("Content-Type").get(0));
            assertEquals("{ \"accept\": \"application/vnd.example.v2+json\", \"content-type\": \"application/vnd.example.v2+json\", \"body\": \"{ \"test\": \"phil\" }\" }", exchange.getBody());
        }
}
