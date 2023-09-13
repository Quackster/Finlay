package org.alexdev.finlay.messages.outgoing.register;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class DATE extends MessageComposer {
    private final String shortDate;

    public DATE(String shortDate) {
        this.shortDate = shortDate;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.shortDate);
    }

    @Override
    public short getHeader() {
        return 163; // "Bc"
    }
}
