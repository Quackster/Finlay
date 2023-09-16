package org.alexdev.finlay.messages.outgoing.user;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class USER_OBJECT extends MessageComposer {
    private final PlayerDetails details;

    public USER_OBJECT(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeValue("name", this.details.getName());
        response.writeValue("figure", this.details.getFigure());
        response.writeValue("sex", Character.toLowerCase(this.details.getSex()));
        response.writeValue("customData", this.details.getMotto());
        response.writeValue("ph_tickets", this.details.getTickets());
        response.writeValue("ph_figure=ch", this.details.getPoolFigure());
        response.writeValue("photo_film", this.details.getFilm());
    }

    @Override
    public short getHeader() {
        return 5; // "@E"
    }
}
