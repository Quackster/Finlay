package org.alexdev.finlay.messages.outgoing.recycler;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class START_RECYCLING_RESULT extends MessageComposer {
    private final boolean canRecycle;

    public START_RECYCLING_RESULT(boolean canRecycle) {
        this.canRecycle = canRecycle;
    }

    @Override
    public void compose(NettyResponse response) {
        if (this.canRecycle) {
            response.writeBool(true);
        }
    }

    @Override
    public short getHeader() {
        return 306;
    }
}
