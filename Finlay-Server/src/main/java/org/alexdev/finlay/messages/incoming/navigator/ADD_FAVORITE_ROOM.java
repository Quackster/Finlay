package org.alexdev.finlay.messages.incoming.navigator;

import org.alexdev.finlay.dao.mysql.RoomFavouritesDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class ADD_FAVORITE_ROOM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        int roomType = reader.readInt();
        int roomId = reader.readInt();

        if (roomType == 1) {
            roomId = (roomId - RoomManager.PUBLIC_ROOM_OFFSET);
        }

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return; // Room was null, ignore request
        }

        List<Room> favouritesList = RoomManager.getInstance().getFavouriteRooms(player.getDetails().getId());

        for (Room favroom : favouritesList) {
            if (favroom.getId() == roomId) {
                return; // Room already added, ignore request
            }
        }

        RoomFavouritesDao.addFavouriteRoom(player.getDetails().getId(), roomId);
    }
}
