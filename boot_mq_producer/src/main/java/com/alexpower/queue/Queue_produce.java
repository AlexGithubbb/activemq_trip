package com.alexpower.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class Queue_produce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue myQueue;

    public void sendMsg(){
        jmsMessagingTemplate.convertAndSend(myQueue, "******** " + UUID.randomUUID().toString().substring(0,6));
    }

    @Scheduled(fixedDelay = 3000) // delay 3s
    public void sendMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(myQueue, "******** scheduled " + UUID.randomUUID().toString().substring(0,6));
        System.out.println("******** sent after 3s..");
    }
}
