package ru.inno;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class Listener {
    private final Logger   logger = LoggerFactory.getLogger(Listener.class);
    @Autowired
    private       Producer producer;

    @JmsListener(destination = "inno.out.topic", containerFactory = "topicJmsListenerContainerFactory")
    public void receiveTopicMessage(final Message message) throws JMSException {
        logger.info("(receiveTopicMessage) Received message {}", message);
        producer.sendMessage("inno.in.queue", message.getBody(String.class), false);
    }


    @JmsListener(destination = "inno.in.queue", containerFactory = "queueJmsListenerContainerFactory")
    @Transactional
    public void receiveQueueMessage(final Message message) {
        logger.info("(receiveQueueMessage) Received message {}", message);
        throw new RuntimeException("ERROR!");
    }
}
