package org.tx;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class EchoClient {
    private final String host;
    private final int port;
    NioEventLoopGroup group;
    private static volatile boolean isStarting = false;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public synchronized void start() {
        if (isStarting) {
            return;
        }
        isStarting = true;
        group = new NioEventLoopGroup();
        EchoClientHandler echoClientHandler = new EchoClientHandler(this);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
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

    public synchronized void close()  {
        if (Objects.isNull(group)) {
        } else {
            group.shutdownGracefully();
            group = null;
            isStarting = false;
        }
    }

    public synchronized void restart()  {
        close();
        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start();
    }

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 8080;
        new EchoClient(host, port).start();
    }
}
