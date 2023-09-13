package org.alexdev.finlay.messages.incoming.rooms.settings;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.log.Log;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DELETEFLAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException {
        int roomId = Integer.parseInt(reader.contents());
        delete(roomId, player.getDetails().getId());
    }

    public static void delete(int roomId, int userId) throws SQLException {
        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(userId)) {
            return;
        }

        for (var item : room.getItems()) {
            item.delete();
        }

        List<Entity> entities = new ArrayList<>(room.getEntities());

        for (Entity entity : entities) {
            room.getEntityManager().leaveRoom(entity, true);
        }

        if (!room.tryDispose()) {
            Log.getErrorLogger().error("Room " + roomId + " did not want to get disposed by player id " + userId);
        }

        RoomDao.delete(room);
    }
}
