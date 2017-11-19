package com.animal.loc.server;

import com.animal.loc.entity.AnimalLocMsg;
import com.animal.loc.entity.ContentMsgBean.BaseMsgBean;
import com.animal.loc.entity.ContentMsgBean.LocMsgBean;
import com.animal.loc.entity.ContentMsgBean.LoginMsgBean;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class AnimalLocServerHandler extends IoHandlerAdapter {

    private Map<Long, String> IMEIs = new HashMap<>();

    @Override
    public void sessionCreated(IoSession session){
        // 显示客户端的ip和端口
        System.out.println(session.getRemoteAddress().toString());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        AnimalLocMsg animalLocMsg =(AnimalLocMsg) message;
        System.out.println("----------数据内容-----------");

        String decodeHex = DatatypeConverter.printHexBinary(animalLocMsg.getContent());
        System.out.println(decodeHex);

        System.out.println("-----------------------------");

    }

}
