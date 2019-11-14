package com.bogdansukonnov.eclinic.message;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class MessageCounter {

    private AtomicLong counter = new AtomicLong(0);

    public long incrementAndGet() {
        return counter.incrementAndGet();
    }

    public long getCounter() {
        return counter.get();
    }

}
