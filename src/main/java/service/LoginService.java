package service;

import contract.IAnimalLocHandler;
import contract.LoginContract;
import entity.AnimalLocMsg;
import entity.ContentMsgBean.LoginMsgBean;
import model.LoginModel;
import org.apache.mina.core.session.IoSession;

public class LoginService implements LoginContract.ILoginService {

    private IAnimalLocHandler mAnimalLocHandler;
    private LoginContract.ILoginModel mLoginModel;

    public LoginService(IAnimalLocHandler animalLocHandler) {
        mAnimalLocHandler = animalLocHandler;
        mLoginModel = new LoginModel();
    }

    // 处理接收到的消息
    @Override
    public void handleMsg(AnimalLocMsg animalLocMsg, IoSession session) {
        LoginMsgBean loginMsgBean = new LoginMsgBean(animalLocMsg);

        if(!checkIMEI(loginMsgBean)) {
            return;
        }

        mAnimalLocHandler.addIMEI(loginMsgBean.getIMEI(), session.getId());

        AnimalLocMsg msg = new AnimalLocMsg();
        msg.setStartBit((short) 0x7878);
        msg.setPackageLen((byte)0x01);
        msg.setContent(null);
        msg.setProtocolNo((byte)0x01);
        msg.setEndBit((short)0x0D0A);
        mAnimalLocHandler.sentMsg(session, msg);
    }

    private boolean checkIMEI(LoginMsgBean loginMsgBean) {
        String IMEI = loginMsgBean.getIMEI();

        return mLoginModel.verifyIMEI(IMEI);
    }

}
