package com.alexpower.queue.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue_01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // create connection from connection factory
        Connection connection = activeMQConnectionFactory.createConnection();
        // start connection up
        connection.start();
        // create session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // crate destination
        Queue queue = session.createQueue(QUEUE_NAME);

        // create producer and point out the destination
        MessageProducer producer = session.createProducer(queue);

        // send message
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("textMessage ---" + i);
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("******* messages have been sent to queue_01");
    }
}
