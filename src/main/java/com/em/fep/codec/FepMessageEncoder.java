package com.em.fep.codec;

import com.em.fep.protocol.FepSendMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class FepMessageEncoder extends MessageToMessageEncoder<FepSendMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, FepSendMessage msg, List<Object> out) throws Exception {

        byte[] bytes = msg.toByteArray();

        if (bytes.length == 0) {
            return;
        }

        out.add(ctx.alloc().buffer().writeBytes(bytes));
    }
}
