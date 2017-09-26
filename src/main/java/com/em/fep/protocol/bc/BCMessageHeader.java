package com.em.fep.protocol.bc;

import com.em.fep.exception.FepMessageException;
import com.em.fep.protocol.FepMessageHeader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.ArrayUtils;

@Setter@Getter
@ToString
public class BCMessageHeader implements FepMessageHeader {

    public static final int HEADER_LENGTH = 26;

    private int length;
    private String taskDivCode;
    private String organCode;
    private String kindCode;
    private String transDivCode;
    private String sendRcvFlag;
    private String fileName;
    private String responseCode;

    public FepMessageHeader clone()  {
        try {
            return (FepMessageHeader) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void parseHeader(byte[] headerByte) {

        if (getHeaderLength() != headerByte.length) {
            throw new FepMessageException("헤더 길이 오류");
        }

        setLength(Integer.valueOf(new String(ArrayUtils.subarray(headerByte, 0, 4))));
        setTaskDivCode(new String(ArrayUtils.subarray(headerByte, 4, 7)));
        setOrganCode(new String(ArrayUtils.subarray(headerByte, 7, 9)));
        setKindCode(new String(ArrayUtils.subarray(headerByte, 9, 13)));
        setTransDivCode(new String(ArrayUtils.subarray(headerByte, 13, 14)));
        setSendRcvFlag(new String(ArrayUtils.subarray(headerByte, 14, 15)));
        setFileName(new String(ArrayUtils.subarray(headerByte, 15, 23)));
        setResponseCode(new String(ArrayUtils.subarray(headerByte, 23, 26)));
    }

    @Override
    public int getHeaderLength() {
        return HEADER_LENGTH;
    }
}
