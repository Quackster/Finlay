package org.alexdev.finlay.messages.incoming.handshake;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.handshake.SESSION_PARAMETERS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GET_SESSION_PARAMETERS implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new SESSION_PARAMETERS(player.getDetails()));
    }
}
