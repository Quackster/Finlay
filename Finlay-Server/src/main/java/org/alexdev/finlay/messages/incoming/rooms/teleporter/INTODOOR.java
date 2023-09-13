package org.alexdev.finlay.messages.incoming.rooms.teleporter;

import org.alexdev.finlay.dao.mysql.ItemDao;
import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.messages.outgoing.rooms.items.BROADCAST_TELEPORTER;
import org.alexdev.finlay.messages.outgoing.rooms.items.TELEPORTER_INIT;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

public class INTODOOR implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String contents = reader.contents();

        if (!StringUtils.isNumeric(contents)) {
            return;
        }

        Item item = room.getItemManager().getById(Integer.parseInt(contents));

        if (item == null || !item.hasBehaviour(ItemBehaviour.TELEPORTER)) {
            return;
        }

        if (player.getRoomUser().getAuthenticateTelporterId() != -1) {
            return;
        }


        Item linkedTeleporter = ItemDao.getItem(item.getTeleporterId());

        if (linkedTeleporter == null) {
            return;
        }

        if (!item.getPosition().getSquareInFront().equals(player.getRoomUser().getPosition())) {
            return;
        }

       // player.getRoomUser().setAuthenticateTelporterId(item.getId());
        player.getRoomUser().setAuthenticateTelporterId(item.getId());
        player.getRoomUser().walkTo(item.getPosition().getX(), item.getPosition().getY());
        //player.getRoomUser().setWalkingAllowed(false);
    }
}
