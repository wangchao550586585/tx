package org.tx.netty;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.util.ObjectUtils;
import org.tx.netty.NettyService;
import org.tx.netty.SocketManager;


/**
 * @author wangchao
 * @date 2020年12月5日 07:56:07
 */
@Sharable
public class TXClientHandler
        extends ChannelInboundHandlerAdapter {
    private NettyService echoClient;
    private String heartData;

    public TXClientHandler(NettyService echoClient) {
        this.echoClient = echoClient;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", "h");
        jsonObject.put("k", "h");
        jsonObject.put("p", "{}");
        heartData = jsonObject.toString();

    }

    /**
     * 在到服务器的连接已经建立之后将被调用；
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知 Channel是活跃的时候，发送一条消息
        ctx.writeAndFlush(Unpooled.copiedBuffer(heartData,
                CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        String jsonData = getJsonData(msg);
        if (!ObjectUtils.isEmpty(jsonData)) {
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            if (jsonObject.containsKey("a")) {
                SocketManager.instance().flagOpen();
            }
        }
    }

    private String getJsonData(Object msg) {
        ByteBuf in = (ByteBuf) msg;
        String s = in.toString(CharsetUtil.UTF_8);
        ReferenceCountUtil.release(msg);
        return s;
    }

    @Override
    //在发生异常时，记录错误并关闭Channel
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
//        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SocketManager.instance().flagClose();
        //执行到这里,channel已经关闭
        //重新连接
        echoClient.restart();
    }


    /**
     * 4S没事件,触发
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //(2) 发送心跳消息，并在发送失败时关闭该连接
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                //好久没收到消息
            } else if (event.state() == IdleState.WRITER_IDLE) {
                //好久没发消息
                //发送心跳包
                ctx.writeAndFlush(Unpooled.buffer().writeBytes(heartData.getBytes()));
            } else if (event.state() == IdleState.ALL_IDLE) {
                //好久没收发消息
            }
        } else {
            //不是IdleStateEvent事件,所以将它传递给下一个ChannelInboundHandler
            super.userEventTriggered(ctx, evt);
        }
    }
}
