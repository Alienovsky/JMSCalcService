package com.madebykamil.jms;


import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

public class MessageSender {

    private final JmsTemplate jmsTemplate;

    public MessageSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final Map map) {
        jmsTemplate.convertAndSend(map);
    }

    public void send(final String textMessage){
        MessageCreator messageCreator = new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(textMessage);
            }
        };
        jmsTemplate.send(messageCreator);
    }



}