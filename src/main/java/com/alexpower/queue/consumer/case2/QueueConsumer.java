package com.alexpower.queue.consumer.case2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/*
 *  消费方式二： 监听器机制，异步非阻塞
 *   two listeners : 底层轮询机制 ： 7 messages in total : ListenerA - (1,3,5,7), ListenerB - (2,4,6)
 *
 * */
public class QueueConsumer {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue_01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("this is consumer1 ");
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
        MessageConsumer consumer = session.createConsumer(queue);

        // listener
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("consumed message: " + textMessage.getText());
                        System.out.println(textMessage.getStringProperty("007"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read(); // keep reading in back
        consumer.close();
        session.close();
        connection.close();
        System.out.println("******* messages have been received from queue_01");
    }

}
