package testClient;

import common.AnimalLocMsgProtocolCodecFactory;
import entity.AnimalLocMsg;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import util.Log4jUtil;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinaClient {

    private static String HOST = "127.0.0.1";
    private static int PORT = 8889;

    public static void main(String[] args) {
        // 创建一个非阻塞的客户端程序
        IoConnector connector = new NioSocketConnector();
        // 设置链接超时时间
        connector.setConnectTimeout(30000);
        // 添加过滤器
//        connector.getFilterChain().addLast(
//                "codec",
//                new ProtocolCodecFilter(new TextLineCodecFactory(Charset
//                        .forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),
//                        LineDelimiter.WINDOWS.getValue())));
        // 设置编码过滤器
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter( new AnimalLocMsgProtocolCodecFactory()));
        // 添加业务逻辑处理器类
        connector.setHandler(new MinaClientHandler());
        IoSession session = null;
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        ConnectFuture future = connector.connect(new InetSocketAddress(
                HOST, PORT));// 创建连接
        future.awaitUninterruptibly();// 等待连接创建完成
        session = future.getSession();// 获得session
        while (true) {
            try {
                String s = scanner.readLine();

                // 去除空格
                Pattern pattern = Pattern.compile("\\s*");
                Matcher m = pattern.matcher(s);
                byte[] content = DatatypeConverter.parseHexBinary(m.replaceAll(""));

                // 构造消息实体
                AnimalLocMsg msg = new AnimalLocMsg();
                msg.setStartBit((short)0x7878);
                msg.setPackageLen(content[2]);
                msg.setProtocolNo(content[3]);
                msg.setContent(Arrays.copyOfRange(content, 4, content.length - 2));
                msg.setEndBit((short)0x0d0a);
                session.write(msg);// 发送消息
            } catch (Exception e) {
                Log4jUtil.instance.error("客户端链接异常: " + e.toString());
                break;
            }
        }
        session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
        connector.dispose();
    }

}
