/**
 * Copyright(C) 2020 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.huzhihuai.swagger.controller;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.LockSupport;

/**
 * @author HuZhihuai
 * @version $Id$
 * @since 2020年12月10日 2:30 下午
 */
public class ResultFuture implements Future<Object> {

    private volatile int state;
    private volatile Object outcome;
    // TODO 暂且仅允许一个线程获取
    private volatile Thread waitThread;
    private static final int NEW = 0;
    private static final int COMPLETING = 1;
    private static final int NORMAL = 2;
    private static final int EXCEPTIONAL = 3;
    private static final int CANCELLED = 4;
    private static final int INTERRUPTING = 5;

    private Object report(int s) throws ExecutionException {
        Object x = outcome;
        if (s == NORMAL) {
            return x;
        }
        if (s >= CANCELLED) {
            throw new CancellationException();
        }
        throw new ExecutionException((Throwable)x);
    }

    private int awaitDone(boolean timed, long nanos) throws InterruptedException {
        // TODO 先增后减的原因
        final long deadline = timed ? System.nanoTime() + nanos : 0L;
        for (; ; ) {
            if (Thread.interrupted()) {
                state = INTERRUPTING;
                throw new InterruptedException();
            }
            int s = state;
            if (s > COMPLETING) {
                return s;
            } else if (waitThread == null) {
                waitThread = Thread.currentThread();
            } else if (timed) {
                nanos = deadline - System.nanoTime();
                if (nanos <= 0L) {
                    return state;
                }
                LockSupport.parkNanos(this, nanos);
            } else {
                LockSupport.park(this);
            }
        }
    }

    @Override
    public boolean isCancelled() {
        return state >= CANCELLED;
    }

    @Override
    public boolean isDone() {
        return state != NEW;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        // TODO 暂为单线程处理
        if (waitThread != null) {
            throw new RuntimeException("已经有线程" + waitThread.getName() + "在该对象上等待, 目前仅允许一个线程获取结果");
        } else {
            waitThread = Thread.currentThread();
        }
        int s = state;
        if (s <= COMPLETING) {
            s = awaitDone(false, 0L);
        }
        return report(s);
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        // TODO 暂为单线程处理
        if (waitThread != null) {
            throw new RuntimeException("已经有线程" + waitThread.getName() + "在该对象上等待, 目前仅允许一个线程获取结果");
        } else {
            waitThread = Thread.currentThread();
        }
        if (unit == null) {
            throw new NullPointerException();
        }
        int s = state;
        if (s <= COMPLETING && (s = awaitDone(true, unit.toNanos(timeout))) <= COMPLETING) {
            throw new TimeoutException();
        }
        return report(s);
    }

    @Override
    public boolean cancel(boolean paramBoolean) {
        throw new RuntimeException("不能取消远程调用消息");
    }

    public void set(Object result) {
        outcome = result;
        state = NORMAL;
        if (result instanceof Throwable) {
            state = EXCEPTIONAL;
        }
        LockSupport.unpark(waitThread);
    }

}

