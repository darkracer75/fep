package com.em.fep.codec;

import com.em.fep.protocol.FepMessageFactory;
import com.em.fep.protocol.FepMessageHeader;
import com.em.fep.protocol.FepReceiveMessage;
import com.em.fep.protocol.bc.BCMessageFactory;
import com.em.fep.protocol.bc.BCMessageHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

public class FepMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    public static final Charset charset = Charset.defaultCharset();

    private FepMessageFactory messageFactory;

    public FepMessageDecoder(FepMessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        FepMessageHeader header = messageFactory.getFepMessageHeader();

        header.parseHeader(ByteBufUtil.getBytes(msg.readBytes(header.getHeaderLength())));

        FepReceiveMessage fepRequest = messageFactory.getFepReceiveMessage(header);

        if (fepRequest != null) {
            fepRequest.setHeader(header);
            fepRequest.parseBody(msg.readBytes(msg.readableBytes()));
            out.add(fepRequest);
        }
    }

    public String readString(int length, ByteBuf in) {
        return in.readBytes(length).toString(charset);
    }

}
