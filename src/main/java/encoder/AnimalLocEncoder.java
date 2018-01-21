package encoder;

import entity.AnimalLocMsg;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class AnimalLocEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession session, Object message,
                       ProtocolEncoderOutput out) throws Exception {

        AnimalLocMsg animalLocMsg = (AnimalLocMsg) message;
        IoBuffer buffer = IoBuffer.allocate(100, false).setAutoExpand(true);

        buffer.putShort(animalLocMsg.getStartBit());
        buffer.put(animalLocMsg.getPackageLen());
        buffer.put(animalLocMsg.getProtocolNo());
        if (null != animalLocMsg.getContent()) {
            buffer.put(animalLocMsg.getContent());
        }
        buffer.putShort(animalLocMsg.getEndBit());

        buffer.flip();
        out.write(buffer);

    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }

}
