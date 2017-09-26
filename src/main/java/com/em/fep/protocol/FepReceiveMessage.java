package com.em.fep.protocol;

import io.netty.buffer.ByteBuf;

public interface FepReceiveMessage {

    FepMessageHeader getHeader();

    void parseBody(ByteBuf body);

    void setHeader(FepMessageHeader header);
}
