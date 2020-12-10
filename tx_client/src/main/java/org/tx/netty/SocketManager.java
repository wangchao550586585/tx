package org.tx.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.tx.factory.bean.Request;

import java.util.Objects;

/**
 * @author WangChao
 * @create 2020/12/7 6:30
 */
public class SocketManager {
    ChannelHandlerContext ctx;

    private static class SocketManagerHolder {
        static final SocketManager INSTANCE = createSocketManager();

        private static SocketManager createSocketManager() {
            return new SocketManager();
        }
    }

    public static SocketManager instance() {
        return SocketManagerHolder.INSTANCE;
    }

    /**
     * 标记socket连接状态
     */
    private volatile boolean netState;

    public void flagClose() {
        this.netState = false;
    }

    public void flagOpen() {
        this.netState = true;
    }

    public boolean isNetState() {
        return netState;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void sendMsg(final Request request) {
        if (Objects.isNull(ctx) || ctx.channel() == null || !ctx.channel().isActive()) {
        } else {
            ctx.writeAndFlush(Unpooled.buffer().writeBytes(request.toMsg().getBytes()));
        }

    }
}
