/**
 * Copyright(C) 2020 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.huzhihuai.swagger.controller.mq;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import com.huzhihuai.swagger.controller.ResultFuture;

/**
 * @author HuZhihuai
 * @version $Id$
 * @since 2020年12月07日 11:46 上午
 */
public class SyncProducer {
    public static DefaultMQProducer producer;

    static {
        producer = new DefaultMQProducer("PID_huzhihuai_group");
        // Specify name server addresses.
        producer.setNamesrvAddr("172.16.20.154:9876");
        //Launch the instance.
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public static Object send(String tag) throws Exception {
        ResultFuture resultFuture = new ResultFuture();
        Message msg = new Message("topic_huzhihuai", "TagA", (tag).getBytes(RemotingHelper.DEFAULT_CHARSET));
        String transactionId = UUID.randomUUID().toString();
        msg.setKeys(transactionId);
        ReturnTest.results.put(transactionId, resultFuture);
        producer.send(msg);
        System.out.println("发送成功：" + tag);
        return resultFuture.get(10, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(send("mq"));
    }
}
