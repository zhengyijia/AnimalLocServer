package service;

import contract.IAnimalLocHandler;
import contract.LoginContract;
import entity.AnimalLocMsg;
import entity.ContentMsgBean.LoginMsgBean;
import model.LoginModel;

public class LoginService implements LoginContract.ILoginService {

    private IAnimalLocHandler mAnimalLocHandler;
    private LoginContract.ILoginModel mLoginModel;

    public LoginService(IAnimalLocHandler animalLocHandler) {
        mAnimalLocHandler = animalLocHandler;
        mLoginModel = new LoginModel();
    }

    // 处理接收到的消息
    @Override
    public void handleMsg(AnimalLocMsg animalLocMsg, Long sessionId) {
        LoginMsgBean loginMsgBean = new LoginMsgBean(animalLocMsg);

        if(!checkIMEI(loginMsgBean)) {
            return;
        }

        mAnimalLocHandler.addIMEI(loginMsgBean.getIMEI(), sessionId);
    }

    private boolean checkIMEI(LoginMsgBean loginMsgBean) {
        String IMEI = loginMsgBean.getIMEI();

        // TODO: 2017/11/19 添加检查IMEI操作

        return true;
    }

}