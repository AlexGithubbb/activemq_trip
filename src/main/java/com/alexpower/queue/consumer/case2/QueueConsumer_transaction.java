package com.alexpower.queue.consumer.case2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.soap.Text;
import java.io.IOException;

/*
 *  消费方式二： 监听器机制，异步非阻塞
 *   two listeners : 底层轮询机制 ： 7 messages in total : ListenerA - (1,3,5,7), ListenerB - (2,4,6)
 *
 * */
public class QueueConsumer_transaction {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue_trasc";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("this is consumer1 ");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        // create connection from connection factory
        Connection connection = activeMQConnectionFactory.createConnection();
        // start connection up
        connection.start();
        // create session
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        // crate destination
        Queue queue = session.createQueue(QUEUE_NAME);

        // create producer and point out the destination
        MessageConsumer consumer = session.createConsumer(queue);

        // listener
        while (true){
            Message message = consumer.receive(3000L);
            if(message != null){
                TextMessage textMessage = (TextMessage) message;
                textMessage.acknowledge();
                System.out.println("consume text " + textMessage.getText());
                 // wait for 3s and terminate
            }else{
                break;
            }
        }
        consumer.close();
//        session.commit();
        session.close();
        connection.close();
        System.out.println("******* messages have been received from queue_trasc");
    }

}
