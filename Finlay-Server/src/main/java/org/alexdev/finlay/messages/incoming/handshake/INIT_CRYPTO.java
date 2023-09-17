package org.alexdev.finlay.messages.incoming.handshake;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.handshake.CRYPTO_PARAMETERS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class INIT_CRYPTO implements MessageEvent {

    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.isLoggedIn()) {
            return;
        }

        // player.send(new CRYPTO_PARAMETERS());
    }
}
