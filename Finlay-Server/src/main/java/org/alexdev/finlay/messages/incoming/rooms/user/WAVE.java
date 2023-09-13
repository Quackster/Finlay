package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class WAVE implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.getRoomUser().wave();
        player.getRoomUser().getTimerManager().resetRoomTimer();
    }

}
