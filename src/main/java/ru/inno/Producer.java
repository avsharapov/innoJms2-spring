package ru.inno;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Session;

@Component
class Producer {
    private final Logger logger = LoggerFactory.getLogger(Producer.class);
    @Autowired
    JmsTemplate jmsTemplate;

    void sendMessage(final String queueName, final String message, boolean isPubSubDomain) {
        logger.info("Sending message {} to queue - {}", message, queueName);
        jmsTemplate.setTimeToLive(3000);
        jmsTemplate.setPubSubDomain(isPubSubDomain);
        jmsTemplate.send(queueName, Session::createTextMessage);
    }

}
