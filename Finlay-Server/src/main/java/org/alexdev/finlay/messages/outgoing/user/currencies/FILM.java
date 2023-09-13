package org.alexdev.finlay.messages.outgoing.user.currencies;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class FILM extends MessageComposer {
    private final int film;

    public FILM(PlayerDetails details) {
        this.film = details.getFilm();
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.film);
    }

    @Override
    public short getHeader() {
        return 4; // "@D"
    }
}
