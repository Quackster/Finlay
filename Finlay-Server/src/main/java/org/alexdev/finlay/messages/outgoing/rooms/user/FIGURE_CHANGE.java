package org.alexdev.finlay.messages.outgoing.rooms.user;

import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class FIGURE_CHANGE extends MessageComposer {
    private final int instanceId;
    private final PlayerDetails details;

    public FIGURE_CHANGE(int instanceId, PlayerDetails details) {
        this.instanceId = instanceId;
        this.details = details;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.instanceId);
        response.writeString(this.details.getFigure());
        response.writeString(this.details.getSex());
        response.writeString(this.details.getMotto());
    }

    @Override
    public short getHeader() {
        return 266; // "DJ"
    }
}
