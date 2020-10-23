package com.alexpower.queue.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer_tracsaction {
    public static final String BROKER_URL = "tcp://127.0.0.1:61616";
    public static final String QUEUE_NAME = "queue_trasc";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // create session (transacted, acknowledge)
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);


        // send message
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("tx msg---" + i);
            producer.send(textMessage);
        }

        producer.close();
        // for security
        try {
            session.commit();
        }catch (Exception e){
            session.rollback();
        }finally {
            if(session != null)
                session.close();
            if(connection!= null)
                connection.close();
        }
        System.out.println("******* Tx messages have been sent to queue_trasc");
    }
}
