package com.animal.loc.contract;

import com.animal.loc.entity.AnimalLocMsg;

public class LocationContract {

    public interface ILocationService {
        // 处理接收到的消息
        void handleMsg(AnimalLocMsg animalLocMsg, Long sessionId);
    }

    public interface ILocationModel {

    }

}
