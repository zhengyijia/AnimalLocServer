package contract;

import entity.AnimalLocMsg;
import org.apache.mina.core.session.IoSession;

public class CommonContract {

    public interface ICommonService {
        // 处理通用消息
        void handleMsg(AnimalLocMsg animalLocMsg, IoSession session);
    }

}
