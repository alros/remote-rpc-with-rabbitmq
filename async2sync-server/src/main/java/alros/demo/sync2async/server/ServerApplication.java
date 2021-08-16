package alros.demo.sync2async.server;

import static alros.demo.sync2async.model.Configurations.requestExchangeName;
import static alros.demo.sync2async.model.Configurations.requestManualRoutingKey;
import static alros.demo.sync2async.model.Configurations.requestRpcRoutingKey;
import static alros.demo.sync2async.model.Configurations.requestManualQueueName;
import static alros.demo.sync2async.model.Configurations.requestRpcQueueName;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(requestExchangeName);
	}

	/* manual processing */

	@Bean
	Queue queueManual() {
		return new Queue(requestManualQueueName, false);
	}

	@Bean
	Binding bindingManual(@Qualifier("queueManual") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(requestManualRoutingKey);
	}

	@Bean
	MessageListenerAdapter listenerAdapterManual(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessageManual");
	}

	@Bean
	SimpleMessageListenerContainer containerManual(ConnectionFactory connectionFactory,
			@Qualifier("listenerAdapterManual") MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(requestManualQueueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	/* rpc processing */

	@Bean
	Queue queueRpc() {
		return new Queue(requestRpcQueueName, false);
	}

	@Bean
	Binding bindingRpc(@Qualifier("queueRpc") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(requestRpcRoutingKey);
	}

	@Bean
	MessageListenerAdapter listenerAdapterRpc(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessageRpc");
	}

	@Bean
	SimpleMessageListenerContainer containerRpc(ConnectionFactory connectionFactory,
			@Qualifier("listenerAdapterRpc") MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(requestRpcQueueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

}