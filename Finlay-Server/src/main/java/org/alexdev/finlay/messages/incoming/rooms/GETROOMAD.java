package org.alexdev.finlay.messages.incoming.rooms;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.outgoing.rooms.ROOM_AD;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GETROOMAD implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        player.send(new ROOM_AD());
    }
}
