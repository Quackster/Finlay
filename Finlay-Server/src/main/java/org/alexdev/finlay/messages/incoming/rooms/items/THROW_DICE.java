package org.alexdev.finlay.messages.incoming.rooms.items;

import org.alexdev.finlay.game.GameScheduler;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.entities.RoomEntity;
import org.alexdev.finlay.game.room.tasks.DiceTask;
import org.alexdev.finlay.messages.outgoing.rooms.items.DICE_VALUE;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;


public class THROW_DICE implements MessageEvent {
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

        // Return if dice is already being rolled
        if (item.getRequiresUpdate()) {
            return;
        }

        // Check if user is next to dice
        if (!roomEntity.getTile().getPosition().touches(item.getTile().getPosition())) {
            return;
        }

        // We reset the room timer here too as in casinos you might be in the same place for a while
        // And you don't want to get kicked while you're still actively rolling dices for people :)
        player.getRoomUser().getTimerManager().resetRoomTimer();

        room.send(new DICE_VALUE(itemId, true, 0));

        // Send spinning animation to room
        item.setCustomData("-1");
        item.updateStatus();

        item.setRequiresUpdate(true);

        GameScheduler.getInstance().getService().schedule(new DiceTask(item), 2, TimeUnit.SECONDS);
    }
}