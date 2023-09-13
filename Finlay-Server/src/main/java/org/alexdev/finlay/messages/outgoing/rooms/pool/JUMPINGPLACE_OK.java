package org.alexdev.finlay.messages.outgoing.rooms.pool;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class JUMPINGPLACE_OK extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 125; // "A}"
    }
}
