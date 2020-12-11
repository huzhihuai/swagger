/**
 * Copyright(C) 2020 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.huzhihuai.swagger.controller.mq;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import com.huzhihuai.swagger.controller.ResultFuture;

/**
 * @author HuZhihuai
 * @version $Id$
 * @since 2020年12月07日 1:56 下午
 */
public class ReturnConsumer {

    public static void init() throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("CID_huzhihuai_return_group");

        // Specify name server addresses.
        consumer.setNamesrvAddr("172.16.20.154:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("topic_huzhihuai", "TagA_return");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                String body = new String(messageExt.getBody());
                String key = messageExt.getKeys();
                ResultFuture resultFuture = ReturnTest.results.get(key);
                if (resultFuture != null) {
                    resultFuture.set(body);
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("ReturnConsumer Started.%n");
    }
}

