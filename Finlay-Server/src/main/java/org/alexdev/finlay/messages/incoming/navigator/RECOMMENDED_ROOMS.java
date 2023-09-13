package org.alexdev.finlay.messages.incoming.navigator;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.outgoing.navigator.RECOMMENDED_ROOM_LIST;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class RECOMMENDED_ROOMS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int roomLimit = 3;

        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(RoomDao.getRecommendedRooms(roomLimit, false));

        RoomManager.getInstance().sortRooms(roomList);
        RoomManager.getInstance().ratingSantiyCheck(roomList);

        if (roomList.size() < roomLimit) {
            //int difference = roomLimit - roomList.size();

            for (Room room : RoomManager.getInstance().replaceQueryRooms(RoomDao.getHighestRatedRooms(roomLimit, false))) {
                if (roomList.size() == roomLimit) {
                    break;
                }

                roomList.add(room);
            }
        }

        player.send(new RECOMMENDED_ROOM_LIST(player, roomList));
    }
}
