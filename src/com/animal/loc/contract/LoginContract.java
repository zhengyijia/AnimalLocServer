package com.animal.loc.contract;

import com.animal.loc.entity.AnimalLocMsg;

public class LoginContract {

    public interface ILoginService {
        // 处理接收到的消息
        void handleMsg(AnimalLocMsg animalLocMsg, Long sessionId);
    }

    public interface ILoginModel {

    }

}
