package com.bogdansukonnov.eclinic.config;

import lombok.AllArgsConstructor;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@PropertySource({"classpath:messaging.properties"})
@AllArgsConstructor
public class MessagingConfig {

    private Environment environment;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(environment.getProperty("broker.url"));
        connectionFactory.setUserName(environment.getProperty("broker.username"));
        connectionFactory.setPassword(environment.getProperty("broker.password"));
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(true);
        template.setDefaultDestinationName(environment.getProperty("topic.name"));
        return template;
    }
}
