package org.alexdev.finlay.messages.outgoing.welcomingparty;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class INVITATION_FOLLOW_FAILED extends MessageComposer {
    public INVITATION_FOLLOW_FAILED() {

    }

    @Override
    public void compose(NettyResponse response) {
        // Packet doesn't have any packet structure according to Lingo
    }

    @Override
    public short getHeader() {
        return 359; // "Eg"
    }
}
