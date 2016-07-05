package com.madebykamil.jms;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import javax.jms.*;
import java.util.List;

public class MessageConsumer implements MessageListener {

    private MessageSender messageSender;

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void onMessage(final Message message) {
        if(message instanceof TextMessage){
            final TextMessage textMessage = (TextMessage) message;
            try {
                String formula = textMessage.getText();
                Iterable<String> it = Splitter.on(',').trimResults().omitEmptyStrings().split(formula);
                final List<String> stringList = Lists.newArrayList(it);
                String result = calculateTheResult(stringList);
                System.out.println(formula);
                System.out.println(result);
                messageSender.send(result);
            } catch (JMSException e) {
                messageSender.send(e.getMessage());
            }
        }
        if (message instanceof MapMessage) {
            final MapMessage mapMessage = (MapMessage) message;
            try {
                System.out.println(mapMessage.getMapNames());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private String calculateTheResult(List<String> stringList) throws JMSException {
        float x = Float.parseFloat(stringList.get(0));
        float y = Float.parseFloat(stringList.get(1));
        char sign = stringList.get(2).toCharArray()[0];
        switch (sign){
            case '+':
               return String.valueOf(x+y);
            case '-':
                return String.valueOf(x-y);
            case '/':
                return String.valueOf(x/y);
            case '*':
                return String.valueOf(x*y);
        }
        throw new JMSException("Such operation is not supported: "+sign);
    }
}
