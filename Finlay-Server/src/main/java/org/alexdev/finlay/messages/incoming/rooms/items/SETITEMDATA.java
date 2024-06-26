package org.alexdev.finlay.messages.incoming.rooms.items;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.item.base.ItemBehaviour;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.util.StringUtil;

public class SETITEMDATA implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        //if (!room.hasRights(player.getDetails().getId())) {
        //    return;
        //}

        String contents = reader.contents();

        int itemId = Integer.parseInt(contents.substring(0, contents.indexOf('/')));
        int itemIdLength = String.valueOf(itemId).length();

        String colour = contents.substring(itemIdLength + 1).substring(0, 6);
        String newMessage = StringUtil.filterInput(contents.substring(itemIdLength + 8), false);

        if (!colour.equals("FFFFFF") &&
                !colour.equals("FFFF33") &&
                !colour.equals("FF9CFF") &&
                !colour.equals("9CFF9C") &&
                !colour.equals("9CCEFF")) {
            return;
        }

        Item item = room.getItemManager().getById(itemId);

        if (item == null) return;

        if(!item.hasBehaviour(ItemBehaviour.POST_IT)) return; // Prevent from editing other furnis.

        String oldText = "";

        if (item.getCustomData().length() > 6) { // Strip colour code
            oldText = item.getCustomData().substring(6);
        }

        // If the user doesn't have rights, make sure they can only append to the sticky, not remove the existing information before it
        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.ANY_ROOM_CONTROLLER)) {
            if (!newMessage.startsWith(oldText)) {
                return;
            }
        }

        if (newMessage.length() > 684) {
            newMessage = newMessage.substring(0, 684);
        }

        item.setCustomData(colour + newMessage);
        item.updateStatus();
        item.save();
    }
}
