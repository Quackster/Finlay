package org.alexdev.finlay.messages.incoming.rooms.teleporter;

import org.alexdev.finlay.dao.mysql.ItemDao;
import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomManager;
import org.alexdev.finlay.messages.outgoing.rooms.items.BROADCAST_TELEPORTER;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class DOORGOIN implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        int itemId = Integer.parseInt(reader.contents());

        if (player.getRoomUser().getAuthenticateTelporterId() == itemId) {
            Item item = player.getRoomUser().getRoom().getItemManager().getById(itemId);
            player.getRoomUser().getRoom().send(new BROADCAST_TELEPORTER(item, player.getDetails().getName(), false));
        }
    }
}
