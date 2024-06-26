package org.alexdev.finlay.messages.outgoing.moderation;

import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class DELETE_CRY extends MessageComposer {
    private int cryId;

    public DELETE_CRY(int cryId){
        this.cryId = cryId;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(cryId);
    }

    @Override
    public short getHeader() {
        return 273; // "DQ"
    }
}
