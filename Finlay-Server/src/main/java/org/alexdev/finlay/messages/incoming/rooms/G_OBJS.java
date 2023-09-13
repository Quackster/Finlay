package org.alexdev.finlay.messages.incoming.rooms;

import org.alexdev.finlay.game.item.Item;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.messages.outgoing.rooms.ACTIVE_OBJECTS;
import org.alexdev.finlay.messages.outgoing.rooms.OBJECTS_WORLD;
import org.alexdev.finlay.messages.outgoing.rooms.items.SHOWPROGRAM;
import org.alexdev.finlay.messages.types.MessageComposer;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;
import org.alexdev.finlay.server.netty.streams.NettyResponse;

public class G_OBJS implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        player.sendQueued(new OBJECTS_WORLD(room.getItemManager().getPublicItems()));
        player.sendQueued(new ACTIVE_OBJECTS(room));
        player.flush();

        player.getMessenger().sendStatusUpdate();
    }
}
