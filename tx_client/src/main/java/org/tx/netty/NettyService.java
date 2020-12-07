package org.tx.netty;

/**
 * @author WangChao
 * @create 2020/12/7 8:30
 */
public interface NettyService {
    public void start();

    public void close();

    public void restart();
}
