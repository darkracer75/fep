package com.em.fep.protocol;

public interface FepMessageHeader extends Cloneable {

    int getHeaderLength();

    void parseHeader(byte[] headerByte);

    FepMessageHeader clone();
}
