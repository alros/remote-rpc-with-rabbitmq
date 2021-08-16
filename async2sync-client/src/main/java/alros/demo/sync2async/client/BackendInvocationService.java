package alros.demo.sync2async.client;

import static alros.demo.sync2async.model.Configurations.requestExchangeName;
import static alros.demo.sync2async.model.Configurations.requestManualRoutingKey;
import static alros.demo.sync2async.model.Configurations.requestRpcRoutingKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import alros.demo.sync2async.model.Request;
import alros.demo.sync2async.model.Response;

@Component
public class BackendInvocationService {

	private static final Logger LOG = LoggerFactory.getLogger(BackendInvocationService.class);

	private RabbitTemplate rabbitTemplate;
	private MessageRepository messageRepository;

	public BackendInvocationService(RabbitTemplate rabbitTemplate, MessageRepository messageRepository) {
		this.rabbitTemplate = rabbitTemplate;
		this.messageRepository = messageRepository;
	}

	public Response manuallyInvokeCube(Request request) {
		rabbitTemplate.convertAndSend(requestExchangeName, requestManualRoutingKey, request);
		Future<Response> futureResult = messageRepository.getFuture(request.getCorrelationId());
		LOG.info("invocation completed for {}", request.getCorrelationId());
		try {
			return futureResult.get(120, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException e) {
			return Response.PROCESSING_ERROR;
		} catch (TimeoutException e) {
			Thread.currentThread().interrupt();
			return Response.PROCESSING_ERROR;
		}
	}

	public Response rpcInvokeSquare(Request request) {
		return (Response) rabbitTemplate.convertSendAndReceive(requestExchangeName, requestRpcRoutingKey, request);
	}

}
