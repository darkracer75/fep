package com.em.fep.server;

import com.em.fep.protocol.FepMessageProcessor;
import com.em.fep.protocol.FepReceiveMessage;
import com.em.fep.protocol.FepSendMessage;
import com.em.fep.protocol.bc.BCMessageHeader;
import com.em.fep.protocol.bc.server.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FepServerHandShakingHandler extends ChannelInboundHandlerAdapter {

    private FepMessageProcessor messageProcessor;

    public FepServerHandShakingHandler (FepMessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FepReceiveMessage) {

            FepReceiveMessage reqMsg = (FepReceiveMessage) msg;

            System.out.println(String.format("receive data request:[%s]", reqMsg));

            FepSendMessage resMsg = messageProcessor.process(reqMsg);

            if (resMsg != null) {
                ctx.writeAndFlush(resMsg);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
