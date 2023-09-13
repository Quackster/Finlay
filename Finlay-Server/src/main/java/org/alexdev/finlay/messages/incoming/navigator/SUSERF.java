package org.alexdev.finlay.messages.incoming.navigator;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.outgoing.navigator.NOFLATSFORUSER;
import org.alexdev.finlay.messages.outgoing.navigator.FLAT_RESULTS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class SUSERF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getRoomsByUserId(player.getDetails().getId()));

        if (roomList.size() > 0) {
            RoomManager.getInstance().sortRooms(roomList);
            RoomManager.getInstance().ratingSantiyCheck(roomList);

            player.send(new FLAT_RESULTS(roomList));
        } else {
            player.send(new NOFLATSFORUSER(player.getDetails().getName()));
        }
    }
}
