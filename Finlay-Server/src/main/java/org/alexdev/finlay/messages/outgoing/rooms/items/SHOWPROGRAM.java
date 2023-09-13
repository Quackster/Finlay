package org.alexdev.finlay.messages.outgoing.rooms.items;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;
import org.alexdev.finlay.util.StringUtil;

public class SHOWPROGRAM extends MessageComposer {
    private final String[] arguments;
    public SHOWPROGRAM(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public void compose(NettyResponse response) {
        response.write(String.join(" ", this.arguments));
    }

    @Override
    public short getHeader() {
        return 71; // "AG"
    }
}
