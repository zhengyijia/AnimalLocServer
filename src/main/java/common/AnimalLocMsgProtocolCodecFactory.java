package common;

import decoder.AnimalLocDecoder;
import encoder.AnimalLocEncoder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class AnimalLocMsgProtocolCodecFactory implements ProtocolCodecFactory {

    private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;

    public AnimalLocMsgProtocolCodecFactory()
    {
        encoder = new AnimalLocEncoder();
        decoder = new AnimalLocDecoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
        return encoder;
    }

}
