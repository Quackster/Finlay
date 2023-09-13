package org.alexdev.finlay.messages.outgoing.handshake;

import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

import java.util.List;

public class RIGHTS extends MessageComposer {
    private final List<Fuseright> avaliableFuserights;

    public RIGHTS(List<Fuseright> avaliableFuserights) {
        this.avaliableFuserights = avaliableFuserights;
    }

    @Override
    public void compose(NettyResponse response) {
        for (Fuseright fuseright : this.avaliableFuserights) {
            response.writeString(fuseright.getFuseright());
        }
    }

    @Override
    public short getHeader() {
        return 2; // "@B"
    }
}
