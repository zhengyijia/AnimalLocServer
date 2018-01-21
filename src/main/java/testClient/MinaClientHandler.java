package testClient;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import util.Log4jUtil;

public class MinaClientHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        String msg = message.toString();
        Log4jUtil.instance.info("客户端接收到的信息为：" + msg);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        Log4jUtil.instance.error("客户端发生异常: " + cause.toString());
    }

}
