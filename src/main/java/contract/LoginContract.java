package contract;

import entity.AnimalLocMsg;
import org.apache.mina.core.session.IoSession;

public class LoginContract {

    public interface ILoginService {
        // 处理接收到的消息
        void handleMsg(AnimalLocMsg animalLocMsg, IoSession session);
    }

    public interface ILoginModel {
        // 验证是否存在对应IMEI
        boolean verifyIMEI(String IMEI);
    }

}
