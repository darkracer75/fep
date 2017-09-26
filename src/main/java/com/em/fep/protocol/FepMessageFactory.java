package com.em.fep.protocol;

public interface FepMessageFactory {
    FepReceiveMessage getFepReceiveMessage(FepMessageHeader messageHeader);

    FepMessageHeader getFepMessageHeader();
}
