package org.alexdev.finlay.messages.outgoing.user;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class LATENCY extends MessageComposer {
    private final int latency;

    public LATENCY(int latency) {
        this.latency = latency;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.latency);
    }

    @Override
    public short getHeader() {
        return 354; // "Eb"
    }
}
