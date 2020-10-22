package com.alexpower.topic.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "topic_01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // create connection from connection factory
        Connection connection = activeMQConnectionFactory.createConnection();
        // start connection up
        connection.start();
        // create session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // crate destination
        Topic topic = session.createTopic(TOPIC_NAME);

        // create producer and point out the destination
        MessageProducer producer = session.createProducer(topic);

        // send message
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("topic textMessage --- " + i);
            producer.send(textMessage);
//            textMessage.setJMSDestination(topic);
//            textMessage.setJMSDeliveryMode(2); // 2:  persistent ; 1 : non-persistent
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("******* Topic messages have been sent to topic_01");
    }
}
