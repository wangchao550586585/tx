package org.tx.netty;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangchao
 * @description: TODO
 * @date 2020/12/11 14:27
 */
public class Task {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    String key;
    private Back back;

    public Task(String key) {
        this.key=key;
    }

    public void await() {
        reentrantLock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
        } finally {
            reentrantLock.unlock();
        }
    }

    public void signal() {
        reentrantLock.lock();
        try {
            condition.signal();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void setBack(Back back) {
        this.back = back;
    }

    public Object exec() {
        return back.call();
    }

    public void remove() {
        TaskManager.instance().remove(getKey());
    }

    public String getKey() {
        return key;
    }

}
