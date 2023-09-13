package org.alexdev.finlay.messages.incoming.rooms.moderation;

import org.alexdev.finlay.dao.mysql.RoomRightsDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class REMOVEALLRIGHTS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int roomId = reader.readInt();

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        room.getRights().clear();

        for (Player roomPlayer : room.getEntityManager().getPlayers()) {
            room.refreshRights(roomPlayer);
        }

        RoomRightsDao.deleteRoomRights(room.getData());
    }
}
