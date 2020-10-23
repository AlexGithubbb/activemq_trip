package com.alexpower.queue.consumer.case2;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.soap.Text;
import java.io.IOException;

/*
 *  。在事务性会话中， 当一个事务被成功提交则消息被自动签收
 *    如果事务回滚，消息会被再次传送
 *  。在非事务性会话中，消息何时被确认取决于是自动签收还是手动签收（手动时需要显式调用acknowledge() 方法)
 *
 * transacted > acknowledge
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
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        // crate destination
        Queue queue = session.createQueue(QUEUE_NAME);

        // create producer and point out the destination
        MessageConsumer consumer = session.createConsumer(queue);

        // listener
        while (true){
            Message message = consumer.receive(3000L);
            if(message != null){
                TextMessage textMessage = (TextMessage) message;
//                textMessage.acknowledge();
                System.out.println("consume text " + textMessage.getText());
            }else{
                break;
            }
        }
        consumer.close();
        session.commit();
        session.close();
        connection.close();
        System.out.println("******* messages have been received from queue_trasc");
    }

}
