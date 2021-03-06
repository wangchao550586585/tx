package org.tx.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import org.tx.factory.bean.Request;
import org.tx.task.Task;
import org.tx.task.TaskManager;

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

    public String sendMsg(final Request request) {
        String result = null;
        if (Objects.isNull(ctx) || ctx.channel() == null || !ctx.channel().isActive()) {
        } else {
            String k = request.getKey();
            Task task = TaskManager.instance().createTask(k);

            // TODO: 2021/2/24  这里可设置定时器给予默认返回值,以及线程池优化代码
            new Thread(() -> ctx.writeAndFlush(Unpooled.buffer().writeBytes(request.toMsg().getBytes()))).start();
            task.await();
            result = (String) task.exec();

            task.remove();
        }
        return result;

    }
}
