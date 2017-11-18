package com.animal.loc.entity;

public class AnimalLocMsg {

    private short startBit;     // 起始位
    private byte packageLen;    // 数据长度
    private byte protocolNo;    // 协议号
    private byte[] content;     // 数据内容
    private short endBit;       // 停止位

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public short getEndBit() {
        return endBit;
    }

    public void setEndBit(short endBit) {
        this.endBit = endBit;
    }
}
