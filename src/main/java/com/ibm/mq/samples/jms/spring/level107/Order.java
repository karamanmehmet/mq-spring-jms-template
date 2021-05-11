package com.ibm.mq.samples.jms.spring.level107;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private String id;
    private String from;
    private String to;
    private int amount;
}