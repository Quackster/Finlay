package org.alexdev.finlay.messages.outgoing.register;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class AGE_CHECK_RESULT extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return 164;
    }
}
