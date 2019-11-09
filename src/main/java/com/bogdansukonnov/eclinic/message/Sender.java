package com.bogdansukonnov.eclinic.message;

import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@AllArgsConstructor
public class Sender {

    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {

        jmsTemplate.send(new MessageCreator(){
            @Override
            @NonNull
            public TextMessage createMessage(@NonNull Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

}
