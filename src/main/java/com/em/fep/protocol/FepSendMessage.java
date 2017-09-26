package com.em.fep.protocol;

public interface FepSendMessage {

    FepMessageHeader getHeader();

    byte[] toByteArray();

    void setHeader(FepMessageHeader header);
}
