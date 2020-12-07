package org.tx;

/**
 * @author WangChao
 * @create 2020/12/7 6:30
 */
public class SocketManager {
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
}
