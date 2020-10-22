package com.alexpower.queue.consumer.case1;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
/*
* 消费方式一： receive 阻塞式消费
*
* */
public class QueueConsumer {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue_01";

    public static void main(String[] args) throws JMSException, IOException {
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

        // receive message
        while(true) {
            TextMessage textMessage = (TextMessage) consumer.receive(3000L); // timeout
            if(textMessage != null)
            System.out.println("consumed message: " + textMessage.getText());
            else {
                break;
            }
        }

        System.in.read(); // keep consumer up
        consumer.close();
        session.close();
        connection.close();
        System.out.println("******* messages have been received from queue_01");
    }

}
