package com.em.fep.protocol.bc;

import com.em.fep.protocol.FepMessageFactory;
import com.em.fep.protocol.FepMessageHeader;
import com.em.fep.protocol.FepReceiveMessage;
import com.em.fep.protocol.bc.server.FepMessage320310;
import com.em.fep.protocol.bc.server.FepMessage600610;
import com.em.fep.protocol.bc.server.FepMessage620;
import com.em.fep.protocol.bc.server.FepMessage630640;

public class BCMessageFactory implements FepMessageFactory {

    @Override
    public FepReceiveMessage getFepReceiveMessage(FepMessageHeader messageHeader) {

        BCMessageHeader bcMessageHeader = (BCMessageHeader) messageHeader;

        if ("0600".equals(bcMessageHeader.getKindCode())) {
            return new FepMessage600610();
        } else if ("0630".equals(bcMessageHeader.getKindCode())) {
            return new FepMessage630640();
        } else if ("0320".equals(bcMessageHeader.getKindCode())) {
            return new FepMessage320310();
        } else if ("0620".equals(bcMessageHeader.getKindCode())) {
            return new FepMessage620();
        }

        return null;
    }

    @Override
    public FepMessageHeader getFepMessageHeader() {
        return new BCMessageHeader();
    }
}
