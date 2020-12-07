package org.tx.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class NettyServiceImpl implements NettyService, DisposableBean {
    private final String host="127.0.0.1";
    private final int port=8080;
    NioEventLoopGroup group;
    private static volatile boolean isStarting = false;



    @Override
    public synchronized void start() {
        if (isStarting) {
            return;
        }
        isStarting = true;
        group = new NioEventLoopGroup();
        TXClientHandler echoClientHandler = new TXClientHandler(this);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast("timeout", new IdleStateHandler(4, 4, 4, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(echoClientHandler);
                        }
                    });
            ChannelFuture f = b.connect();

            f.addListener((ChannelFutureListener) channelFuture -> {
                if (!channelFuture.isSuccess()) {
                    channelFuture.channel().eventLoop().schedule(() -> {
                        isStarting = false;
                        start();
                    }, 5, TimeUnit.SECONDS);
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public synchronized void close() {
        if (Objects.isNull(group)) {
        } else {
            group.shutdownGracefully();
            group = null;
            SocketManager.instance().flagClose();
            isStarting = false;
        }
    }

    @Override
    public synchronized void restart() {
        close();
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void destroy() throws Exception {
        close();
    }
}
