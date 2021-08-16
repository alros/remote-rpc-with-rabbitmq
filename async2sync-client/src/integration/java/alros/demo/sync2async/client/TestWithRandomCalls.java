package alros.demo.sync2async.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class TestWithRandomCalls {

	private static final Logger LOG = LoggerFactory.getLogger(TestWithRandomCalls.class);

	public static void main(String[] args) {
		WebClient client = WebClient.builder().baseUrl("http://localhost:8080")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080")).build();

		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int value = i;
			threads.add(new Thread(() -> {
				String result = client.get().uri(builder -> builder.path("/cube-rpc").queryParam("value", value).build())
						.retrieve().bodyToMono(String.class).block();
				LOG.info("result for {} was {}", value, result);
			}));
		}
		threads.forEach(t -> t.start());
		threads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}

}
