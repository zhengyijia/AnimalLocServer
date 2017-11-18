package com.animal.loc.server;

import com.animal.loc.common.AnimalLocMsgProtocolCodecFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class AnimalLocServer {

    public static final int PORT= 8765;

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
        acceptor.bind(new InetSocketAddress(PORT));

    }

}
