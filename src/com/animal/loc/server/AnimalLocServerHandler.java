package com.animal.loc.server;

import com.animal.loc.contract.IAnimalLocHandler;
import com.animal.loc.contract.LocationContract;
import com.animal.loc.contract.LoginContract;
import com.animal.loc.entity.AnimalLocMsg;
import com.animal.loc.service.LocationService;
import com.animal.loc.service.LoginService;
import com.animal.loc.util.Log4jUtil;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class AnimalLocServerHandler extends IoHandlerAdapter implements IAnimalLocHandler{

    private LoginContract.ILoginService mLoginService;
    private LocationContract.ILocationService mLocationService;

    private Map<Long, String> IMEIs = new HashMap<>();

    public AnimalLocServerHandler() {
        initService();
    }

    private void initService() {
        mLoginService = new LoginService(this);
        mLocationService = new LocationService(this);
    }

    @Override
    public void sessionCreated(IoSession session){
        // 显示客户端的ip和端口
        Log4jUtil.instance.debug("服务端与客户端创建连接...");
        Log4jUtil.instance.debug("客户端ip及端口：" + session.getRemoteAddress().toString());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log4jUtil.instance.debug("服务端与客户端连接打开...");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        Log4jUtil.instance.debug("服务端收到来自" + getIMEI(session.getId()) + "客户端的数据...");

        AnimalLocMsg animalLocMsg =(AnimalLocMsg) message;
        Log4jUtil.instance.debug("----------数据内容-----------");
        String decodeHex = DatatypeConverter.printHexBinary(animalLocMsg.getContent());
        Log4jUtil.instance.debug(decodeHex);
        Log4jUtil.instance.debug("-----------------------------");

    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Log4jUtil.instance.debug("服务端向" + getIMEI(session.getId()) + "发送信息成功...");

        AnimalLocMsg animalLocMsg =(AnimalLocMsg) message;
        Log4jUtil.instance.debug("----------数据内容-----------");
        String decodeHex = DatatypeConverter.printHexBinary(animalLocMsg.getContent());
        Log4jUtil.instance.debug(decodeHex);
        Log4jUtil.instance.debug("-----------------------------");

        handleAnimalLocMsg(animalLocMsg, session.getId());
    }

    private void handleAnimalLocMsg(AnimalLocMsg animalLocMsg, Long sessionId) {
        switch (animalLocMsg.getProtocolNo()) {
            case 0x01:
                // 登录消息

                break;
            case 0x10:
                // 定位数据包
                break;
            default:
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Log4jUtil.instance.debug("服务端与客户端的连接关闭...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        Log4jUtil.instance.debug("服务端进入空闲状态...");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Log4jUtil.instance.error("服务端发送异常：" + cause.getMessage());
    }

    // 记录长连接线程对应的IMEI号
    @Override
    public void addIMEI(String IMEI, Long sessionId) {
        IMEIs.put(sessionId, IMEI);
    }

    // 获取sessionId对应IMEI号
    @Override
    public String getIMEI(Long sessionId) {
        if (!IMEIs.containsKey(sessionId)) {
            return null;
        }

        return IMEIs.get(sessionId);
    }

}
