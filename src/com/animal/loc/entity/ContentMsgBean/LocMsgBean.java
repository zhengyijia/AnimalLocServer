package com.animal.loc.entity.ContentMsgBean;

import com.animal.loc.entity.AnimalLocMsg;
import com.animal.loc.entity.BaseMsgBean;

import javax.xml.bind.DatatypeConverter;

// 定位数据包
public class LocMsgBean extends BaseMsgBean {

    private String time;                    // 时间，6byte
    private byte gpsDataLength;             // GPS数据长度，4bit（数据长度为经纬度加GPS速度共9byte？）
    private byte satelliteNum;              // 可见卫星个数，4bit
    private int longitude;                  // 经度，4byte
    private int latitude;                   // 纬度，4byte
    private byte velocity;                  // 速度，1byte
    private boolean isGPSEnable;            // GPS是否定位
    private boolean isWestLongitude;        // 东西经
    private boolean isNorthLatitude;        // 南北纬
    private short track;                    // 航向，10bit（正北为0）

    public LocMsgBean(AnimalLocMsg animalLocMsg) {

        byte[] content = animalLocMsg.getContent();

        setPackageLen(animalLocMsg.getPackageLen());
        setProtocolNo(animalLocMsg.getProtocolNo());

        byte[] timeTemp = new byte[6];
        System.arraycopy(content, 0, timeTemp, 0, 6);
        time = DatatypeConverter.printHexBinary(timeTemp);

        // 注意byte最高位为1时，即符号位为1，进行位运算转int时前面会补1
        gpsDataLength = (byte)((content[6] & 0xFF) >>> 4);

        satelliteNum = (byte) (content[6] & 0x0f);

        // & 0xFF ： 把字节转换成int，避免移位时丢失高位字节
        longitude = content[10] & 0xFF |
                (content[9] & 0xFF) << 8 |
                (content[8] & 0xFF) << 16 |
                (content[7] & 0xFF) << 24;

        latitude = content[14] & 0xFF |
                (content[13] & 0xFF) << 8 |
                (content[12] & 0xFF) << 16 |
                (content[11] & 0xFF) << 24;

        velocity = animalLocMsg.getContent()[15];

        isGPSEnable = (animalLocMsg.getContent()[16] & 0x10) != 0;

        isWestLongitude = (animalLocMsg.getContent()[16] & 0x08) != 0;

        isNorthLatitude = (animalLocMsg.getContent()[16] & 0x04) != 0;

        track = (short)(content[17] & 0xFF |
                content[16] & 0x03 << 8);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte getGpsDataLength() {
        return gpsDataLength;
    }

    public void setGpsDataLength(byte gpsDataLength) {
        this.gpsDataLength = gpsDataLength;
    }

    public byte getSatelliteNum() {
        return satelliteNum;
    }

    public void setSatelliteNum(byte satelliteNum) {
        this.satelliteNum = satelliteNum;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public byte getVelocity() {
        return velocity;
    }

    public void setVelocity(byte velocity) {
        this.velocity = velocity;
    }

    public boolean isGPSEnable() {
        return isGPSEnable;
    }

    public void setGPSEnable(boolean GPSEnable) {
        isGPSEnable = GPSEnable;
    }

    public boolean isWestLongitude() {
        return isWestLongitude;
    }

    public void setWestLongitude(boolean westLongitude) {
        isWestLongitude = westLongitude;
    }

    public boolean isNorthLatitude() {
        return isNorthLatitude;
    }

    public void setNorthLatitude(boolean northLatitude) {
        isNorthLatitude = northLatitude;
    }

    public short getTrack() {
        return track;
    }

    public void setTrack(short track) {
        this.track = track;
    }

    @Override
    public AnimalLocMsg encodeContentMsg() {
        AnimalLocMsg animalLocMsg = new AnimalLocMsg();

        // 设置起始位
        animalLocMsg.setStartBit(getStartBit());
        // 设置数据长度
        animalLocMsg.setPackageLen(getPackageLen());
        // 设置协议号
        animalLocMsg.setProtocolNo(getProtocolNo());

        // 设置数据内容
        byte[] content = new byte[animalLocMsg.getPackageLen() - 1];

        byte[] timeTemp = DatatypeConverter.parseHexBinary(time);
        System.arraycopy(timeTemp, 0, content, 0, 6);

        content[6] = (byte)((gpsDataLength << 4) | satelliteNum);

        content[7] = (byte) (longitude >>> 24);
        content[8] = (byte) ((longitude & 0x00FF0000) >>> 16);
        content[9] = (byte) ((longitude & 0x0000FF00) >>> 8);
        content[10] = (byte) (longitude & 0x000000FF);

        content[11] = (byte) (latitude >>> 24);
        content[12] = (byte) ((latitude & 0x00FF0000) >>> 16);
        content[13] = (byte) ((latitude & 0x0000FF00) >>> 8);
        content[14] = (byte) (latitude & 0x000000FF);

        content[15] = velocity;

        int isGPSEnableTemp = isGPSEnable ? 0x10 : 0x00;
        int isWestLongitudeTemp = isWestLongitude ? 0x08 : 0x00;
        int isNorthLatitudeTemp = isNorthLatitude ? 0x04 : 0x00;
        content[16] = (byte) (isGPSEnableTemp |
                isWestLongitudeTemp |
                isNorthLatitudeTemp |
                track >>> 8);

        content[17] = (byte) (track & 0x00FF);

        animalLocMsg.setContent(content);

        // 设置停止位
        animalLocMsg.setEndBit(getEndBit());

        return animalLocMsg;
    }
}
