package org.alexdev.finlay.messages.incoming.rooms.settings;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.navigator.NavigatorCategory;
import org.alexdev.finlay.game.navigator.NavigatorManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SETFLATCAT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int roomId = reader.readInt();
        int categoryId = reader.readInt();

        NavigatorCategory category = NavigatorManager.getInstance().getCategoryById(categoryId);

        if (category == null) {
            return;
        }

        if (category.getMinimumRoleSetFlat().getRankId() > player.getDetails().getRank().getRankId()) {
            return;
        }

        if (category.isNode() || category.isPublicSpaces()) {
            category = NavigatorManager.getInstance().getCategoryById(2); // No category
        }

        Room room = RoomManager.getInstance().getRoomById(roomId);

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId())) {
            return;
        }

        room.getData().setCategoryId(category.getId());
        RoomDao.save(room);
    }
}
