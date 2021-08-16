package alros.demo.sync2async.client;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

import alros.demo.sync2async.model.Response;

@Component
public class MessageRepository {

	private Map<String, Response> repo = new ConcurrentHashMap<>();

	public Future<Response> getFuture(String uuid) {
		return CompletableFuture.supplyAsync(() -> {
			while (!repo.containsKey(uuid)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			return repo.remove(uuid);
		});
	}

	public void store(Response response) {
		repo.put(response.getCorrelationId(), response);
	}

}
