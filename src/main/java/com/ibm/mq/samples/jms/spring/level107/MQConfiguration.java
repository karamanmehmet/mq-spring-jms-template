/*
 * (c) Copyright IBM Corporation 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.mq.samples.jms.spring.level107;

import com.ibm.mq.jms.MQQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;



@Configuration
public class MQConfiguration {
    protected final Log logger = LogFactory.getLog(getClass());

    public static final String REQ_QUEUE = "DEV.QUEUE.2";
    public static final String RES_QUEUE = "DEV.QUEUE.4";



    @Autowired
    private ConnectionFactory connectionFactory;


    @Bean
    public Destination orderDestination() throws JMSException {
        return new  MQQueue(REQ_QUEUE);
    }

    @Bean
    public Destination statusDestination() throws JMSException {
        return new  MQQueue(RES_QUEUE);
    }

    @Bean
    public JmsTemplate orderJmsTemplate() throws JMSException {
        JmsTemplate jmsTemplate =
                new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(orderDestination());
        jmsTemplate.setReceiveTimeout(5000);

        return jmsTemplate;
    }

}
