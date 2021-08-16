package alros.demo.sync2async.server;

import static alros.demo.sync2async.model.Configurations.responseExchangeName;
import static alros.demo.sync2async.model.Configurations.responseManualRoutingKey;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import alros.demo.sync2async.model.Request;
import alros.demo.sync2async.model.Response;

@Component
public class Receiver {

	private RabbitTemplate rabbitTemplate;

	public Receiver(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void receiveMessageManual(Request request) {
		Response response = new Response(request.getCorrelationId(), cube(request));
		rabbitTemplate.convertAndSend(responseExchangeName, responseManualRoutingKey, response);
	}

	public Response receiveMessageRpc(Request request) {
		return new Response(null, cube(request));
	}

	private int cube(Request request) {
		return request.getValue() * request.getValue() * request.getValue();
	}
}