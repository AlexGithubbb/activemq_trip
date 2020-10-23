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
        // two params: 1st is transaction, 2nd is acknowledge
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // crate destination
        Queue queue = session.createQueue(QUEUE_NAME);

        // create producer and point out the destination
        MessageProducer producer = session.createProducer(queue);
        // persistent, message still alive even MQ is down (by default)
//        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // non-persistent, message will be  gone if MQ is down
//        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // send message
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("textMessage ---" + i);
            textMessage.setStringProperty("007", "vip property text message");
            producer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("k1", "mapMessage --- v1");
            mapMessage.setStringProperty("kk", "vip property map message");
            producer.send(mapMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("******* messages have been sent to queue_01");
    }
}
