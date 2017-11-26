package contract;

import entity.AnimalLocMsg;
import entity.TableBean.AnimalLocLocInfoEntity;

public class LocationContract {

    public interface ILocationService {
        // 处理接收到的消息
        void handleMsg(AnimalLocMsg animalLocMsg, Long sessionId);
    }

    public interface ILocationModel {
        // 保存gps数据
        void saveLocInfo(AnimalLocLocInfoEntity locInfo);
    }

}
