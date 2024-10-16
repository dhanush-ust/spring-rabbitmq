package com.demo.spring_rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

public static final String QUEUE = "message_queue";
public static final String EXCHANGE = "message_exchange";
public static final String ROUTING_KEY = "message_routingkey";
@Bean
public Queue queue() {
return new Queue(QUEUE);
}
@Bean
public TopicExchange topicExchange() {
return new TopicExchange(EXCHANGE);
}
@Bean
public Binding binding(Queue queue, TopicExchange exchange) {
return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
}
@Bean
public MessageConverter messageConverter() {
return new Jackson2JsonMessageConverter();
}
@Bean
public AmqpTemplate template(ConnectionFactory connectionFactory) {
RabbitTemplate template = new RabbitTemplate(connectionFactory);
template.setMessageConverter(messageConverter());
return template;
}
}