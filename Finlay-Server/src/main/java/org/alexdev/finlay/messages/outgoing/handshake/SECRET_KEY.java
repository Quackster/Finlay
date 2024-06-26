package org.alexdev.finlay.messages.outgoing.handshake;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class SECRET_KEY extends MessageComposer {
    private final String key;

    public SECRET_KEY(String key) {
        this.key = key;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.key);
    }

    @Override
    public short getHeader() {
        return 1; // "@A"
    }
}
