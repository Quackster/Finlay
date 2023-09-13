package org.alexdev.finlay.messages.incoming.rooms.items;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.messages.outgoing.rooms.items.DICE_VALUE;
import org.alexdev.finlay.messages.outgoing.rooms.items.STUFFDATAUPDATE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;


public class DICE_OFF implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        RoomEntity roomEntity = player.getRoomUser();
        Room room = roomEntity.getRoom();

        if (room == null) {
            return;
        }

        String contents = reader.contents();

        if (!StringUtils.isNumeric(contents)) {
            return;
        }

        int itemId = Integer.parseInt(contents);

        if (itemId < 0) {
            return;
        }

        Item item = room.getItemManager().getById(itemId);

        if (item == null || !item.hasBehaviour(ItemBehaviour.DICE)) {
            return;
        }

        if (!roomEntity.getTile().getPosition().touches(item.getTile().getPosition())) {
            return;
        }

        room.send(new DICE_VALUE(itemId, false, 0));
        room.send(new STUFFDATAUPDATE(item));

        item.setCustomData("0");
        item.save();
    }
}