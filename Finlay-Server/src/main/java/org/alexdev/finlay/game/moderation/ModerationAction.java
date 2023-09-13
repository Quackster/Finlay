package org.alexdev.finlay.game.moderation;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public interface ModerationAction {
    void performAction(Player player, Room room, String alertMessage, String notes, NettyRequest reader);
}

