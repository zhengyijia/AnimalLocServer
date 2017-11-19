package com.animal.loc.entity;

import com.animal.loc.entity.AnimalLocMsg;

public abstract class BaseMsgBean {

    private short startBit = 0x7878;     // 起始位
    private byte packageLen;             // 数据长度
    private byte protocolNo;             // 协议号
    private short endBit = 0x0d0a;       // 停止位

    public short getStartBit() {
        return startBit;
    }

    public void setStartBit(short startBit) {
        this.startBit = startBit;
    }

    public byte getPackageLen() {
        return packageLen;
    }

    public void setPackageLen(byte packageLen) {
        this.packageLen = packageLen;
    }

    public byte getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(byte protocolNo) {
        this.protocolNo = protocolNo;
    }

    public short getEndBit() {
        return endBit;
    }

    public void setEndBit(short endBit) {
        this.endBit = endBit;
    }

    // 对数据进行编码
    public abstract AnimalLocMsg encodeContentMsg();
}
