package org.alexdev.finlay.messages.outgoing.user.currencies;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class CREDIT_BALANCE extends MessageComposer {
    private final PlayerDetails details;

    public CREDIT_BALANCE(PlayerDetails details) {
        this.details = details;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.details.getCredits() + ".0");
    }

    @Override
    public short getHeader() {
        return 6; // "@F
    }
}
