package org.alexdev.finlay.messages.outgoing.messenger;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class CONSOLE_MOTTO extends MessageComposer {
    private final String consoleMotto;

    public CONSOLE_MOTTO(String consoleMotto) {
        this.consoleMotto = consoleMotto;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.consoleMotto);
    }

    @Override
    public short getHeader() {
        return 147; // "BS"
    }
}
