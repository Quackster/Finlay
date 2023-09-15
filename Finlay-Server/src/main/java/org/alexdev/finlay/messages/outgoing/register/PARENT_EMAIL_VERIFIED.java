package org.alexdev.finlay.messages.outgoing.register;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class PARENT_EMAIL_VERIFIED extends MessageComposer {
    private final int flag;

    public PARENT_EMAIL_VERIFIED(int flag) {
        this.flag = flag;

    }

    @Override
    public void compose(NettyResponse response) {
        response.write(this.flag);
    }

    @Override
    public short getHeader() {
        return 217;
    }
}
