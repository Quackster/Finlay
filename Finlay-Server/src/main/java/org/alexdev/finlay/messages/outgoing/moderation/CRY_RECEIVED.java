package org.alexdev.finlay.messages.outgoing.user;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class CRY_RECEIVED extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {
        response.writeString("H");
    }

    @Override
    public short getHeader() {
        return 321;
    }
}
