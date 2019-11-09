package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.message.Sender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class MessagingService {

    private Sender sender;

    public void send() {
        sender.sendMessage("message " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}
