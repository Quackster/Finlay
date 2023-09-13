package org.alexdev.finlay.messages.incoming.rooms.user;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.UPDATE_VOTES;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class RATEFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        Room room = player.getRoomUser().getRoom();

        if (room == null || room.isPublicRoom()) {
            return;
        }

        // Room owner is not allowed to vote on his own room
        if (room.getData().getOwnerId() == player.getDetails().getId()) {
            return;
        }

        int answer = reader.readInt();

        // It's either negative or positive
        if (answer != 1 && answer != -1) {
            return;
        }

        int userId = player.getDetails().getId();

        if (room.hasVoted(userId)) {
            return;
        }

        room.addVote(answer, userId);
    }
}
