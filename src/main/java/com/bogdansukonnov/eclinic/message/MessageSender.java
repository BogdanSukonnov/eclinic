package com.bogdansukonnov.eclinic.message;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@AllArgsConstructor
@Log4j2
public class MessageSender {

    private JmsTemplate jmsTemplate;

    /**
     * send text message to queue asynchronously
     *
     * @param message text of the message
     */
    public void sendMessage(String message) {
        Runnable runnable =
                () -> {
                    try {
                        jmsTemplate.send(new MessageCreator() {
                            @Override
                            @NonNull
                            public TextMessage createMessage(@NonNull Session session) throws JMSException {
                                return session.createTextMessage(message);
                            }
                        });
                    } catch (JmsException e) {
                        log.error(e + " : " + e.getMessage());
                    }
                };
        new Thread(runnable).start();
    }
}
