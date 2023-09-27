package org.alexdev.finlay.messages.incoming.handshake;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class VERSIONCHECK implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {

    }
}
