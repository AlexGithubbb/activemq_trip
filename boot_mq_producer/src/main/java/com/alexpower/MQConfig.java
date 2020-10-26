package com.alexpower;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;

@Configuration
@EnableJms
public class MQConfig {

    @Value("${myQueue}")
    private String myQueue;

    @Bean
    public Queue myQueue(){
        return new ActiveMQQueue(myQueue);
    }

}
