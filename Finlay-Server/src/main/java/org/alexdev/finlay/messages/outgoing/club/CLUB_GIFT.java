package org.alexdev.finlay.messages.outgoing.club;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class CLUB_GIFT extends MessageComposer {
    private final int giftCount;

    public CLUB_GIFT(int giftCount) {
        this.giftCount = giftCount;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.giftCount);
    }

    @Override
    public short getHeader() {
        return 280;
    }
}
