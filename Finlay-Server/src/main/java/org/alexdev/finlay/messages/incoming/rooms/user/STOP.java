package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.enums.StatusType;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class STOP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String stopWhat = reader.contents();

        if (stopWhat.equals("Dance")) {
            player.getRoomUser().removeStatus(StatusType.DANCE);
            player.getRoomUser().setNeedsUpdate(true);
        }

        if (stopWhat.equals("CarryItem")) {
            player.getRoomUser().removeStatus(StatusType.CARRY_ITEM);
            player.getRoomUser().setNeedsUpdate(true);
        }

        player.getRoomUser().getTimerManager().resetRoomTimer();
    }
}
