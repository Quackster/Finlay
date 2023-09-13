package org.alexdev.finlay.messages.incoming.jukebox;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.jukebox.USER_SONG_DISKS;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

import java.util.HashMap;
import java.util.Map;

public class GET_USER_SONG_DISCS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room.getItemManager().getSoundMachine() == null) {
            return;
        }

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            return;
        }

        Map<Item, Integer> userDisks = new HashMap<>();

        for (Item item : player.getInventory().getItems()) {
            if (item.isHidden()) {
                continue;
            }

            if (item.hasBehaviour(ItemBehaviour.SONG_DISK)) {
                userDisks.put(item, item.getId());
            }
        }

        player.send(new USER_SONG_DISKS(userDisks));
    }
}
