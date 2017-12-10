package server;

import common.AnimalLocMsgProtocolCodecFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import util.Log4jUtil;
import util.PropertiesUtil;

import java.io.IOException;
import java.net.InetSocketAddress;

public class AnimalLocServer {

    public static void main(String[] args) throws IOException {

        // 创建服务端监控线程
        IoAcceptor acceptor = new NioSocketAcceptor();

        // 设置日志记录器
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        // 设置编码过滤器
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter( new AnimalLocMsgProtocolCodecFactory()));
        // 指定业务逻辑处理器
        acceptor.setHandler(new AnimalLocServerHandler());

        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        // 设置端口号
        String portString = PropertiesUtil.getProperty("mina.properties").getProperty("mina.port");
        acceptor.bind(new InetSocketAddress(Integer.parseInt(portString)));
        Log4jUtil.instance.info("服务端启动...端口：" + portString);

    }

}
