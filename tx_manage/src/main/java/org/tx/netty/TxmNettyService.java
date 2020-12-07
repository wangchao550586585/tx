package org.tx.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class TxmNettyService {
    private final int port;
    EventLoopGroup group;

    public TxmNettyService(int port) {
        this.port = port;
    }

    public static void main(String[] args)
            throws Exception {
        int port = 8080;
        new TxmNettyService(port).start();
    }

    public void start() throws Exception {
        TxmNettyHandler serverHandler = new TxmNettyHandler();
        group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    //指定多余连接请求存放队列大小
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("timeout", new IdleStateHandler(25, 25, 25, TimeUnit.SECONDS));
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            b.bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        //(8) 关闭 EventLoopGroup，释放所有的资源
        if (Objects.isNull(group)) {
        } else {
            group.shutdownGracefully();
        }
    }
}
