package org.alexdev.finlay.messages.outgoing.games;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class GAMESTART extends MessageComposer {
    private final int gameLengthSeconds;

    public GAMESTART(int gameLengthSeconds) {
        this.gameLengthSeconds = gameLengthSeconds;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.gameLengthSeconds);
    }

    @Override
    public short getHeader() {
        return 247; // "Cw"
    }
}
