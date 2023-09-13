package org.alexdev.finlay.messages.incoming.inventory;

import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.messages.types.MessageEvent;
import org.alexdev.finlay.server.netty.streams.NettyRequest;

public class GETSTRIP implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) {
        String stripView = reader.contents();
        player.getInventory().getView(stripView);
    }
}
