package com.em.fep.protocol.bc.server;

import com.em.fep.protocol.FepMessageHeader;
import com.em.fep.protocol.bc.BCMessageHeader;
import com.em.fep.protocol.FepReceiveMessage;
import io.netty.buffer.ByteBuf;
import lombok.ToString;

import java.nio.charset.Charset;

@ToString
public class FepMessage620 implements FepReceiveMessage {

    private BCMessageHeader header;
    private int blockNo;
    private int sequenceNo;


    public void parseBody(ByteBuf body) {

        String bodyStr = body.readBytes(7).toString(Charset.defaultCharset());

        blockNo = Integer.valueOf(bodyStr.substring(0, 4));
        sequenceNo = Integer.valueOf(bodyStr.substring(4, 7));
    }

    public void setHeader(FepMessageHeader header) {
        this.header = (BCMessageHeader) header;
    }


    public FepMessageHeader getHeader() {
        return header;
    }

    public int getBlockNo() {
        return blockNo;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

}
