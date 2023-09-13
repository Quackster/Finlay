package org.alexdev.finlay.messages.incoming.navigator;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.outgoing.navigator.NOFLATS;
import org.alexdev.finlay.messages.outgoing.navigator.FLAT_NORESULTS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.List;

public class SRCHF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String searchQuery = reader.contents();

        List<Room> roomList = RoomManager.getInstance().replaceQueryRooms(
                RoomDao.querySearchRooms(searchQuery));

        if (roomList.size() > 0) {
            RoomManager.getInstance().sortRooms(roomList);
            RoomManager.getInstance().ratingSantiyCheck(roomList);

            player.send(new FLAT_NORESULTS(roomList, player));
        } else {
            player.send(new NOFLATS());
        }
    }
}
