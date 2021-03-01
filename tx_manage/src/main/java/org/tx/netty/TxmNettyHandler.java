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
import org.tx.redis.RedisService;
import org.tx.redis.RedisServiceImpl;

@Sharable
public class TxmNettyHandler extends ChannelInboundHandlerAdapter {
    RedisService redisService;

    public TxmNettyHandler(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 对于每个传入的消息都要调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        String jsonData = getJsonData(msg);
        service(jsonData, ctx);

    }

    private void service(String jsonData, ChannelHandlerContext ctx) {
        if (!ObjectUtils.isEmpty(jsonData)) {
            System.out.println(jsonData);
            JSONObject jsonObject = JSONObject.parseObject(jsonData);
            String action = jsonObject.getString("a");
            String key = jsonObject.getString("k");
            JSONObject params = JSONObject.parseObject(jsonObject.getString("p"));
            String channelAddress = ctx.channel().remoteAddress().toString();
            String res = "";
            switch (action) {
                //心跳
                case "h":
                    break;
                //注册
                case "cg":
                    res = execute(channelAddress, key, params);
                    break;
                //关闭
                case "ctg":
                    res = executeCtg(channelAddress, key, params);
                    break;
                //加入事务组
                case "atg":
                    res = executeAdd(channelAddress, key, params);
                    break;
               //注册信息  todo
                case "umi":
                    res = executeAdd(channelAddress, key, params);
                    break;
            }
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("k", key);
            jsonObject1.put("d", res);
            ctx.writeAndFlush(Unpooled.buffer().writeBytes(jsonObject1.toString().getBytes()));


        }
    }

    private String executeCtg(String channelAddress, String key, JSONObject params) {
        String groupId = params.getString("g");
        Integer s = params.getInteger("s");

        return null;
    }

    private String executeAdd(String channelAddress, String key, JSONObject params) {
        TxGroup txGroup = addTxGroup(params,channelAddress);
        txGroup.setNowTime(System.currentTimeMillis());
        return txGroup.toJsonString();
    }

    private TxGroup addTxGroup(JSONObject params,String channelAddress) {
        String groupId = params.getString("g");
        String taskId = params.getString("t");
        String methodStr = params.getString("ms");

        String key=RedisServiceImpl.key_prefix + groupId;
        TxGroup txGroup = redisService.getTransaction(key);

        TxInfo txInfo = new TxInfo();
        txInfo.setChannelAddress(channelAddress);
        txInfo.setTaskId(taskId);
        txInfo.setMethodStr(methodStr);
        txInfo.setAddress("127.0.0.1:8115");

        //这里欠缺注册信息 todo
        txGroup.addTxInfo(txInfo);
        redisService.saveTransaction(key,txGroup.toAllJsonString());
        return txGroup;
    }

    private String execute(String channelAddress, String key, JSONObject params) {
        TxGroup txGroup = createTxGroup(params);
        return txGroup.toJsonString();
    }

    private TxGroup createTxGroup(JSONObject params) {
        String groupId = params.getString("g");
        TxGroup txGroup = new TxGroup();
        txGroup.setGroupId(groupId);
        txGroup.setStartTime(System.currentTimeMillis());
        redisService.saveTransaction(RedisServiceImpl.key_prefix + groupId, txGroup.toJsonString());
        return txGroup;
    }

    private String getJsonData(Object msg) {
        ByteBuf in = (ByteBuf) msg;
        String s = in.toString(CharsetUtil.UTF_8);
        ReferenceCountUtil.release(msg);
        return s;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * 通知ChannelInboundHandler 最后一次对channel-Read()的调用是当前批量读取中的最后一条消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        ctx.flush();
    }

    /**
     * 在读取操作期间，有异常抛出时会调用。
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
//        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                //很久没收到信息上报,就下线该channnel
                ctx.close();
            }
        }
    }
}
