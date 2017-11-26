package entity.ContentMsgBean;

import entity.AnimalLocMsg;
import entity.BaseMsgBean;

import javax.xml.bind.DatatypeConverter;

public class LoginMsgBean extends BaseMsgBean {

    private String IMEI;    // IMEI号，8byte
    private byte version;   // 软件版本号

    public LoginMsgBean(AnimalLocMsg animalLocMsg) {
        setPackageLen(animalLocMsg.getPackageLen());
        setProtocolNo(animalLocMsg.getProtocolNo());

        IMEI = DatatypeConverter.printHexBinary(animalLocMsg.getContent()).substring(0, 16);

        version = animalLocMsg.getContent()[8];
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
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
        byte[] content = new byte[getPackageLen() - 1];
        byte[] IMEITemp = DatatypeConverter.parseHexBinary(IMEI);
        System.arraycopy(IMEITemp, 0, content, 0, 8);

        content[8] = version;

        animalLocMsg.setContent(content);

        // 设置停止位
        animalLocMsg.setEndBit(getEndBit());

        return animalLocMsg;
    }

}
