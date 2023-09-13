package org.alexdev.finlay.messages.outgoing.navigator;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class NOFLATSFORUSER extends MessageComposer {
    private String username;

    public NOFLATSFORUSER(String username) {
        this.username = username;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.username);
    }

    @Override
    public short getHeader() {
        return 57; // "@y"
    }
}
