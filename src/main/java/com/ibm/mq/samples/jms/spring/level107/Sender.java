package com.ibm.mq.samples.jms.spring.level107;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
public class Sender {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Sender.class);

    @Autowired
    private Destination statusDestination;

    @Autowired
    private JmsTemplate jmsTemplate;

    public String sendOrder(String orderNumber) throws JMSException {
        final AtomicReference<Message> message = new AtomicReference<>();

        Order order = new Order( orderNumber,"Adana","Mersin", ThreadLocalRandom.current().nextInt());
        jmsTemplate.convertAndSend(order, messagePostProcessor -> {
            message.set(messagePostProcessor);
            return messagePostProcessor;
        });

        String messageId = message.get().getJMSMessageID();
        LOGGER.info("sending OrderNumber='{}' with MessageId='{}'",
                order, messageId);

        return messageId;
    }

    public String receiveOrderStatus(String correlationId) {
        String status =  (String)  jmsTemplate.receiveSelectedAndConvert(
                statusDestination,
                "JMSCorrelationID = '" + correlationId + "'");
        LOGGER.info("receive Status='{}' for CorrelationId='{}'", status,
                correlationId);

        return status;
    }
}
