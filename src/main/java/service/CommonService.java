package service;

import contract.CommonContract;
import contract.IAnimalLocHandler;
import entity.AnimalLocMsg;
import org.apache.mina.core.session.IoSession;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CommonService implements CommonContract.ICommonService {

    private IAnimalLocHandler mAnimalLocHandler;

    public CommonService(IAnimalLocHandler animalLocHandler) {
        mAnimalLocHandler = animalLocHandler;
    }

    @Override
    public void handleMsg(AnimalLocMsg animalLocMsg, IoSession session) {
        switch (animalLocMsg.getProtocolNo()) {
            case 0x15:
                reset(animalLocMsg, session);
                break;
            case 0x30:
                updateTime(animalLocMsg, session);
                break;
            default:
        }
    }

    // 恢复出厂设置
    private void reset(AnimalLocMsg animalLocMsg, IoSession session) {
        mAnimalLocHandler.sentMsg(session, animalLocMsg);
    }

    // 更新时间
    private void updateTime(AnimalLocMsg animalLocMsg, IoSession session) {
        // 获取GMT时间
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),Locale.US);
        byte[] content = new byte[6];
        content[0] = (byte)(calendar.get(Calendar.YEAR) - 2000);
        content[1] = (byte)(calendar.get(Calendar.MONTH) + 1);
        content[2] = (byte)calendar.get(Calendar.DATE);
        content[3] = (byte)calendar.get(Calendar.HOUR_OF_DAY);
        content[4] = (byte)calendar.get(Calendar.MINUTE);
        content[5] = (byte)calendar.get(Calendar.SECOND);

        AnimalLocMsg msg = new AnimalLocMsg();
        msg.setStartBit((short)0x7878);
        msg.setPackageLen((byte)0x07);
        msg.setContent(content);
        msg.setProtocolNo((byte)0x30);
        msg.setEndBit((short)0x0d0a);

        mAnimalLocHandler.sentMsg(session, msg);
    }

}
