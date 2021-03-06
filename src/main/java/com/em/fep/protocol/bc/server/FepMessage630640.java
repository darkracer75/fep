package com.em.fep.protocol.bc.server;

import com.em.fep.protocol.FepMessageHeader;
import com.em.fep.protocol.bc.BCMessageHeader;
import com.em.fep.protocol.FepReceiveMessage;
import com.em.fep.protocol.FepSendMessage;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

@Getter
@Setter
@ToString
public class FepMessage630640 implements FepReceiveMessage, FepSendMessage {

    private BCMessageHeader header;
    private String fileName;
    private long fileSize;
    private int byteLength;

    public void parseBody(ByteBuf body) {

        String bodyStr = body.toString(Charset.defaultCharset());

        fileName = bodyStr.substring(0, 8);
        fileSize = Long.valueOf(bodyStr.substring(8, 20));
        byteLength = Integer.valueOf(bodyStr.substring(20, 24));
    }

    public void setHeader(FepMessageHeader header) {
        this.header = (BCMessageHeader) header;
    }

    public byte[] toByteArray() {

        StringBuffer s = new StringBuffer();
        s.append(StringUtils.leftPad(String.valueOf(header.getLength()), 4, '0'))
                .append(StringUtils.rightPad(header.getTaskDivCode(), 3))
                .append(StringUtils.rightPad(header.getOrganCode(), 2))
                .append(StringUtils.rightPad(header.getKindCode(), 4))
                .append(StringUtils.rightPad(header.getTransDivCode(), 1))
                .append(StringUtils.rightPad(header.getSendRcvFlag(), 1))
                .append(StringUtils.rightPad(header.getFileName(), 8))
                .append(StringUtils.rightPad(header.getResponseCode(), 3))
                .append(StringUtils.rightPad(fileName, 8))
                .append(StringUtils.leftPad(String.valueOf(fileSize), 12, '0'))
                .append(StringUtils.leftPad(String.valueOf(byteLength), 4, '0'));

        return s.toString().getBytes();
    }
}
