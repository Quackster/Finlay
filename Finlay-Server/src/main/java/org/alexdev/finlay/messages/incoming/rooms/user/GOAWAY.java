package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.handlers.walkways.WalkwaysManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GOAWAY implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        if (!player.getRoomUser().isWalkingAllowed()) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.isPublicRoom()) {
            Position doorPosition = room.getModel().getDoorLocation();

            if (WalkwaysManager.getInstance().getWalkway(room, doorPosition) != null) {
                return;
            }
        }

        player.getRoomUser().kick(true);
    }
}