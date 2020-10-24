package com.alexpower.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class SpringMQConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) throws JMSException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        SpringMQConsumer consumer = (SpringMQConsumer) ctx.getBean("springMQConsumer");
        String returnVal = (String) consumer.jmsTemplate.receiveAndConvert();
        System.out.println("get message : " + returnVal);
    }
}
