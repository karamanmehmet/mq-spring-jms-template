package com.ibm.mq.samples.jms.spring.level107;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shipment implements Serializable {

    private String orderId;
    private String trackingId;
}