package org.alexdev.finlay.messages.outgoing.handshake;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class AVAILABLE_SETS extends MessageComposer {
    private final String set;

    public AVAILABLE_SETS(String set) {
        this.set = set;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.set);
    }

    @Override
    public short getHeader() {
        return 8; // "@H"
    }
}
