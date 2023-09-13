package org.alexdev.finlay.messages.incoming.rooms.pool;

import org.alexdev.finlay.dao.mysql.PlayerDao;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.public_rooms.PoolHandler;
import org.alexdev.finlay.messages.outgoing.rooms.user.USER_OBJECTS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class SWIMSUIT implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.getData().getModel().equals("pool_a") &&
            !room.getData().getModel().equals("md_a")) {
            return;
        }

        String swimsuit = reader.contents();

        if (swimsuit == null || swimsuit.isBlank()) {
            swimsuit = "";
        }

        player.getDetails().setPoolFigure(swimsuit);
        PlayerDao.saveDetails(player.getDetails());

        room.send(new USER_OBJECTS(player));
        PoolHandler.exitBooth(player);
    }
}
