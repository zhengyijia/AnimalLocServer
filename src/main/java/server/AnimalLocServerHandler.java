package server;

import contract.CommonContract;
import contract.IAnimalLocHandler;
import contract.LocationContract;
import contract.LoginContract;
import entity.AnimalLocMsg;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import service.CommonService;
import service.LocationService;
import service.LoginService;
import util.Log4jUtil;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;

public class AnimalLocServerHandler extends IoHandlerAdapter implements IAnimalLocHandler {

    private CommonContract.ICommonService mCommonService;
    private LoginContract.ILoginService mLoginService;
    private LocationContract.ILocationService mLocationService;

    private Map<Long, String> IMEIs = new HashMap<>();

    public AnimalLocServerHandler() {
        initService();
    }

    private void initService() {
        mCommonService = new CommonService(this);
        mLoginService = new LoginService(this);
        mLocationService = new LocationService(this);
    }

    @Override
    public void sessionCreated(IoSession session){
        // 显示客户端的ip和端口
        Log4jUtil.instance.info("服务端与客户端创建连接...");
        Log4jUtil.instance.info("客户端ip及端口：" + session.getRemoteAddress().toString());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log4jUtil.instance.info("服务端与客户端连接打开...");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        Log4jUtil.instance.info("服务端收到来自" + getIMEI(session.getId()) + "客户端的数据...");

        AnimalLocMsg animalLocMsg =(AnimalLocMsg) message;
        Log4jUtil.instance.info("----------数据内容-----------");
        String decodeHex = DatatypeConverter.printHexBinary(animalLocMsg.getContent());
        Log4jUtil.instance.info(decodeHex);
        Log4jUtil.instance.info("-----------------------------");

        handleAnimalLocMsg(animalLocMsg, session);

    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Log4jUtil.instance.info("服务端向" + getIMEI(session.getId()) + "发送信息成功...");

        AnimalLocMsg animalLocMsg =(AnimalLocMsg) message;
        Log4jUtil.instance.info("----------数据内容-----------");
        String decodeHex = "";
        if (null != animalLocMsg.getContent()) {
            decodeHex = DatatypeConverter.printHexBinary(animalLocMsg.getContent());
        }
        Log4jUtil.instance.info(decodeHex);
        Log4jUtil.instance.info("-----------------------------");
    }

    private void handleAnimalLocMsg(AnimalLocMsg animalLocMsg, IoSession session) {
        // 若非登录消息，则需要检查设备是否登录成功
        if (animalLocMsg.getProtocolNo() != 0x01) {
            String IMEI = getIMEI(session.getId());

            if (!checkIMEI(IMEI))
                return;
        }

        switch (animalLocMsg.getProtocolNo()) {
            case 0x01:
                // 登录消息
                mLoginService.handleMsg(animalLocMsg, session);
                break;
            case 0x10:
                // 定位数据包
                mLocationService.handleMsg(animalLocMsg, session);
                break;
            case 0x15:// 恢复出厂设置
            case 0x30:// 更新时间
                mCommonService.handleMsg(animalLocMsg, session);
                break;
            default:
        }
    }

    // 检查设备是否已登录成功
    private boolean checkIMEI(String IMEI) {
        if (null == IMEI) {
            Log4jUtil.instance.info("该设备未登录");
            return false;
        }

        return true;
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Log4jUtil.instance.info("服务端与客户端的连接关闭...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//        Log4jUtil.instance.debug("服务端进入空闲状态...");
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

    // 发送数据
    @Override
    public void sentMsg(IoSession session, AnimalLocMsg animalLocMsg) {
        session.write(animalLocMsg);
    }

}
