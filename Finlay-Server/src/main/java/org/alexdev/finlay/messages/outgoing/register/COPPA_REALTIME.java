package org.alexdev.finlay.messages.outgoing.register;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class COPPA_REALTIME extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 215;
    }
}
