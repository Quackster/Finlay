package org.alexdev.finlay.messages.outgoing.rooms;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class FLATPROPERTY extends MessageComposer {
    private final String property;
    private final int value;

    public FLATPROPERTY(String property, int value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.property);
        response.write("/");
        response.write(this.value);
    }

    @Override
    public short getHeader() {
        return 46; // "@n"
    }
}
