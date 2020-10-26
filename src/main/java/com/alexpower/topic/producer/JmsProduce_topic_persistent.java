package com.alexpower.topic.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_topic_persistent {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "Topic_persist_jdbc";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // create connection from connection factory
        Connection connection = activeMQConnectionFactory.createConnection();

        // create session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // crate destination
        Topic topic = session.createTopic(TOPIC_NAME);

        // create producer and point out the destination
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // start connection up
        connection.start();

        // send message
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("persistent topic textMessage --- " + i);
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("******* All persistent topic msgs sent to Topic_persist...");
    }
}
