package contract;

import entity.AnimalLocMsg;
import entity.TableBean.AnimalLocLocInfoEntity;
import org.apache.mina.core.session.IoSession;

public class LocationContract {

    public interface ILocationService {
        // 处理接收到的消息
        void handleMsg(AnimalLocMsg animalLocMsg, IoSession session);
    }

    public interface ILocationModel {
        // 保存gps数据
        void saveLocInfo(AnimalLocLocInfoEntity locInfo);
    }

}
