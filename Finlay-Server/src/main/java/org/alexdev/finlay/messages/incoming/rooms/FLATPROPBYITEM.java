package org.alexdev.finlay.messages.incoming.rooms;

import org.alexdev.finlay.dao.mysql.RoomDao;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.FLATPROPERTY;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.sql.SQLException;

public class FLATPROPBYITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws SQLException {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.isOwner(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        String contents = reader.contents();
        String property = contents.split("/")[0];

        int itemId = Integer.parseInt(contents.split("/")[1]);

        Item item = player.getInventory().getItem(itemId);

        if (item == null) {
            return;
        }

        int value = Integer.parseInt(item.getCustomData());

        if (property.equals("wallpaper")) {
            room.getData().setWallpaper(value);
        }

        if (property.equals("floor")) {
            room.getData().setFloor(value);
        }

        item.delete();
        RoomDao.saveDecorations(room);

        room.send(new FLATPROPERTY(property, value));
        player.getInventory().getItems().remove(item);
    }
}
