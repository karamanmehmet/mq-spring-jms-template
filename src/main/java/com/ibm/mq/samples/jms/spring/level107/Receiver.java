package com.ibm.mq.samples.jms.spring.level107;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.TextMessage;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
public class Receiver {

    @Autowired
    private Destination statusDestination;

    @Autowired
    private JmsTemplate jmsTemplate;

    public static final String REQ_QUEUE = "DEV.QUEUE.2";
    public static final String RES_QUEUE = "DEV.QUEUE.4";

    @JmsListener(destination = REQ_QUEUE)
    public void receiveOrder(Order order,
                             @Header(JmsHeaders.MESSAGE_ID) String messageId) {
        log.info("received OrderNumber='{}' with MessageId='{}'",
                order, messageId);

        log.info("sending Status='Accepted' with CorrelationId='{}'",
                messageId);

        Shipment shipment = new Shipment("Reply-->"+order.toString(), ThreadLocalRandom.current().nextInt()+"");

        jmsTemplate.send(statusDestination, messageCreator -> {
            TextMessage message =
                    messageCreator.createTextMessage(shipment.toString());
            message.setJMSCorrelationID(messageId);
            return message;
        });
    }
}
