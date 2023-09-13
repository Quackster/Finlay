package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class WALK implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (!player.getRoomUser().isWalkingAllowed()) {
            return;
        }

        int X = reader.readBase64();
        int Y = reader.readBase64();

        player.getRoomUser().walkTo(X, Y);
    }
}
