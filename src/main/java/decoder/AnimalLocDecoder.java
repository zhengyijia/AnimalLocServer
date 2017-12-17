package decoder;

import entity.AnimalLocMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import util.Log4jUtil;

import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.List;

public class AnimalLocDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {

        while (buffer.remaining() >= 6) {
            //记录解码数据起始位置
            buffer.mark();

            // just for debug
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            Log4jUtil.instance.info("接收到的完整内容："
                    + DatatypeConverter.printHexBinary(data));
            buffer.reset();

            short startBit = 0;
            do {
                startBit = buffer.getShort();
            } while (startBit != 0x7878 && buffer.remaining() >= 6);

            if (startBit != 0x7878) {
                return true;
            }

            // 读取包格式及大小
//            short startBit = buffer.getShort();
            byte packageLen = buffer.get();
            byte protocolNo = buffer.get();

            byte temp1;
            byte temp2;
            List<Byte> contentList = new ArrayList<>();

            temp1 = buffer.get();
            temp2 = buffer.get();
            // 循环读到0x0d0a为止
            while ((temp1 != 0x0d || temp2 != 0x0a) && buffer.hasRemaining()) {
                contentList.add(temp1);
                temp1 = temp2;
                temp2 = buffer.get();
            }

            if (temp1 != 0x0d || temp2 != 0x0a) {
                if (contentList.size() > 100) {
                    Log4jUtil.instance.info("接收数据的结束位格式不符！");
                    return true;
                } else {
                    buffer.reset();
                    return false;
                }
            }

            byte[] content = new byte[contentList.size()];
            for (int i = 0; i < contentList.size(); i++) {
                content[i] = contentList.get(i);
            }

            AnimalLocMsg animalLocMsg = new AnimalLocMsg();
            animalLocMsg.setStartBit(startBit);
            animalLocMsg.setPackageLen(packageLen);
            animalLocMsg.setProtocolNo(protocolNo);
            animalLocMsg.setContent(content);
            animalLocMsg.setEndBit((short)0x0d0a);

            out.write(animalLocMsg);
            return true;


//            // 检查读取的包头是否正常，不正常的话清空buffer
//            if (startBit != 0x7878) {
//                buffer.clear();
//                Log4jUtil.instance.info("接收数据的起始位格式不符！");
//                break;
//            }

//            if (packageLen < 1) {
//                buffer.clear();
//                Log4jUtil.instance.info("接收数据的长度标识有误！");
//                break;
//            }

//            if (buffer.remaining() >= packageLen + 2) {
//                AnimalLocMsg animalLocMsg = new AnimalLocMsg();
//
//                animalLocMsg.setStartBit(startBit);
//                animalLocMsg.setPackageLen(packageLen);
//                animalLocMsg.setProtocolNo(buffer.get());
//
//                byte[] content = new byte[animalLocMsg.getPackageLen() - 1];
//                buffer.get(content);
//                animalLocMsg.setContent(content);
//
//                short endBit = buffer.getShort();
//                if (endBit != 0x0d0a) {
//                    buffer.clear();
//                    Log4jUtil.instance.info("接收数据的结束位格式不符！");
//                    break;
//                }
//                animalLocMsg.setEndBit(endBit);
//
//                out.write(animalLocMsg);
//                return true;
//            } else {
//                // 如果消息包不完整,将指针重新移动消息头的起始位置
//                buffer.reset();
//                return false;
//            }

        }

        return false;
    }

}
