package com.alexpower.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class SpringMQProducer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQProducer producer =(SpringMQProducer) ctx.getBean("springMQProducer");
        producer.jmsTemplate.send((Session session) -> {
                TextMessage textMessage = session.createTextMessage("Spring-activemq-textmessage ....");
                return textMessage;
            });
        System.out.println("******** send text done");
    }
}
