package org.alexdev.finlay.messages.incoming.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class PONG implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        // Nice pong :^)
        player.setPingOK(true);
    }
}
