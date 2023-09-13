package org.alexdev.finlay.messages.outgoing.rooms.moderation;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class YOUNOTCONTROLLER extends MessageComposer {
    @Override
    public void compose(NettyResponse response) {

    }

    @Override
    public short getHeader() {
        return 43; // "@k"
    }
}
