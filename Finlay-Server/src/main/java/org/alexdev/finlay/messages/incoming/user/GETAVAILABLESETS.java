package org.alexdev.finlay.messages.incoming.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.handshake.AVAILABLE_SETS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.config.GameConfiguration;

public class GETAVAILABLESETS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        // if (player.getVersion() <= 17) {
            if (player.getDetails().hasClubSubscription()) {
                player.send(new AVAILABLE_SETS("[" + GameConfiguration.getInstance().getString("users.figure.parts.club") + "]"));
            } else {
                player.send(new AVAILABLE_SETS("[" + GameConfiguration.getInstance().getString("users.figure.parts.default") + "]"));
            }
        // }
    }
}
