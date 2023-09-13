package org.alexdev.finlay.messages.outgoing.rooms.infobus;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class VOTE_QUESTION extends MessageComposer {
    private final String message;

    public VOTE_QUESTION(String message) {
        this.message = message;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.message);
    }

    @Override
    public short getHeader() {
        return 79; // "AO"
    }
}

