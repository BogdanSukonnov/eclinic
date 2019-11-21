package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.message.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessagingServiceImpl implements MessagingService {

    private MessageSender messageSender;

    @Override
    public void send(String message) {
        messageSender.sendMessage(message);
    }

}
