package com.ibm.mq.samples.jms.spring.rest;

import com.ibm.mq.samples.jms.spring.globals.data.OurData;
import com.ibm.mq.samples.jms.spring.globals.data.ReplyData;
import com.ibm.mq.samples.jms.spring.level107.Sender;

import com.ibm.mq.samples.jms.spring.level107.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.JMSException;
import java.util.Random;
import java.util.UUID;

@RestController
@Slf4j
public class DemoController {


    @Autowired
    Sender sender;

    @GetMapping(value = "/send")
    public String send() throws JMSException {

     //   for (int i=0;i<1000;i++
    //         ) {
            String correlationId = sender.sendOrder("order-00"+900);
     //   }




        String  status = sender.receiveOrderStatus(correlationId);

        return status;

    }
}