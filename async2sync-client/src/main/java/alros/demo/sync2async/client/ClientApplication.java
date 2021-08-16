package alros.demo.sync2async.client;

import static alros.demo.sync2async.model.Configurations.responseExchangeName;
import static alros.demo.sync2async.model.Configurations.responseManualRoutingKey;
import static alros.demo.sync2async.model.Configurations.responseQueueName;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientApplication.class, args);
  }

  @Bean
  DirectExchange exchange() {
    return new DirectExchange(responseExchangeName);
  }
  
  @Bean
  Queue queue() {
    return new Queue(responseQueueName, false);
  }

  @Bean
  Binding binding(Queue queue, DirectExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(responseManualRoutingKey);
  }
  
  @Bean
  MessageListenerAdapter listenerAdapter(Receiver receiver) {
	  return new MessageListenerAdapter(receiver, "receiveMessage");
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(responseQueueName);
    container.setMessageListener(listenerAdapter);
    return container;
  }
}