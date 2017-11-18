package com.animal.loc.decoder;

import com.animal.loc.entity.AnimalLocMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class AnimalLocDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out) throws Exception {

        while (buffer.remaining() > 3) {
            //记录解码数据起始位置
            buffer.mark();

            // 读取包格式及大小
            short startBit = buffer.getShort();
            byte packageLen = buffer.get();

            // 检查读取的包头是否正常，不正常的话清空buffer
            if (startBit != 0x7878) {
                buffer.clear();
                break;
            }

            if (packageLen < 1) {
                buffer.clear();
                break;
            }

            if (buffer.remaining() >= packageLen + 2) {
                AnimalLocMsg animalLocMsg = new AnimalLocMsg();

                animalLocMsg.setStartBit(startBit);
                animalLocMsg.setPackageLen(packageLen);
                animalLocMsg.setProtocolNo(buffer.get());

                byte[] content = new byte[animalLocMsg.getPackageLen() - 1];
                buffer.get(content);
                animalLocMsg.setContent(content);

                short endBit = buffer.getShort();
                if (endBit != 0x0d0a) {
                    buffer.clear();
                    break;
                }
                animalLocMsg.setEndBit(endBit);

                out.write(animalLocMsg);
                return true;
            } else {
                // 如果消息包不完整,将指针重新移动消息头的起始位置
                buffer.reset();
                return false;
            }

        }

        return false;
    }

}
