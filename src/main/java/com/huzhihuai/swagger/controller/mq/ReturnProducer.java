/**
 * Copyright(C) 2020 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.huzhihuai.swagger.controller.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author HuZhihuai
 * @version $Id$
 * @since 2020年12月10日 2:13 下午
 */
public class ReturnProducer {

    public static DefaultMQProducer producer;

    static {
        producer = new DefaultMQProducer("PID_huzhihuai_return_group");
        // Specify name server addresses.
        producer.setNamesrvAddr("172.16.20.154:9876");
        //Launch the instance.
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public static void send(String result,String key) throws Exception {
        Message msg = new Message("topic_huzhihuai", "TagA_return", (result).getBytes(RemotingHelper.DEFAULT_CHARSET));
        msg.setKeys(key);
        producer.send(msg);
    }
}
