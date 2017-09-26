package com.em.fep.protocol;

public interface FepMessageProcessor {

    FepSendMessage process(FepReceiveMessage reqMsg);
}
