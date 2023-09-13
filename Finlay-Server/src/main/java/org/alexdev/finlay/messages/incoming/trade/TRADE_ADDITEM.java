package org.alexdev.finlay.messages.incoming.trade;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.managers.RoomTradeManager;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class TRADE_ADDITEM implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (player.getRoomUser().getTradePartner() == null) {
            return;
        }

        int itemId = Integer.parseInt(reader.contents());
        Item inventoryItem = player.getInventory().getItem(itemId);

        if (inventoryItem == null) {
            return;
        }

        player.getRoomUser().getTradeItems().add(inventoryItem);

        RoomTradeManager.refreshWindow(player);
        RoomTradeManager.refreshWindow(player.getRoomUser().getTradePartner());
    }
}
