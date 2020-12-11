/**
 * Copyright(C) 2020 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.huzhihuai.swagger.controller.mq;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.huzhihuai.swagger.controller.ResultFuture;

/**
 * @author HuZhihuai
 * @version $Id$
 * @since 2020年12月10日 2:14 下午
 */
public class ReturnTest {

    public static final Map<String, ResultFuture> results = new HashMap<>();

    public static String holle(String tag) {
        System.out.println("接受到数据：" + tag);
        return "Hello:" + tag;
    }

    public static void main(String[] args) throws Exception, InterruptedException {
        Consumer.init();
        ReturnConsumer.init();
        Thread.sleep(5000L);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int j = i;
            executorService.submit(() -> {
                try {
                    long l = System.currentTimeMillis();
                    System.out
                            .println("收到返回结果：" + SyncProducer.send("mq" + j) + ":" + (System.currentTimeMillis() - l));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

    }

}
