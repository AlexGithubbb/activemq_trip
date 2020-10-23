package com.alexpower.topic.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/*
 *
 * */
public class JmsConsumer_topic_persistent {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String TOPIC_NAME = "Topic_persist";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("this is persistent subscriber Alex ");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // create connection from connection factory
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("Alex");

        // create session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // crate destination
        Topic topic = session.createTopic(TOPIC_NAME);
        // create topic subscriber
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark...");

        // start connection up
        connection.start();

        // keep subscribing up
        Message message = topicSubscriber.receive();

        while (null != message){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("receive persistent topic message " + textMessage.getText());
            message = topicSubscriber.receive(1000L);
        }

        session.close();
        connection.close();
        System.out.println("******* received all msgs from Topic_persist");
    }

}
