package org.alexdev.finlay.messages.outgoing.welcomingparty;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class INVITATION_EXISTS extends MessageComposer {
    public INVITATION_EXISTS() {

    }

    @Override
    public void compose(NettyResponse response) {
        // Packet doesn't have any packet structure according to Lingo
    }

    @Override
    public short getHeader() {
        return 358; // "Ef"
    }
}
