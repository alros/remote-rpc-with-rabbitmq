package alros.demo.sync2async.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import alros.demo.sync2async.model.Response;

@Component
public class Receiver {

	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

	private MessageRepository messageRepository;

	public Receiver(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public void receiveMessage(Response response) {
		LOG.info("received answer for {}", response.getCorrelationId());
		messageRepository.store(response);
	}

}