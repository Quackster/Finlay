package org.alexdev.finlay.messages.outgoing.rooms.user;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class LOGOUT extends MessageComposer {
    private final int instanceId;

    public LOGOUT(int instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.instanceId);
    }

    @Override
    public short getHeader() {
        return 29; // "@]
    }
}
