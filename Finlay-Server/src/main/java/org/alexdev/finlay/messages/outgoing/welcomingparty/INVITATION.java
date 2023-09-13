package org.alexdev.finlay.messages.outgoing.welcomingparty;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class INVITATION extends MessageComposer {
    private final PlayerDetails inviter;

    public INVITATION(PlayerDetails inviter) {
        this.inviter = inviter;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.inviter.getId());
        response.writeString(this.inviter.getName());
    }

    @Override
    public short getHeader() {
        return 355; // "Ec"
    }
}
