package org.alexdev.finlay.messages.outgoing.user;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class NO_USER_FOUND extends MessageComposer {
    private final String username;

    public NO_USER_FOUND(String username) {
        this.username = username;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.username);
    }

    @Override
    public short getHeader() {
        return 76; // "AL"
    }
}
