package com.alexpower.queue.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer_transaction {
    public static final String BROKER_URL = "nio://127.0.0.1:61618"; // add nio protocol in Activemq_home/conf/activemq.xml
    public static final String QUEUE_NAME = "jdbc_queue";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        // create session (transacted, acknowledge)
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageProducer producer = session.createProducer(queue);
        // need when integrated with outer db
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);


        // send message
        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("jdbc msg---" + i);
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
