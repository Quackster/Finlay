package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class QUIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        // Remove authentication values when user manually leaves
        player.getRoomUser().setAuthenticateTelporterId(-1);
        player.getRoomUser().setAuthenticateId(-1);

        player.getRoomUser().getRoom().getEntityManager().leaveRoom(player, false);
    }
}
